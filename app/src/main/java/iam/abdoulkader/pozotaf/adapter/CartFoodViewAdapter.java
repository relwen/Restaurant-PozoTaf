package iam.abdoulkader.pozotaf.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.thebrownarrow.customfont.CustomFontTextView;

import java.text.NumberFormat;
import java.util.ArrayList;

import iam.abdoulkader.pozotaf.R;
import iam.abdoulkader.pozotaf.data.CartFood;
import iam.abdoulkader.pozotaf.data.CartFood;
import iam.abdoulkader.pozotaf.listener.CartFoodViewClickListener;
import iam.abdoulkader.pozotaf.service.PozotafWebService;

/**
 * Created by root on 31/03/18.
 */

public class CartFoodViewAdapter extends RecyclerView.Adapter<CartFoodViewAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<CartFood> mCartFoods;
    private ViewFlipper mViewFlipper;
    private static CartFoodViewClickListener mCartFoodViewClickListener;

    public CartFoodViewAdapter(Context mContext, ArrayList<CartFood> mCartFoods,
                               ViewFlipper mViewFlipper, CartFoodViewClickListener listener) {
        this.mContext = mContext;
        this.mCartFoods = mCartFoods;
        this.mViewFlipper = mViewFlipper;
        CartFoodViewAdapter.mCartFoodViewClickListener = listener;

        if(mCartFoods.size() > 0)
            this.mViewFlipper.setDisplayedChild(2);
        else
            this.mViewFlipper.setDisplayedChild(1);
    }

    public void addCartFood(int position, CartFood cartFood) {
        mCartFoods.add(position, cartFood);
        notifyItemInserted(position);

        if(mCartFoods.size() > 0)
            this.mViewFlipper.setDisplayedChild(2);
        else
            this.mViewFlipper.setDisplayedChild(1);
    }

    public void deleteCartFood(int position) {
        mCartFoods.remove(position);
        notifyItemRemoved(position);

        if(mCartFoods.size() > 0)
            this.mViewFlipper.setDisplayedChild(2);
        else
            this.mViewFlipper.setDisplayedChild(1);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.cart_food_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CartFood cartFood = mCartFoods.get(position);
        holder.bind(cartFood);
    }

    @Override
    public int getItemCount() {
        return mCartFoods.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {


        private final ImageView image;
        private final CustomFontTextView name;
        private final CustomFontTextView desc;
        private final CustomFontTextView price;
        private final CustomFontTextView number;

        public ViewHolder(View itemView) {
            super(itemView);

            image = (ImageView) itemView.findViewById(R.id.image);
            name = (CustomFontTextView) itemView.findViewById(R.id.item_name);
            desc = (CustomFontTextView) itemView.findViewById(R.id.item_desc);
            price = (CustomFontTextView) itemView.findViewById(R.id.price);
            number = (CustomFontTextView) itemView.findViewById(R.id.item_number);

            if(CartFoodViewAdapter.mCartFoodViewClickListener != null) {
                itemView.setOnClickListener(this);
                itemView.setOnLongClickListener(this);
            }

        }

        public void bind(CartFood cartFood) {
            Glide.with(mContext).load(PozotafWebService.ENDPOINT + PozotafWebService.TARGET + "/" + cartFood.image).into(image);
            name.setText(cartFood.name);
            number.setText(cartFood.number + "x");
            desc.setText(cartFood.desc);
            price.setText(NumberFormat.getInstance().format(cartFood.price) + " fcfa");
        }


        @Override
        public void onClick(View view) {
            mCartFoodViewClickListener.onCartFoodClickListener(getAdapterPosition(), view, mCartFoods.get(getAdapterPosition()));
        }

        @Override
        public boolean onLongClick(View view) {
            mCartFoodViewClickListener.onCartFoodLongClickListener(getAdapterPosition(), view, mCartFoods.get(getAdapterPosition()));
            return false;
        }
    }
}
