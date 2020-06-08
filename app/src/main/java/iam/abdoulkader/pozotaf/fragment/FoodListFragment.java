package iam.abdoulkader.pozotaf.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.sql.SQLException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import iam.abdoulkader.pozotaf.FoodDetailsActivity;
import iam.abdoulkader.pozotaf.R;
import iam.abdoulkader.pozotaf.adapter.FoodCategoryViewAdapter;
import iam.abdoulkader.pozotaf.adapter.FoodViewAdapter;
import iam.abdoulkader.pozotaf.adapter.MySpinnerAdapter;
import iam.abdoulkader.pozotaf.adapter.RestoViewAdapter;
import iam.abdoulkader.pozotaf.data.CartFoodCategory;
import iam.abdoulkader.pozotaf.data.Food;
import iam.abdoulkader.pozotaf.data.FoodCategory;
import iam.abdoulkader.pozotaf.data.Resto;
import iam.abdoulkader.pozotaf.listener.FoodViewClickListener;
import iam.abdoulkader.pozotaf.util.CommonTask;
import iam.abdoulkader.pozotaf.util.Constant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FoodListFragment extends BaseFragment {

    @BindView(R.id.spinner) Spinner mFoodCatSpinner;
    @BindView(R.id.food_viewflipper) ViewFlipper mFoodVF;
    @BindView(R.id.food_recyclerview) RecyclerView mFoodRV;

    private ArrayList<Food> mFoods;
    private FoodCategory mFoodCategory;
    private FoodViewAdapter mFoodAdapter;

    public static FoodListFragment newInstance(FoodCategory foodCategory) {
        FoodListFragment fragment = new FoodListFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constant.SELECTED_FOOD_CATEGORY, foodCategory);
        fragment.setArguments(args);

        return fragment;
    }

    public FoodListFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mFoodCategory = getArguments().getParcelable(Constant.SELECTED_FOOD_CATEGORY);
        }

        if (savedInstanceState != null) {
            mFoods = savedInstanceState.getParcelableArrayList(Constant.SAVED_FOOD_LIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_food_list, container, false);
        mContext = getContext();

        ButterKnife.bind(this, mView);

        mFoods = new ArrayList<>();

        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getFoodCategories();
        getFoods();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    private void getFoods() {

        try {

            if (mFoodCategory == null) {
                mFoods = (ArrayList<Food>) getDatabaseHelper().getFoodDao().queryForAll();
                setFoodAdapter();
            } else {
                Call<ArrayList<Food>> call = CommonTask.getWebService().getFoods(String.valueOf(mFoodCategory.id));
                call.enqueue(new Callback<ArrayList<Food>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Food>> call, Response<ArrayList<Food>> response) {
                        if(response.isSuccessful()) {
                            mFoods = response.body();
                            setFoodAdapter();
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Food>> call, Throwable throwable) {
                        Toast.makeText(mContext, R.string.error_occured, Toast.LENGTH_LONG).show();
                        Log.d("MY_USER_FAIL", throwable.getMessage());
                        mFoodVF.setDisplayedChild(1);
                    }
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
            mFoodVF.setDisplayedChild(1);
        }
    }

    private void getFoodCategories() {

        try {
            final ArrayList<FoodCategory> fcs = (ArrayList<FoodCategory>) getDatabaseHelper()
                    .getFoodCategoryDao().queryForAll();
            final String[] menuTitles = new String[fcs.size() + 1];
            menuTitles[0] = getString(R.string.all_cats);
            int i = 1;
            for(FoodCategory fc : fcs) {
                menuTitles[i] = fc.name;
                i++;
            }
            mFoodCatSpinner.setAdapter(new MySpinnerAdapter(
                    mContext,
                    menuTitles));
            for(int j = 0; j < menuTitles.length; j++) {
                if (mFoodCategory != null && mFoodCategory.name.equals(menuTitles[j])) {
                    mFoodCatSpinner.setSelection(j);
                }
            }

            mFoodCatSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    // When the given dropdown item is selected, show its contents in the
                    // container view.
                    if (position == 0) {
                        mFoodCategory = null;
                    } else {
                        mFoodCategory = fcs.get(position - 1);
                    }
                    mFoodVF.setDisplayedChild(0);
                    getFoods();

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
            mFoodCatSpinner.setVisibility(View.VISIBLE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setFoodAdapter() {

        mFoodAdapter = new FoodViewAdapter(mContext, mFoods, mFoodVF, new FoodViewClickListener() {
            @Override
            public void onFoodClickListener(int position, View view, Food food) {
                Intent intent = new Intent(mContext, FoodDetailsActivity.class);
                intent.putExtra(Constant.SELECTED_FOOD, food);
                CommonTask.openActivity(mContext, intent);
            }

            @Override
            public void onFoodLongClickListener(int position, View view, Food food) {

            }
        });
        //mFoodRV.setNestedScrollingEnabled(false);
        mFoodRV.setAdapter(mFoodAdapter);
    }
}
