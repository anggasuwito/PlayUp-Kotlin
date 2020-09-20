package com.app.playup.user.api

import com.app.playup.user.model.UserRegisterModel
import com.app.playup.utils.Wrapper
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT

interface UserRegisterAPI {
    @POST("user/register")
    fun registerNewUser(@Body userRegisterModel: UserRegisterModel): Call<Wrapper>

    @PUT("user/update")
    fun updateUserProfil(@Body userRegisterModel: UserRegisterModel):Call<Wrapper>
}