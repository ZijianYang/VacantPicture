package com.yzj.vacantpicture.service.user;

import com.yzj.vacantpicture.service.ServiceResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserInfoService {
    @POST("UserInfo")
    Call<ServiceResponse> login(@Body UserLoginRequest body);
}
