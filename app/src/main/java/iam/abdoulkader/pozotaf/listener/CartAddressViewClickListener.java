package iam.abdoulkader.pozotaf.listener;

import android.view.View;

import iam.abdoulkader.pozotaf.data.CartAddress;


/**
 * Created by root on 31/03/18.
 */

public interface CartAddressViewClickListener {

    public void onCartAddressClickListener(int position, View view, CartAddress cartAddress);
    public void onCartAddressLongClickListener(int position, View view, CartAddress cartAddress);
}
