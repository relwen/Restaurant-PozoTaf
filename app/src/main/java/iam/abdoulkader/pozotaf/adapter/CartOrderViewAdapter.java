package iam.abdoulkader.pozotaf.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewFlipper;

import com.thebrownarrow.customfont.CustomFontTextView;

import java.util.ArrayList;

import iam.abdoulkader.pozotaf.R;
import iam.abdoulkader.pozotaf.data.CartOrder;
import iam.abdoulkader.pozotaf.listener.CartOrderViewClickListener;

/**
 * Created by root on 31/03/18.
 */

public class CartOrderViewAdapter extends RecyclerView.Adapter<CartOrderViewAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<CartOrder> mCartOrders;
    private ViewFlipper mViewFlipper;
    private static CartOrderViewClickListener mCartOrderViewClickListener;

    public CartOrderViewAdapter(Context mContext, ArrayList<CartOrder> mCartOrders,
                                ViewFlipper mViewFlipper, CartOrderViewClickListener listener) {
        this.mContext = mContext;
        this.mCartOrders = mCartOrders;
        this.mViewFlipper = mViewFlipper;
        CartOrderViewAdapter.mCartOrderViewClickListener = listener;

        if(mCartOrders.size() > 0)
            this.mViewFlipper.setDisplayedChild(2);
        else
            this.mViewFlipper.setDisplayedChild(1);
    }

    public void addCartOrder(int position, CartOrder cartOrder) {
        mCartOrders.add(position, cartOrder);
        notifyItemInserted(position);

        if(mCartOrders.size() > 0)
            this.mViewFlipper.setDisplayedChild(2);
        else
            this.mViewFlipper.setDisplayedChild(1);
    }

    public void deleteCartOrder(int position) {
        mCartOrders.remove(position);
        notifyItemRemoved(position);

        if(mCartOrders.size() > 0)
            this.mViewFlipper.setDisplayedChild(2);
        else
            this.mViewFlipper.setDisplayedChild(1);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.order_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CartOrder cartOrder = mCartOrders.get(position);
        holder.bind(cartOrder);
    }

    @Override
    public int getItemCount() {
        return mCartOrders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {


        private final CustomFontTextView date;
        private final CustomFontTextView items;
        private final CustomFontTextView total_prize;

        public ViewHolder(View itemView) {
            super(itemView);

            date = (CustomFontTextView) itemView.findViewById(R.id.date);
            items = (CustomFontTextView) itemView.findViewById(R.id.items);
            total_prize = (CustomFontTextView) itemView.findViewById(R.id.total_prize);

            if(CartOrderViewAdapter.mCartOrderViewClickListener != null) {
                itemView.setOnClickListener(this);
                itemView.setOnLongClickListener(this);
            }

        }

        public void bind(CartOrder cartOrder) {

        }


        @Override
        public void onClick(View view) {
            mCartOrderViewClickListener.onCartOrderClickListener(getAdapterPosition(), view, mCartOrders.get(getAdapterPosition()));
        }

        @Override
        public boolean onLongClick(View view) {
            mCartOrderViewClickListener.onCartOrderLongClickListener(getAdapterPosition(), view, mCartOrders.get(getAdapterPosition()));
            return false;
        }
    }
}
