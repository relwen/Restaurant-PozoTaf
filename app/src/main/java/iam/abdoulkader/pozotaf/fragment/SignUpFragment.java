package iam.abdoulkader.pozotaf.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
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
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import iam.abdoulkader.pozotaf.MainActivity;
import iam.abdoulkader.pozotaf.R;
import iam.abdoulkader.pozotaf.SignActivity;
import iam.abdoulkader.pozotaf.data.JsonResponse;
import iam.abdoulkader.pozotaf.data.User;
import iam.abdoulkader.pozotaf.service.RequestInterface;
import iam.abdoulkader.pozotaf.util.CommonTask;
import iam.abdoulkader.pozotaf.util.Constant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends BaseFragment {

    String full_name,phone,pwd,email;


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
                 full_name = mFullNameET.getText().toString();
                 email = mEmailET.getText().toString();
                 phone = mPhoneET.getText().toString();
                 pwd = mPwdET.getText().toString();

                if (!full_name.isEmpty() && CommonTask.isValidPhoneNumer(phone)) {
                    mUser = new User(CommonTask.generateID(), full_name, phone, email, pwd, 0);
                    register();
                } else {
                    Toast.makeText(mContext, "****", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void register() {

        mSignUpBtn.setEnabled(false);
        mProgressBar.setVisibility(View.VISIBLE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://pozotaf.webvision-sarl.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Call<JsonResponse> call=retrofit.create(RequestInterface.class).register(
                full_name,email,phone,pwd
        );

        //Call<User> call = CommonTask.getWebService().userLogin(email_or_phone, pwd);
        call.enqueue(new Callback<JsonResponse>() {
            @Override
            public void onResponse(Call<JsonResponse> call, Response<JsonResponse> response) {
                if (response.isSuccessful()) {


                    JsonResponse reponse = response.body();
                    int success = reponse.getCode();

                    if (success == 200) {
                        //getDatabaseHelper().getUserDao().create(response.body());
                        //CommonTask.setUserID(mContext, response.body().id);
                        Intent i = new Intent(getContext(), MainActivity.class);
                        startActivity(i);
                        //CommonTask.openActivityAsFirst(mContext, MainActivity.class);
                    } else if (success == 201) {
                        Toast.makeText(getContext(), "Le numéro de téléphone existe dejà", Toast.LENGTH_SHORT).show();
                    } else if (success == 202) {
                        Toast.makeText(getContext(), "L'adresse email existe dejà", Toast.LENGTH_SHORT).show();
                    }

                    mSignUpBtn.setEnabled(true);
                    mProgressBar.setVisibility(View.GONE);
                }
            }
                @Override
                public void onFailure (Call < JsonResponse > call, Throwable throwable){
                    Toast.makeText(mContext, R.string.error_occured, Toast.LENGTH_LONG).show();
                    Log.d("MY_USER_FAIL", throwable.getMessage());
                    mSignUpBtn.setEnabled(true);
                    mProgressBar.setVisibility(View.GONE);
                }

        });
    }
}
