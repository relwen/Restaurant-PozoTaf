package iam.abdoulkader.pozotaf.listener;

import android.view.View;

import iam.abdoulkader.pozotaf.data.FoodCategory;


/**
 * Created by root on 31/03/18.
 */

public interface FoodCategoryViewClickListener {

    public void onFoodCategoryClickListener(int position, View view, FoodCategory foodCategory);
    public void onFoodCategoryLongClickListener(int position, View view, FoodCategory foodCategory);
}
