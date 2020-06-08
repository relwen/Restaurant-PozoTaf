package iam.abdoulkader.pozotaf.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.thebrownarrow.customfont.CustomFontTextView;

import java.util.ArrayList;

import iam.abdoulkader.pozotaf.R;
import iam.abdoulkader.pozotaf.data.FoodCategory;
import iam.abdoulkader.pozotaf.listener.FoodCategoryViewClickListener;
import iam.abdoulkader.pozotaf.service.PozotafWebService;

/**
 * Created by root on 31/03/18.
 */

public class FoodCategoryViewAdapter extends RecyclerView.Adapter<FoodCategoryViewAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<FoodCategory> mFoodCategorys;
    private ViewFlipper mViewFlipper;
    private static FoodCategoryViewClickListener mFoodCategoryViewClickListener;

    public FoodCategoryViewAdapter(Context mContext, ArrayList<FoodCategory> mFoodCategorys,
                               ViewFlipper mViewFlipper, FoodCategoryViewClickListener listener) {
        this.mContext = mContext;
        this.mFoodCategorys = mFoodCategorys;
        this.mViewFlipper = mViewFlipper;
        FoodCategoryViewAdapter.mFoodCategoryViewClickListener = listener;

        if(mFoodCategorys.size() > 0)
            this.mViewFlipper.setDisplayedChild(2);
        else
            this.mViewFlipper.setDisplayedChild(1);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.food_cat_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FoodCategory foodCategory = mFoodCategorys.get(position);
        holder.bind(foodCategory);
    }

    @Override
    public int getItemCount() {
        return mFoodCategorys.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {


        private final ImageView image;
        private final CustomFontTextView name;

        public ViewHolder(View itemView) {
            super(itemView);

            image = (ImageView) itemView.findViewById(R.id.food_cat_image);
            name = (CustomFontTextView) itemView.findViewById(R.id.food_cat_name);

            if(FoodCategoryViewAdapter.mFoodCategoryViewClickListener != null) {
                itemView.setOnClickListener(this);
                itemView.setOnLongClickListener(this);
            }

        }

        public void bind(FoodCategory foodCategory) {
            name.setText(foodCategory.name);
            Glide.with(mContext).load(PozotafWebService.ENDPOINT + PozotafWebService.TARGET + "/" + foodCategory.image).into(image);
        }


        @Override
        public void onClick(View view) {
            mFoodCategoryViewClickListener.onFoodCategoryClickListener(getAdapterPosition(), view, mFoodCategorys.get(getAdapterPosition()));
        }

        @Override
        public boolean onLongClick(View view) {
            mFoodCategoryViewClickListener.onFoodCategoryLongClickListener(getAdapterPosition(), view, mFoodCategorys.get(getAdapterPosition()));
            return false;
        }
    }
}
