package com.example.pomodoropucp.services;

import com.example.pomodoropucp.dto.Login;
import com.example.pomodoropucp.dto.Users;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface LoginService {
    @FormUrlEncoded
    @POST("/auth/login")
    Call<Users> verifiedUser(@Field("username") String username,
                             @Field("password") String password);




}
