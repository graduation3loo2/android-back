package com.example.myapplication.api;


import com.example.myapplication.models.LoginResponse;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api {

    @GET("trips")
    Call<List<Trip>> getTrips();

    @FormUrlEncoded
    @POST("users/")
    Call<ResponseBody> createUser(
            @Field("name") String name,
            @Field("password") String password,
            @Field("e_mail") String email,
            @Field("phone") String phone,
            @Field("city") String city

    );

    @FormUrlEncoded
    @POST("userlogin/")
    Call<LoginResponse> UserLogin(
            @Field("email") String email,
            @Field("password") String password
    );

}