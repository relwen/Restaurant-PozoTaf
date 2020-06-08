package iam.abdoulkader.pozotaf.fragment;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import iam.abdoulkader.pozotaf.BaseActivity;
import iam.abdoulkader.pozotaf.R;
import iam.abdoulkader.pozotaf.database.DatabaseHelper;

public class BaseFragment extends Fragment {

    protected Context mContext;
    protected IActivityEnabledListener aeListener;
    private DatabaseHelper mDatabaseHelper = null;
    protected View mView;

    public BaseFragment() {
    }

    protected interface IActivityEnabledListener{
        void onActivityEnabled(FragmentActivity activity);
    }

    protected void getAvailableActivity(IActivityEnabledListener listener){
        if (getActivity() == null) {
            aeListener = listener;
        } else {
            listener.onActivityEnabled(getActivity());
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (aeListener != null){
            aeListener.onActivityEnabled((FragmentActivity) activity);
            aeListener = null;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (aeListener != null){
            aeListener.onActivityEnabled((FragmentActivity) context);
            aeListener = null;
        }
    }

    protected DatabaseHelper getDatabaseHelper() {

        if (mDatabaseHelper != null)
            return mDatabaseHelper;

        getAvailableActivity(new IActivityEnabledListener() {
            @Override
            public void onActivityEnabled(FragmentActivity activity) {
                mDatabaseHelper = ((BaseActivity)activity).getDatabaseHelper();
            }
        });

        return mDatabaseHelper;
    }
}
