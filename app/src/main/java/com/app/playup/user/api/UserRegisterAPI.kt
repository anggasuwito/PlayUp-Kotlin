package com.app.playup.user.api

import com.app.playup.user.model.UserRegisterModel
import com.app.playup.utils.Wrapper
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UserRegisterAPI {
    @POST("user/register")
    fun registerNewUser(@Body userRegisterModel: UserRegisterModel): Call<Wrapper>

    @POST("user/update")
    fun updateUserProfil(@Body userRegisterModel: UserRegisterModel):Call<Wrapper>
}