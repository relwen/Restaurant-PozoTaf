package iam.abdoulkader.pozotaf;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import iam.abdoulkader.pozotaf.fragment.BaseFragment;
import iam.abdoulkader.pozotaf.fragment.SignHomeFragment;
import iam.abdoulkader.pozotaf.fragment.SignInFragment;
import iam.abdoulkader.pozotaf.fragment.SignUpFragment;

public class SignActivity extends BaseActivity {

    public SignHomeFragment mSignHomeFragment;
    public SignUpFragment mSignUpFragment;
    public SignInFragment mSignInFragment;

    public int mCurrentFragmentID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mSignHomeFragment == null)
            mSignHomeFragment = new SignHomeFragment();

        if (mSignUpFragment == null)
            mSignUpFragment = new SignUpFragment();

        if (mSignInFragment == null)
            mSignInFragment = new SignInFragment();

        /*--------------------------------------*/

        if (mCurrentFragmentID == 0)
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, mSignHomeFragment).commit();

        if (mCurrentFragmentID == 1)
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, mSignUpFragment).commit();

        if (mCurrentFragmentID == 2)
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, mSignInFragment).commit();
    }


}
