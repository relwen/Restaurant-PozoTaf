package iam.abdoulkader.pozotaf;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import devlight.io.library.ntb.NavigationTabBar;
import iam.abdoulkader.pozotaf.adapter.RestoViewAdapter;
import iam.abdoulkader.pozotaf.data.Food;
import iam.abdoulkader.pozotaf.data.FoodCategory;
import iam.abdoulkader.pozotaf.data.Resto;
import iam.abdoulkader.pozotaf.fragment.FoodListFragment;
import iam.abdoulkader.pozotaf.fragment.HomeFragment;
import iam.abdoulkader.pozotaf.fragment.RestoListFragment;
import iam.abdoulkader.pozotaf.fragment.UserAccountFragment;
import iam.abdoulkader.pozotaf.util.CommonTask;
import iam.abdoulkader.pozotaf.util.Constant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity {

    @BindView(R.id.botton_nav_view) public NavigationTabBar mNavigationTabBar;

    public HomeFragment mHomeFragment;
    public FoodListFragment mFoodListFragment;
    public RestoListFragment mRestoListFragment;
    public UserAccountFragment mUserAccountFragment;
    public int mCurrentIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);

        mCurrentIndex = 0;

        ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(new NavigationTabBar.Model.Builder(
                getResources().getDrawable(R.drawable.ic_home_black_24dp), Color.WHITE)
                .title(getResources().getString(R.string.home_str)).build());
        models.add(new NavigationTabBar.Model.Builder(
                getResources().getDrawable(R.drawable.ic_room_service_black_24dp), Color.WHITE)
                .title(getResources().getString(R.string.browse_food_str)).build());
        models.add(new NavigationTabBar.Model.Builder(
                getResources().getDrawable(R.drawable.ic_restaurant_black_24dp), Color.WHITE)
                .title(getResources().getString(R.string.resto_str)).build());
        models.add(new NavigationTabBar.Model.Builder(
                getResources().getDrawable(R.drawable.ic_account_circle_black_24dp), Color.WHITE)
                .title(getResources().getString(R.string.user_account_str)).build());
        mNavigationTabBar.setModels(models);
    }

    @Override
    protected void onResume() {
        super.onResume();

        getRestos();

        if (mHomeFragment == null) {
            mHomeFragment = new HomeFragment();
        }

        if (mFoodListFragment == null) {
            mFoodListFragment = new FoodListFragment();
        }

        if (mRestoListFragment == null) {
            mRestoListFragment = new RestoListFragment();
        }

        if (mUserAccountFragment == null) {
            mUserAccountFragment = new UserAccountFragment();
        }

        mNavigationTabBar.setOnTabBarSelectedIndexListener(new NavigationTabBar.OnTabBarSelectedIndexListener() {
            @Override
            public void onStartTabSelected(NavigationTabBar.Model model, int index) {
                getRestos();
                mCurrentIndex = index;
                if (index == 0)
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, mHomeFragment).commit();
                else if (index == 1)
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, mFoodListFragment).commit();
                else if (index == 2)
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, mRestoListFragment).commit();
                else if (index == 3)
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, mUserAccountFragment).commit();
            }

            @Override
            public void onEndTabSelected(NavigationTabBar.Model model, int index) {

            }
        });
        mNavigationTabBar.setModelIndex(mCurrentIndex, true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.cart) {
            CommonTask.openActivity(mContext, CartActivity.class);
        } else if (id == R.id.orders) {
            CommonTask.openActivity(mContext, OrderActivity.class);
        }

        return super.onOptionsItemSelected(item);

    }

    /******************************************************/

    private void getRestos() {

        Call<ArrayList<Resto>> call = CommonTask.getWebService().getRestos();
        call.enqueue(new Callback<ArrayList<Resto>>() {
            @Override
            public void onResponse(Call<ArrayList<Resto>> call, Response<ArrayList<Resto>> response) {
                if (response.isSuccessful()) {
                    for(Resto r : response.body()) {
                        try {
                            getDatabaseHelper().getRestoDao().createOrUpdate(r);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }

                    getFoodCategories();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Resto>> call, Throwable throwable) {
                Toast.makeText(mContext, R.string.error_occured, Toast.LENGTH_LONG).show();
                Log.d("MY_USER_FAIL", throwable.getMessage());
            }
        });
    }

    private void getFoodCategories() {

        Call<ArrayList<FoodCategory>> call = CommonTask.getWebService().getFoodCategories();
        call.enqueue(new Callback<ArrayList<FoodCategory>>() {
            @Override
            public void onResponse(Call<ArrayList<FoodCategory>> call, Response<ArrayList<FoodCategory>> response) {
                for(FoodCategory fc : response.body()) {
                    try {
                        getDatabaseHelper().getFoodCategoryDao().createOrUpdate(fc);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                getFoods();
            }

            @Override
            public void onFailure(Call<ArrayList<FoodCategory>> call, Throwable throwable) {
                Toast.makeText(mContext, R.string.error_occured, Toast.LENGTH_LONG).show();
                Log.d("MY_USER_FAIL", throwable.getMessage());
            }
        });
    }

    private void getFoods() {

        Call<ArrayList<Food>> call = CommonTask.getWebService().getFoods();
        call.enqueue(new Callback<ArrayList<Food>>() {
            @Override
            public void onResponse(Call<ArrayList<Food>> call, Response<ArrayList<Food>> response) {
                for(Food f : response.body()) {
                    try {
                        getDatabaseHelper().getFoodDao().createOrUpdate(f);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
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
