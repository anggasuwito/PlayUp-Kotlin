package com.app.playup.menu.api

import com.app.playup.menu.model.MenuAccountModel
import com.app.playup.utils.Wrapper
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*
import java.io.File


interface MenuAccountAPI {
    @Multipart
    @POST("user/post-image")
    fun menuAccountChangePhoto(
        @Part image: MultipartBody.Part,
        @Part data: MultipartBody.Part
    ): Call<Wrapper>

    @GET("user/get-image/{id}")
    fun getUserPhoto(@Path("id") id: String): Call<ResponseBody>
}