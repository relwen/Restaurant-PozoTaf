package iam.abdoulkader.pozotaf.service;

import java.util.ArrayList;

import iam.abdoulkader.pozotaf.data.Food;
import iam.abdoulkader.pozotaf.data.FoodCategory;
import iam.abdoulkader.pozotaf.data.Resto;
import iam.abdoulkader.pozotaf.data.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by root on 12/09/17.
 */

public interface PozotafWebService {

    public static final String ENDPOINT = "http://pozotaf.webvision-sarl.com/";
    public final String TARGET = "";

    @POST(TARGET + "/api/UserMobile/register")
    Call<User> userRegister(@Body User user);

    @POST(TARGET + "/api/UserMobile/update")
    Call<User> userUpdate(@Body User user);

    @FormUrlEncoded
    @POST(TARGET + "/api/UserMobile/login")
    Call<User> userLogin(@Field("email_or_phone") String email_or_phone,
                         @Field("password") String password);

    @FormUrlEncoded
    @POST(TARGET + "/api/UserMobile/refreshUser")
    Call<User> userRefresh(@Field("id") String id);

    @GET(TARGET + "/api/UserMobile/restos")
    Call<ArrayList<Resto>> getRestos();

    @GET(TARGET + "/api/UserMobile/foodCategories")
    Call<ArrayList<FoodCategory>> getFoodCategories();

    @GET(TARGET + "/api/UserMobile/foods")
    Call<ArrayList<Food>> getFoods();

    @GET(TARGET + "/api/UserMobile/foods/{food_category_id}")
    Call<ArrayList<Food>> getFoods(@Path("food_category_id") String food_category_id);

    @GET(TARGET + "/api/UserMobile/foodOfTheDay")
    Call<ArrayList<Food>> getFoodOfTheDay();

}
