package iam.abdoulkader.pozotaf.listener;

import android.view.View;

import iam.abdoulkader.pozotaf.data.CartOrder;


/**
 * Created by root on 31/03/18.
 */

public interface CartOrderViewClickListener {

    public void onCartOrderClickListener(int position, View view, CartOrder cartOrder);
    public void onCartOrderLongClickListener(int position, View view, CartOrder cartOrder);
}
