package com.app.playup.user.api

import com.app.playup.user.model.UserLoginModel
import com.app.playup.user.model.UserRegisterModel
import com.app.playup.utils.Wrapper
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UserLoginAPI {
    @POST("user/login")
    fun loginUser(@Body userLoginModel: UserLoginModel): Call<Wrapper>
}