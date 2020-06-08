package iam.abdoulkader.pozotaf.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.thebrownarrow.customfont.CustomFontButton;
import com.thebrownarrow.customfont.CustomFontTextView;

import java.text.NumberFormat;
import java.util.ArrayList;

import iam.abdoulkader.pozotaf.R;
import iam.abdoulkader.pozotaf.data.Food;
import iam.abdoulkader.pozotaf.listener.FoodViewClickListener;
import iam.abdoulkader.pozotaf.service.PozotafWebService;

/**
 * Created by root on 31/03/18.
 */

public class FoodViewAdapter extends RecyclerView.Adapter<FoodViewAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Food> mFoods;
    private ViewFlipper mViewFlipper;
    private static FoodViewClickListener mFoodViewClickListener;

    public FoodViewAdapter(Context mContext, ArrayList<Food> mFoods,
                           ViewFlipper mViewFlipper, FoodViewClickListener listener) {
        this.mContext = mContext;
        this.mFoods = mFoods;
        this.mViewFlipper = mViewFlipper;
        FoodViewAdapter.mFoodViewClickListener = listener;

        if(mFoods.size() > 0)
            this.mViewFlipper.setDisplayedChild(2);
        else
            this.mViewFlipper.setDisplayedChild(1);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.food_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Food food = mFoods.get(position);
        holder.bind(food);
    }

    @Override
    public int getItemCount() {
        return mFoods.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {


        private final ImageView image;
        private final CustomFontTextView name_and_desc;
        private final CustomFontTextView price;
        private final ImageView non_halal;

        public ViewHolder(View itemView) {
            super(itemView);

            image = (ImageView) itemView.findViewById(R.id.image);
            name_and_desc = (CustomFontTextView) itemView.findViewById(R.id.item_name_and_desc);
            price = (CustomFontTextView) itemView.findViewById(R.id.price);
            non_halal = (ImageView) itemView.findViewById(R.id.non_halal);

            if(FoodViewAdapter.mFoodViewClickListener != null) {
                itemView.setOnClickListener(this);
                itemView.setOnLongClickListener(this);
            }

        }

        public void bind(Food food) {
            Glide.with(mContext).load(PozotafWebService.ENDPOINT + PozotafWebService.TARGET + "/" + food.image).into(image);
            name_and_desc.setText(Html.fromHtml("<strong>" + food.name + "</strong> - " + food.desc));
            price.setText(NumberFormat.getInstance().format(food.price) + " fcfa");

            if (food.is_non_halal)
                Glide.with(mContext).load(R.drawable.non_halal_sign).into(non_halal);
        }


        @Override
        public void onClick(View view) {
            mFoodViewClickListener.onFoodClickListener(getAdapterPosition(), view, mFoods.get(getAdapterPosition()));
        }

        @Override
        public boolean onLongClick(View view) {
            mFoodViewClickListener.onFoodLongClickListener(getAdapterPosition(), view, mFoods.get(getAdapterPosition()));
            return false;
        }
    }
}
