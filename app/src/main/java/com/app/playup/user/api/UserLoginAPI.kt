package com.app.playup.user.api

import com.app.playup.user.model.UserLoginModel
import com.app.playup.user.model.UserRegisterModel
import com.app.playup.utils.Wrapper
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserLoginAPI {
    @POST("user/login")
    fun loginUser(@Body userLoginModel: UserLoginModel): Call<Wrapper>

    @GET("user/detail/{id}")
    fun getUserById(@Path("id") id: String): Call<Wrapper>
}