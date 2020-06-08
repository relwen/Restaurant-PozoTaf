package iam.abdoulkader.pozotaf.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.request.RequestOptions;
import com.glide.slider.library.Animations.DescriptionAnimation;
import com.glide.slider.library.SliderLayout;
import com.glide.slider.library.SliderTypes.BaseSliderView;
import com.glide.slider.library.SliderTypes.TextSliderView;
import com.glide.slider.library.Tricks.ViewPagerEx;

import java.sql.SQLException;
import java.util.ArrayList;

import iam.abdoulkader.pozotaf.FoodDetailsActivity;
import iam.abdoulkader.pozotaf.MainActivity;
import iam.abdoulkader.pozotaf.R;
import iam.abdoulkader.pozotaf.adapter.FoodCategoryViewAdapter;
import iam.abdoulkader.pozotaf.adapter.FoodViewAdapter;
import iam.abdoulkader.pozotaf.adapter.RestoViewAdapter;
import iam.abdoulkader.pozotaf.data.Food;
import iam.abdoulkader.pozotaf.data.FoodCategory;
import iam.abdoulkader.pozotaf.data.Resto;
import iam.abdoulkader.pozotaf.listener.FoodCategoryViewClickListener;
import iam.abdoulkader.pozotaf.listener.FoodViewClickListener;
import iam.abdoulkader.pozotaf.util.CommonTask;
import iam.abdoulkader.pozotaf.util.Constant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment implements BaseSliderView.OnSliderClickListener,
        ViewPagerEx.OnPageChangeListener {


    private SliderLayout mFavoriteFoodSlider;

    private ViewFlipper mFoodCatVF;
    private RecyclerView mFoodCatRV;
    private ArrayList<FoodCategory> mFoodCategories;
    private FoodCategoryViewAdapter mFoodCategoryAdapter;

    private ViewFlipper mFoodVF;
    private RecyclerView mFoodRV;
    private ArrayList<Food> mFoods;
    private FoodViewAdapter mFoodAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mContext = getContext();
        
        mFavoriteFoodSlider = (SliderLayout) view.findViewById(R.id.slider);
        mFoodCatVF = (ViewFlipper) view.findViewById(R.id.food_cat_viewflipper);
        mFoodCatRV = (RecyclerView) view.findViewById(R.id.food_cat_recyclerview);

        mFoodVF = (ViewFlipper) view.findViewById(R.id.food_viewflipper);
        mFoodRV = (RecyclerView) view.findViewById(R.id.food_recyclerview);

        ArrayList<Integer> listImages = new ArrayList<>();
        ArrayList<String> listName = new ArrayList<>();

        listImages.add(R.drawable.test_food);
        listName.add("PLAT 1 - 5000 Fcfa");

        listImages.add(R.drawable.test_food_0);
        listName.add("PLAT 2 - 5000 Fcfa");

        listImages.add(R.drawable.test_food_1);
        listName.add("PLAT 3 - 3000 Fcfa");

        listImages.add(R.drawable.test_food_2);
        listName.add("PLAT 4 - 5000 Fcfa");

        listImages.add(R.drawable.test_food_3);
        listName.add("PLAT 5 - 5000 Fcfa");

        listImages.add(R.drawable.test_food_8);
        listName.add("PLAT 6 - 3000 Fcfa");

        RequestOptions requestOptions = new RequestOptions();
        requestOptions
                .centerCrop();
                //.diskCacheStrategy(DiskCacheStrategy.NONE)
                //.placeholder(R.drawable.placeholder)
                //.error(R.drawable.placeholder);

        for (int i = 0; i < listImages.size(); i++) {
            TextSliderView sliderView = new TextSliderView(mContext);
            // if you want show image only / without description text use DefaultSliderView instead

            // initialize SliderLayout
            sliderView
                    .image(listImages.get(i))
                    .description(listName.get(i))
                    .setRequestOption(requestOptions)
                    .setBackgroundColor(Color.WHITE)
                    .setProgressBarVisible(true)
                    .setOnSliderClickListener(this);

            //add your extra information
            sliderView.bundle(new Bundle());
            sliderView.getBundle().putString("extra", listName.get(i));
            mFavoriteFoodSlider.addSlider(sliderView);
        }

        // set Slider Transition Animation
        // mFavoriteFoodSlider.setPresetTransformer(SliderLayout.Transformer.Default);
        mFavoriteFoodSlider.setPresetTransformer(SliderLayout.Transformer.Fade);

        mFavoriteFoodSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mFavoriteFoodSlider.setCustomAnimation(new DescriptionAnimation());
        mFavoriteFoodSlider.setDuration(4000);
        mFavoriteFoodSlider.addOnPageChangeListener(this);

        //===========================

        mFoodCategories = new ArrayList<>();
        mFoods = new ArrayList<>();
        
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        getFoodCategories();
        getFoodOfTheDay();
    }

    @Override
    public void onStop() {

        mFavoriteFoodSlider.stopAutoCycle();

        super.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void getFoodCategories() {

        try {
            mFoodCategories = (ArrayList<FoodCategory>) getDatabaseHelper().getFoodCategoryDao().queryForAll();
            mFoodCategoryAdapter = new FoodCategoryViewAdapter(mContext, mFoodCategories, mFoodCatVF,
                    new FoodCategoryViewClickListener() {
                        @Override
                        public void onFoodCategoryClickListener(int position, View view, final FoodCategory foodCategory) {
                            getAvailableActivity(new IActivityEnabledListener() {
                                @Override
                                public void onActivityEnabled(FragmentActivity activity) {
                                    ((MainActivity)activity).mCurrentIndex = 1;
                                    ((MainActivity)activity).mNavigationTabBar
                                            .setModelIndex(((MainActivity)activity).mCurrentIndex, true);
                                    //((MainActivity)activity).mFoodListFragment = FoodListFragment.newInstance(foodCategory);
                                    activity.getSupportFragmentManager().beginTransaction()
                                            .replace(R.id.container, FoodListFragment.newInstance(foodCategory))
                                            .commit();
                                }
                            });
                        }

                        @Override
                        public void onFoodCategoryLongClickListener(int position, View view, FoodCategory foodCategory) {

                        }
                    });
            mFoodCatRV.setAdapter(mFoodCategoryAdapter);
        } catch (SQLException e) {
            e.printStackTrace();
            mFoodCatVF.setDisplayedChild(1);
        }
    }

    private void getFoodOfTheDay() {

        Call<ArrayList<Food>> call = CommonTask.getWebService().getFoodOfTheDay();
        call.enqueue(new Callback<ArrayList<Food>>() {
            @Override
            public void onResponse(Call<ArrayList<Food>> call, Response<ArrayList<Food>> response) {
                if (response.isSuccessful()) {
                    mFoods = response.body();
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
                    mFoodRV.setNestedScrollingEnabled(false);
                    mFoodRV.setAdapter(mFoodAdapter);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Food>> call, Throwable throwable) {
                Toast.makeText(mContext, R.string.error_occured, Toast.LENGTH_LONG).show();
                Log.d("MY_USER_FAIL", throwable.getMessage());
            }
        });
    }
}
