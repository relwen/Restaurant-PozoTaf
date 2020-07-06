package iam.abdoulkader.pozotaf.service;

import iam.abdoulkader.pozotaf.data.JsonResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RequestInterface {

    @FormUrlEncoded
    @POST("api/UserMobile/login")
    Call<JsonResponse> login(
      @Field("email_or_phone") String email_or_phone,
      @Field("password") String password
    );


    @FormUrlEncoded
    @POST("api/UserMobile/register")
    Call<JsonResponse> register(
      @Field("full_name") String full_name,
      @Field("email") String email,
      @Field("phone") String phone,
      @Field("password") String password
    );




}
