package iam.abdoulkader.pozotaf.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.thebrownarrow.customfont.CustomFontTextView;

import java.text.NumberFormat;
import java.util.ArrayList;

import iam.abdoulkader.pozotaf.R;
import iam.abdoulkader.pozotaf.data.Cart;
import iam.abdoulkader.pozotaf.data.CartAddress;
import iam.abdoulkader.pozotaf.listener.CartAddressViewClickListener;

/**
 * Created by root on 31/03/18.
 */

public class CartAddressViewAdapter extends RecyclerView.Adapter<CartAddressViewAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<CartAddress> mCartAddresss;
    private ViewFlipper mViewFlipper;
    private static CartAddressViewClickListener mCartAddressViewClickListener;

    public CartAddressViewAdapter(Context mContext, ArrayList<CartAddress> mCartAddresss,
                                  ViewFlipper mViewFlipper, CartAddressViewClickListener listener) {
        this.mContext = mContext;
        this.mCartAddresss = mCartAddresss;
        this.mViewFlipper = mViewFlipper;
        CartAddressViewAdapter.mCartAddressViewClickListener = listener;

        if(mCartAddresss.size() > 0)
            this.mViewFlipper.setDisplayedChild(2);
        else
            this.mViewFlipper.setDisplayedChild(1);
    }

    public void addCartAddress(int position, CartAddress cartAddress) {
        mCartAddresss.add(position, cartAddress);
        notifyItemInserted(position);

        if(mCartAddresss.size() > 0)
            this.mViewFlipper.setDisplayedChild(2);
        else
            this.mViewFlipper.setDisplayedChild(1);
    }

    public void deleteCartAddress(int position) {
        mCartAddresss.remove(position);
        notifyItemRemoved(position);

        if(mCartAddresss.size() > 0)
            this.mViewFlipper.setDisplayedChild(2);
        else
            this.mViewFlipper.setDisplayedChild(1);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.address_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CartAddress cartAddress = mCartAddresss.get(position);
        holder.bind(cartAddress);
    }

    @Override
    public int getItemCount() {
        return mCartAddresss.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {


        private final CustomFontTextView name;
        private final CustomFontTextView location;

        public ViewHolder(View itemView) {
            super(itemView);

            name = (CustomFontTextView) itemView.findViewById(R.id.name);
            location = (CustomFontTextView) itemView.findViewById(R.id.location);

            if(CartAddressViewAdapter.mCartAddressViewClickListener != null) {
                itemView.setOnClickListener(this);
                itemView.setOnLongClickListener(this);
            }

        }

        public void bind(CartAddress cartAddress) {
            name.setText(cartAddress.name);
            location.setText(cartAddress.location);
        }


        @Override
        public void onClick(View view) {
            mCartAddressViewClickListener.onCartAddressClickListener(getAdapterPosition(), view, mCartAddresss.get(getAdapterPosition()));
        }

        @Override
        public boolean onLongClick(View view) {
            mCartAddressViewClickListener.onCartAddressLongClickListener(getAdapterPosition(), view, mCartAddresss.get(getAdapterPosition()));
            return false;
        }
    }
}
