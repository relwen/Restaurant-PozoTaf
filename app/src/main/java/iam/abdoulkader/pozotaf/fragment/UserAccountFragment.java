package iam.abdoulkader.pozotaf.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.thebrownarrow.customfont.CustomFontButton;
import com.thebrownarrow.customfont.CustomFontEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import iam.abdoulkader.pozotaf.BaseActivity;
import iam.abdoulkader.pozotaf.MainActivity;
import iam.abdoulkader.pozotaf.R;
import iam.abdoulkader.pozotaf.SignActivity;
import iam.abdoulkader.pozotaf.adapter.CartAddressViewAdapter;
import iam.abdoulkader.pozotaf.adapter.CartAddressViewAdapter;
import iam.abdoulkader.pozotaf.adapter.SmartFragmentStatePagerAdapter;
import iam.abdoulkader.pozotaf.data.Address;
import iam.abdoulkader.pozotaf.data.CartAddress;
import iam.abdoulkader.pozotaf.data.CartAddress;
import iam.abdoulkader.pozotaf.data.User;
import iam.abdoulkader.pozotaf.listener.CartAddressViewClickListener;
import iam.abdoulkader.pozotaf.util.CommonTask;
import iam.abdoulkader.pozotaf.util.Constant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static iam.abdoulkader.pozotaf.util.Constant.PLACE_PICKER_REQUEST;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserAccountFragment extends BaseFragment {

    @BindView(R.id.tabs) TabLayout mTabLayout;
    @BindView(R.id.main_view_flipper) ViewFlipper mMainVF;
    @BindView(R.id.user_info_viewflipper) ViewFlipper mUserInfoVF;
    @BindView(R.id.update_user_info_btn) CustomFontButton mUpdateUserInfoBtn;
    @BindView(R.id.save_user_info_btn) CustomFontButton mSaveUserInfoBtn;
    @BindView(R.id.input_full_name) CustomFontEditText mFullNameET;
    @BindView(R.id.input_phone) CustomFontEditText mPhoneET;
    @BindView(R.id.input_email) CustomFontEditText mEmailET;
    @BindView(R.id.input_password) CustomFontEditText mPasswdET;
    @BindView(R.id.address_viewflipper) ViewFlipper mCartAddressVF;
    @BindView(R.id.address_recyclerview) RecyclerView mCartAddressRV;
    @BindView(R.id.add_address_btn) CustomFontButton mAddAddressBtn;
    @BindView(R.id.sign_out_btn) CustomFontButton mSignOutBtn;

    private ArrayList<CartAddress> mCartAddresss;
    private CartAddressViewAdapter mCartAddressAdapter;

    private User mUser;

    public UserAccountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_user_account, container, false);
        mContext = getContext();
        ButterKnife.bind(this, mView);

        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.user_details));
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.favorite_addresses));
        mTabLayout.setTabTextColors(getResources().getColor(R.color.black),
                getResources().getColor(R.color.colorAccent));
        mTabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorAccent));

        try {
            mUser = getDatabaseHelper().getUserDao().queryForId(CommonTask.getUserID(mContext));
            mFullNameET.setText(mUser.full_name);
            mPhoneET.setText(mUser.phone);
            mEmailET.setText(mUser.email);
            mPasswdET.setText(mUser.password);

            mCartAddresss = (ArrayList<CartAddress>) getDatabaseHelper().getCartAddressDao().queryForAll();

        } catch (SQLException e) {
            e.printStackTrace();
            mCartAddresss = new ArrayList<>();
        }
        mCartAddressAdapter = new CartAddressViewAdapter(mContext, mCartAddresss, mCartAddressVF, new CartAddressViewClickListener() {
            @Override
            public void onCartAddressClickListener(int position, View view, CartAddress cartAddress) {

            }

            @Override
            public void onCartAddressLongClickListener(final int position, View view, final CartAddress cartAddress) {
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
                                    getDatabaseHelper().getCartAddressDao().delete(cartAddress);
                                    mCartAddressAdapter.deleteCartAddress(position);
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                    Toast.makeText(mContext, "Error", Toast.LENGTH_LONG).show();
                                }
                            }
                        }).build().show();
            }
        });
        mCartAddressRV.setNestedScrollingEnabled(false);
        mCartAddressRV.setAdapter(mCartAddressAdapter);
        
        return mView;
    }

    private void toogleUserInfoInput(boolean b) {
        mFullNameET.setEnabled(b);
        mPhoneET.setEnabled(b);
        mEmailET.setEnabled(b);
        mPasswdET.setEnabled(b);
    }

    @Override
    public void onResume() {
        super.onResume();

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mMainVF.setDisplayedChild(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        toogleUserInfoInput(false);

        mAddAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mAddAddressBtn.setEnabled(false);

                try {
                    PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();
                    Intent intent = intentBuilder.build(getActivity());
                    // Start the Intent by requesting a result, identified by a request code.
                    startActivityForResult(intent, Constant.PLACE_PICKER_REQUEST);

                } catch (GooglePlayServicesRepairableException e) {
                    GooglePlayServicesUtil
                            .getErrorDialog(e.getConnectionStatusCode(), getActivity(), 0);
                    mAddAddressBtn.setEnabled(true);
                } catch (GooglePlayServicesNotAvailableException e) {
                    Toast.makeText(getActivity(), "Google Play Services error.",
                            Toast.LENGTH_LONG)
                            .show();
                    mAddAddressBtn.setEnabled(true);
                }
            }
        });

        mUpdateUserInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toogleUserInfoInput(true);
                mUserInfoVF.setDisplayedChild(1);
            }
        });

        mSaveUserInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // update on server and db
                String full_name = mFullNameET.getText().toString();
                String email = mEmailET.getText().toString();
                String phone = mPhoneET.getText().toString();
                String pwd = mPasswdET.getText().toString();

                if (!full_name.isEmpty() && CommonTask.isValidPhoneNumer(phone)) {
                    User user = new User(mUser.id, full_name, phone, email, pwd, 0);
                    update(user);
                } else {
                    Toast.makeText(mContext, "****", Toast.LENGTH_LONG).show();
                }
            }
        });

        mSignOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    getDatabaseHelper().recreate();
                    //getDatabaseHelper().getUserDao().delete(mUser);
                    CommonTask.getConcealPref(mContext).remove(Constant.USER_ID);
                    CommonTask.openActivityAsFirst(mContext, SignActivity.class);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constant.PLACE_PICKER_REQUEST) {

            mAddAddressBtn.setEnabled(true);

            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, mContext);

                CartAddress cartAddress = new CartAddress(new Address(place.getId(), place.getName().toString(), place.getAddress().toString(), place.getLatLng().longitude, place.getLatLng().latitude), null);

                try {
                    getDatabaseHelper().getCartAddressDao().create(cartAddress);
                    mCartAddressAdapter.addCartAddress(mCartAddresss.size(), cartAddress);
                } catch (SQLException e) {
                    e.printStackTrace();
                    Toast.makeText(mContext, "Error", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private void update(User user) {

        Call<User> call = CommonTask.getWebService().userUpdate(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    try {
                        getDatabaseHelper().getUserDao().update(response.body());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        String msg = response.errorBody().string();
                        JSONObject jo = new JSONObject(msg);
                        Log.d("MYUSER_ERROR", msg.toString());
                        Toast.makeText(mContext, jo.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                toogleUserInfoInput(false);
                mUserInfoVF.setDisplayedChild(0);

            }

            @Override
            public void onFailure(Call<User> call, Throwable throwable) {
                Toast.makeText(mContext, R.string.error_occured, Toast.LENGTH_LONG).show();
                Log.d("MY_USER_FAIL", throwable.getMessage());

                toogleUserInfoInput(false);
                mUserInfoVF.setDisplayedChild(0);
            }
        });
    }
}
