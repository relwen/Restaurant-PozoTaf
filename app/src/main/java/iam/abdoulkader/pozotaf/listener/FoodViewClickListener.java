package iam.abdoulkader.pozotaf.listener;

import android.view.View;

import iam.abdoulkader.pozotaf.data.Food;


/**
 * Created by root on 31/03/18.
 */

public interface FoodViewClickListener {

    public void onFoodClickListener(int position, View view, Food food);
    public void onFoodLongClickListener(int position, View view, Food food);
}
