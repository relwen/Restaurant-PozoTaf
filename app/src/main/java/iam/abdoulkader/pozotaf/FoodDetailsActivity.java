package iam.abdoulkader.pozotaf;

import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.thebrownarrow.customfont.CustomFontButton;
import com.thebrownarrow.customfont.CustomFontEditText;
import com.thebrownarrow.customfont.CustomFontTextView;

import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;

import iam.abdoulkader.pozotaf.data.Cart;
import iam.abdoulkader.pozotaf.data.CartFood;
import iam.abdoulkader.pozotaf.data.Food;
import iam.abdoulkader.pozotaf.database.DatabaseHelper;
import iam.abdoulkader.pozotaf.service.PozotafWebService;
import iam.abdoulkader.pozotaf.util.CommonTask;
import iam.abdoulkader.pozotaf.util.Constant;
import iam.abdoulkader.pozotaf.view.RippleView;

public class FoodDetailsActivity extends BaseActivity {

    private Food mFood;
    private ImageView mFoodIV;
    private ImageView mFoodNotHalalIV;
    private CustomFontTextView mFoodNameTV;
    private CustomFontTextView mFoodPriceTV;
    private CustomFontTextView mFoodDescTV;
    private CustomFontButton mAddBtn;
    private CustomFontButton mSubstractBtn;
    private CustomFontTextView mCountTV;
    private CustomFontEditText mMessageForFoodET;
    private RippleView mAddToCartBtn;
    private LinearLayout mMiniCartL;
    private CustomFontTextView mTotalItemTV;
    private CustomFontTextView mTotalPrizeTV;

    private int mCount;
    private int mTotalItem;
    private int mTotalPrize;

    private Cart mCurrentCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.black));
        }

        setContentView(R.layout.activity_food_details);

        if (getIntent() != null) {
            mFood = getIntent().getParcelableExtra(Constant.SELECTED_FOOD);
        }

        mFoodIV = (ImageView) findViewById(R.id.image);
        mFoodNotHalalIV = (ImageView) findViewById(R.id.non_halal);
        mFoodNameTV = (CustomFontTextView) findViewById(R.id.name);
        mFoodPriceTV = (CustomFontTextView) findViewById(R.id.price);
        mFoodDescTV = (CustomFontTextView) findViewById(R.id.desc);
        mAddBtn = (CustomFontButton) findViewById(R.id.add);
        mSubstractBtn = (CustomFontButton) findViewById(R.id.substract);
        mCountTV = (CustomFontTextView) findViewById(R.id.count);
        mMessageForFoodET = (CustomFontEditText) findViewById(R.id.msg_edit_text);
        mAddToCartBtn = (RippleView) findViewById(R.id.add_to_cart_btn);
        mMiniCartL = (LinearLayout) findViewById(R.id.min_cart_layout);
        mTotalItemTV = (CustomFontTextView) findViewById(R.id.total_cart_items);
        mTotalPrizeTV = (CustomFontTextView) findViewById(R.id.total_prize);

        mCount = 1;
        mCountTV.setText("" + mCount);

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mFood == null)
            ;

        updateCartDisplay();

        mFoodNameTV.setText(mFood.name);
        mFoodPriceTV.setText(NumberFormat.getInstance().format(mFood.price) + " fcfa");
        mFoodDescTV.setText(mFood.desc);
        Glide.with(mContext).load(PozotafWebService.ENDPOINT + PozotafWebService.TARGET + "/" + mFood.image).into(mFoodIV);
        if (mFood.is_non_halal)
            Glide.with(mContext).load(R.drawable.non_halal_sign).into(mFoodNotHalalIV);

        (findViewById(R.id.control_space)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCount >= Constant.MAX_FOOD_COUNT) {
                    new MaterialDialog.Builder(mContext)
                            .title("Remarques")
                            .content("Pour commander plus de " + mCount + " plats de "
                                    + mFood.name + " veuilliez nous appeler pour confirmation.")
                            .positiveText("Appeler maintenant")
                            .negativeText("Fermer")
                            .show();

                    return;
                }

                mCount++;
                mCountTV.setText("" + mCount);
            }
        });

        mSubstractBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCount <= 1) {
                    return;
                }
                mCount--;
                mCountTV.setText("" + mCount);
            }
        });

        mAddToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    // add to cart now
                    CartFood cartFood = new CartFood(mFood, mCount, mMessageForFoodET.getText().toString(), null, null, null);
                    cartFood.cart = mCurrentCart;
                    getDatabaseHelper().getCartFoodDao().create(cartFood);

                    Toast.makeText(mContext, "Ajouter avec succes au panier", Toast.LENGTH_LONG).show();

                } catch (SQLException e) {
                    e.printStackTrace();
                }

                updateCartDisplay();
            }
        });

        mMiniCartL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommonTask.openActivity(mContext, CartActivity.class);
            }
        });
    }

    private void updateCartDisplay() {

        mTotalItem = 0;
        mTotalPrize = 0;

        try {
            ArrayList<Cart> carts = (ArrayList<Cart>) getDatabaseHelper().getCartDao().queryForAll();
            if (carts.size() == 0) {
                mCurrentCart = new Cart(0, null, null, null);
                getDatabaseHelper().getCartDao().create(mCurrentCart);
            } else {
                mCurrentCart = carts.get(carts.size() - 1);
                for (CartFood cf : mCurrentCart.cartFoods) {
                    mTotalItem += cf.number;
                    mTotalPrize += cf.price * cf.number;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        mTotalItemTV.setText("" + mTotalItem);
        mTotalPrizeTV.setText(NumberFormat.getInstance().format(mTotalPrize) + " fcfa");
    }
}
