package iam.abdoulkader.pozotaf.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.thebrownarrow.customfont.CustomFontButton;
import com.thebrownarrow.customfont.CustomFontEditText;
import com.thebrownarrow.customfont.CustomFontTextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.SQLException;

import butterknife.BindView;
import butterknife.ButterKnife;
import iam.abdoulkader.pozotaf.MainActivity;
import iam.abdoulkader.pozotaf.R;
import iam.abdoulkader.pozotaf.SignActivity;
import iam.abdoulkader.pozotaf.data.User;
import iam.abdoulkader.pozotaf.util.CommonTask;
import iam.abdoulkader.pozotaf.util.Constant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends BaseFragment {


    @BindView(R.id.control_space) RelativeLayout mBackBtn;
    @BindView(R.id.input_full_name) CustomFontEditText mFullNameET;
    @BindView(R.id.input_phone) CustomFontEditText mPhoneET;
    @BindView(R.id.input_email) CustomFontEditText mEmailET;
    @BindView(R.id.input_password) CustomFontEditText mPwdET;
    @BindView(R.id.btn_to_sign_up) CustomFontButton mSignUpBtn;
    @BindView(R.id.sign_in_now) CustomFontTextView mSignInNow;
    @BindView(R.id.progress_bar) ProgressBar mProgressBar;

    private User mUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getContext();
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_sign_up, container, false);
        ButterKnife.bind(this, mView);
        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAvailableActivity(new IActivityEnabledListener() {
                    @Override
                    public void onActivityEnabled(FragmentActivity activity) {
                        ((SignActivity)activity).mCurrentFragmentID = 0;
                        activity.getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, ((SignActivity) activity).mSignHomeFragment).commit();
                    }
                });
            }
        });

        mSignInNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAvailableActivity(new IActivityEnabledListener() {
                    @Override
                    public void onActivityEnabled(FragmentActivity activity) {
                        ((SignActivity)activity).mCurrentFragmentID = 2;
                        activity.getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, ((SignActivity) activity).mSignInFragment).commit();
                    }
                });
            }
        });

        mSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String full_name = mFullNameET.getText().toString();
                String email = mEmailET.getText().toString();
                String phone = mPhoneET.getText().toString();
                String pwd = mPwdET.getText().toString();

                if (!full_name.isEmpty() && CommonTask.isValidPhoneNumer(phone)) {
                    mUser = new User(CommonTask.generateID(), full_name, phone, email, pwd, 0);
                    register(mUser);
                } else {
                    Toast.makeText(mContext, "****", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void register(User user) {

        mSignUpBtn.setEnabled(false);
        mProgressBar.setVisibility(View.VISIBLE);
        Call<User> call = CommonTask.getWebService().userRegister(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 201) {
                    try {
                        getDatabaseHelper().getUserDao().create(response.body());
                        CommonTask.setUserID(mContext, response.body().id);
                        CommonTask.openActivityAsFirst(mContext, MainActivity.class);
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

                mSignUpBtn.setEnabled(true);
                mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<User> call, Throwable throwable) {
                Toast.makeText(mContext, R.string.error_occured, Toast.LENGTH_LONG).show();
                Log.d("MY_USER_FAIL", throwable.getMessage());
                mSignUpBtn.setEnabled(true);
                mProgressBar.setVisibility(View.GONE);
            }
        });
    }
}
