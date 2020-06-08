package iam.abdoulkader.pozotaf;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.thebrownarrow.customfont.CustomFontTextView;

import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;

import iam.abdoulkader.pozotaf.adapter.CartFoodViewAdapter;
import iam.abdoulkader.pozotaf.adapter.FoodViewAdapter;
import iam.abdoulkader.pozotaf.data.Cart;
import iam.abdoulkader.pozotaf.data.CartFood;
import iam.abdoulkader.pozotaf.data.Food;
import iam.abdoulkader.pozotaf.listener.CartFoodViewClickListener;
import iam.abdoulkader.pozotaf.util.CommonTask;

public class CartActivity extends BaseActivity {

    private ViewFlipper mCartFoodVF;
    private RecyclerView mCartFoodRV;
    private ArrayList<CartFood> mCartFoods;
    private CartFoodViewAdapter mCartFoodAdapter;

    private LinearLayout mOrderL;
    private CustomFontTextView mTotalItemTV;
    private CustomFontTextView mTotalPrizeTV;

    private Cart mCurrentCart;
    private int mCount;
    private int mTotalItem;
    private int mTotalPrize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.black));
        }

        setContentView(R.layout.activity_cart);

        mContext = this;

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);

        mCartFoodVF = (ViewFlipper) findViewById(R.id.cart_food_viewflipper);
        mCartFoodRV = (RecyclerView) findViewById(R.id.cart_food_recyclerview);
        mOrderL = (LinearLayout) findViewById(R.id.min_cart_layout);
        mTotalItemTV = (CustomFontTextView) findViewById(R.id.total_cart_items);
        mTotalPrizeTV = (CustomFontTextView) findViewById(R.id.total_prize);

        displayCardFoods();
    }

    private void displayCardFoods() {

        mCartFoods = new ArrayList<>();

        try {
            ArrayList<Cart> carts = (ArrayList<Cart>) getDatabaseHelper().getCartDao().queryForAll();
            if (carts.size() > 0) {
                mCurrentCart = carts.get(carts.size() - 1);
                mCartFoods.addAll(mCurrentCart.cartFoods);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        mCartFoodAdapter = new CartFoodViewAdapter(mContext, mCartFoods, mCartFoodVF, new CartFoodViewClickListener() {
            @Override
            public void onCartFoodClickListener(int position, View view, CartFood cartFood) {

            }

            @Override
            public void onCartFoodLongClickListener(final int position, View view, final CartFood cartFood) {
                new MaterialDialog.Builder(mContext).title(R.string.deletion).content(R.string.deletion_text)
                        .negativeText(R.string.cancel)
                        .positiveText(R.string.confirm)
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        })
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                try {
                                    getDatabaseHelper().getCartFoodDao().delete(cartFood);
                                    mCartFoodAdapter.deleteCartFood(position);
                                    updateOrderDisplay();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                    Toast.makeText(mContext, "Error", Toast.LENGTH_LONG).show();
                                }
                            }
                        }).build().show();
            }
        });
        mCartFoodRV.setNestedScrollingEnabled(false);
        mCartFoodRV.setAdapter(mCartFoodAdapter);

        updateOrderDisplay();
    }

    @Override
    protected void onResume() {
        super.onResume();

        (findViewById(R.id.control_space)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void updateOrderDisplay() {

        mTotalItem = 0;
        mTotalPrize = 0;

        for (CartFood cf : mCartFoods) {
            mTotalItem += cf.number;
            mTotalPrize += cf.price * cf.number;
        }

        mTotalItemTV.setText("" + mTotalItem);
        mTotalPrizeTV.setText(NumberFormat.getInstance().format(mTotalPrize) + " fcfa");
    }
}
