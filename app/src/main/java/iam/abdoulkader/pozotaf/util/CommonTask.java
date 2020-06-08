package iam.abdoulkader.pozotaf.util;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Point;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.facebook.crypto.CryptoConfig;
import com.zeroone.conceal.ConcealPrefRepository;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import iam.abdoulkader.pozotaf.service.PozotafWebService;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by root on 30/03/18.
 */

public class CommonTask {

    public static int generateID() {
        Random rand = new Random();
        return rand.nextInt(1000000);
    }

    public static ConcealPrefRepository getConcealPref(Context context) {

        return new ConcealPrefRepository.PreferencesBuilder(context)
                .useDefaultPrefStorage()
                .sharedPrefsBackedKeyChain(CryptoConfig.KEY_256)  //CryptoConfig.KEY_256 or CryptoConfig.KEY_128
                .enableCrypto(true,true) //param 1 - enable value encryption , param 2 - enable key encryption
                .createPassword("derokabore@gmail.com") //default value - BuildConfig.APPLICATION_ID
                .setFolderName("Pozotaf") //create Folder for data stored: default is - "conceal_path"
                .create();
    }

    public static void setUserID(Context context, int id) {
        CommonTask.getConcealPref(context).putInt(Constant.USER_ID, id);
    }

    public static int getUserID(Context context) {
        return CommonTask.getConcealPref(context).getInt(Constant.USER_ID, -1);
    }

    public static void openActivityAsFirst(Context context, Class<?> myClass) {

        Intent intent = new Intent(context, myClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    public static void openActivityAsFirst(Context context, Intent intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    public static void openActivity(Context context, Class<?> myClass) {

        Intent intent = new Intent(context, myClass);
        context.startActivity(intent);
    }

    public static void openActivity(Context context, Intent intent) {
        context.startActivity(intent);
    }

    //==================================================================

    public static PozotafWebService getWebService() {

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10000, TimeUnit.SECONDS)
                .readTimeout(10000, TimeUnit.SECONDS).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PozotafWebService.ENDPOINT)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(PozotafWebService.class);
    }

    //==================================================================

    public static Point ScreemSize(Context context) {

        WindowManager windowManager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        return size;

    }

    public static int dpToPixel(int dp, Context contex) {
        Resources resources = contex.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return dp * ((int)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    public static boolean isValidPhoneNumer(String phone) {
        return !phone.isEmpty() && phone.length() == 8;
    }

    public static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
