package iam.abdoulkader.pozotaf.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import iam.abdoulkader.pozotaf.R;
import iam.abdoulkader.pozotaf.SignActivity;
import iam.abdoulkader.pozotaf.util.Constant;
import iam.abdoulkader.pozotaf.view.RippleView;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignHomeFragment extends BaseFragment {

    private RippleView mEmailSignInBtn;
    private RippleView mEmailSignUpBtn;

    public SignHomeFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_home, container, false);
        mEmailSignInBtn = (RippleView) view.findViewById(R.id.email_signin_btn);
        mEmailSignUpBtn = (RippleView) view.findViewById(R.id.email_signup_btn);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        mEmailSignInBtn.setOnClickListener(new View.OnClickListener() {
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

        mEmailSignUpBtn.setOnClickListener(new View.OnClickListener() {
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
    }
}
