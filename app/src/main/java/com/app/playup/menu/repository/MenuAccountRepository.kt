package com.app.playup.menu.repository

import android.app.Activity
import android.graphics.BitmapFactory
import android.widget.ImageView
import com.app.playup.menu.api.MenuAccountAPI
import com.app.playup.menu.model.MenuAccountModel
import com.bumptech.glide.Glide
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


class MenuAccountRepository @Inject constructor(val menuAccountAPI: MenuAccountAPI) {

    fun menuAccountChangePhoto(image: MultipartBody.Part, data: MultipartBody.Part) {
        menuAccountAPI.menuAccountChangePhoto(image, data)
            .enqueue(object : Callback<MenuAccountModel> {
                override fun onFailure(call: Call<MenuAccountModel>, t: Throwable) {
                    t.printStackTrace()
                    println("RESPONSE = FAIL CHANGE PHOTO")
                }

                override fun onResponse(
                    call: Call<MenuAccountModel>,
                    response: Response<MenuAccountModel>
                ) {
                    println("RESPONSE = SUCCESS CHANGE PHOTO")
                }
            })
    }

    fun getUserPhoto(id: String, imageContainer: ImageView, activity: Activity) {
        menuAccountAPI.getUserPhoto(id).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                val responseImage = BitmapFactory.decodeStream(response.body()!!.byteStream())
                Glide.with(activity).asBitmap().load(responseImage).into(imageContainer)
            }
        })
    }
}