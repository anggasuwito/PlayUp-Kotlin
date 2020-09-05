package com.app.playup.menu.api

import com.app.playup.menu.model.MenuAccountModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import java.io.File


interface MenuAccountAPI {
    @Multipart
    @POST("user/post-image")
    fun menuAccountChangePhoto(@Part image: MultipartBody.Part,@Part data :MultipartBody.Part): Call<MenuAccountModel>
}