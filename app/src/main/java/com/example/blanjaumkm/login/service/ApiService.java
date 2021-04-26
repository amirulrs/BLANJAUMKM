package com.example.blanjaumkm.login.service;

import com.example.blanjaumkm.login.model.Success;
import com.example.blanjaumkm.login.model.Token;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {

    @POST("register")
    @FormUrlEncoded
    Call<Success> daftar(
            @Field("nama") String nama,
            @Field("email") String email,
            @Field("telp") String telp,
            @Field("pass") String pass
           
    );

    @POST("login")
    @FormUrlEncoded
    Call<Success> login(
            @Field("email") String email,
            @Field("password") String password
    );




    @POST("refresh")
    @FormUrlEncoded
    Call<Token> refresh(@Field("refresh_token") String refreshToken
    );




    @POST("logout")
    Call<Token> logout();
}
