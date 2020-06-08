package iam.abdoulkader.pozotaf.listener;

import android.view.View;

import iam.abdoulkader.pozotaf.data.CartFood;


/**
 * Created by root on 31/03/18.
 */

public interface CartFoodViewClickListener {

    public void onCartFoodClickListener(int position, View view, CartFood cartFood);
    public void onCartFoodLongClickListener(int position, View view, CartFood cartFood);
}
