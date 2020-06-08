package iam.abdoulkader.pozotaf;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import iam.abdoulkader.pozotaf.database.DatabaseHelper;

/**
 * Created by root on 16/04/18.
 */

public class BaseActivity extends AppCompatActivity {

    protected Context mContext;
    protected Toolbar mToolbar;
    private DatabaseHelper mDatabaseHelper = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;
    }

    public DatabaseHelper getDatabaseHelper() {
        if (mDatabaseHelper == null) {
            mDatabaseHelper = OpenHelperManager.getHelper(mContext,DatabaseHelper.class);
        }
        return mDatabaseHelper;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
}
