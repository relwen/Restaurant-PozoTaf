package iam.abdoulkader.pozotaf.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignInFragment extends BaseFragment {

    @BindView(R.id.control_space) RelativeLayout mBackBtn;
    @BindView(R.id.input_email_or_phone) CustomFontEditText mEmailOrPhoneET;
    @BindView(R.id.input_password) CustomFontEditText mPwdET;
    @BindView(R.id.btn_to_sign_in) CustomFontButton mSignInBtn;
    @BindView(R.id.sign_up_now) CustomFontTextView mSignUpNow;
    @BindView(R.id.progress_bar) ProgressBar mProgressBar;


    public SignInFragment() {
        super();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_sign_in, container, false);
        mContext = getContext();
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

        mSignUpNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAvailableActivity(new IActivityEnabledListener() {
                    @Override
                    public void onActivityEnabled(FragmentActivity activity) {
                        ((SignActivity)activity).mCurrentFragmentID = 1;
                        activity.getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, ((SignActivity) activity).mSignUpFragment).commit();
                    }
                });
            }
        });

        mSignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email_or_phone = mEmailOrPhoneET.getText().toString();
                String pwd = mPwdET.getText().toString();

                if (!email_or_phone.isEmpty() && !pwd.isEmpty()) {
                    login(email_or_phone, pwd);
                } else {
                    Toast.makeText(mContext, "****", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void login(String email_or_phone, String pwd) {

        mSignInBtn.setEnabled(false);
        mProgressBar.setVisibility(View.VISIBLE);
        Call<User> call = CommonTask.getWebService().userLogin(email_or_phone, pwd);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
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

                mSignInBtn.setEnabled(true);
                mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<User> call, Throwable throwable) {
                Toast.makeText(mContext, R.string.error_occured, Toast.LENGTH_LONG).show();
                Log.d("MY_USER_FAIL", throwable.getMessage());
                mSignInBtn.setEnabled(true);
                mProgressBar.setVisibility(View.GONE);
            }
        });
    }
}
