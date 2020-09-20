package com.app.playup.user.api

import com.app.playup.user.model.UserLoginModel
import com.app.playup.user.model.UserLoginResponseDataModel
import com.app.playup.user.model.UserRegisterModel
import com.app.playup.utils.Wrapper
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface UserLoginAPI {
    @POST("user/login")
    fun loginUser(@Body userLoginModel: UserLoginModel): Call<Wrapper>

    @GET("user/detail/{id}")
    fun getUserById(@Path("id") id: String): Call<Wrapper>

    @POST("user/login/google")
    fun loginWithGoogle(@Body userLoginModel: UserLoginModel): Call<Wrapper>

    @POST("user/login/facebook")
    fun loginWithFacebook(@Body userLoginModel: UserLoginModel): Call<Wrapper>

    @PUT("user/update/google")
    fun updateGoogleAccount(@Body userLoginResponseDataModel: UserLoginResponseDataModel):Call<Wrapper>

    @PUT("user/update/facebook")
    fun updateFacebookAccount(@Body userLoginResponseDataModel: UserLoginResponseDataModel):Call<Wrapper>
}