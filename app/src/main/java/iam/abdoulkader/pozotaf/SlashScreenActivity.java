package iam.abdoulkader.pozotaf;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.q42.android.scrollingimageview.ScrollingImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;

import iam.abdoulkader.pozotaf.data.User;
import iam.abdoulkader.pozotaf.util.CommonTask;
import iam.abdoulkader.pozotaf.util.Constant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SlashScreenActivity extends BaseActivity {

    private RelativeLayout mContainer;
    private AnimationDrawable mAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slash_screen);

        mContainer = (RelativeLayout) findViewById(R.id.slash_container);

//        mAnim = (AnimationDrawable) mContainer.getBackground();
//        mAnim.setEnterFadeDuration(6000);
//        mAnim.setExitFadeDuration(2000);

        ScrollingImageView scrollingBackground1 = (ScrollingImageView) findViewById(R.id.siv_1);
        scrollingBackground1.stop();
        scrollingBackground1.start();

        ScrollingImageView scrollingBackground2 = (ScrollingImageView) findViewById(R.id.siv_2);
        scrollingBackground2.stop();
        scrollingBackground2.start();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mAnim != null && !mAnim.isRunning())
            mAnim.start();

        if(CommonTask.getUserID(mContext) != -1) {
            refresh(String.valueOf(CommonTask.getUserID(mContext)));
        } else {
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    CommonTask.openActivityAsFirst(mContext, SignActivity.class);
                }
            }, 5000);
        }
    }

    private void refresh(final String id) {

        Call<User> call = CommonTask.getWebService().userRefresh(id);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    try {
                        getDatabaseHelper().getUserDao().update(response.body());
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

                    try {
                        getDatabaseHelper().getUserDao().deleteById(Integer.valueOf(id));
                        CommonTask.getConcealPref(mContext).remove(Constant.USER_ID);
                        CommonTask.openActivityAsFirst(mContext, SignActivity.class);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable throwable) {
                Toast.makeText(mContext, R.string.error_occured, Toast.LENGTH_LONG).show();
                Log.d("MY_USER_FAIL", throwable.getMessage());
                CommonTask.openActivityAsFirst(mContext, MainActivity.class);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mAnim != null && mAnim.isRunning())
            mAnim.stop();
    }
}
