package com.app.playup.menu.repository

import android.app.Activity
import android.graphics.BitmapFactory
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import com.app.playup.menu.api.MenuAccountAPI
import com.app.playup.menu.model.MenuAccountDataModel
import com.app.playup.menu.model.MenuAccountModel
import com.app.playup.user.model.UserLoginResponseDataModel
import com.app.playup.utils.Wrapper
import com.bumptech.glide.Glide
import com.google.gson.Gson
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


class MenuAccountRepository @Inject constructor(val menuAccountAPI: MenuAccountAPI) {
    var menuAccountResponseData = MutableLiveData<MenuAccountDataModel>()
    fun menuAccountChangePhoto(image: MultipartBody.Part, data: MultipartBody.Part) {
        menuAccountAPI.menuAccountChangePhoto(image, data)
            .enqueue(object : Callback<Wrapper> {
                override fun onFailure(call: Call<Wrapper>, t: Throwable) {
                    t.printStackTrace()
                    println("RESPONSE = FAIL CHANGE PHOTO")
                }

                override fun onResponse(
                    call: Call<Wrapper>,
                    response: Response<Wrapper>
                ) {
                    val response = response.body()
                    val stringResponseData = Gson().toJson(response?.data)
                    val menuAccountDataResponseObject =
                        Gson().fromJson<MenuAccountDataModel>(
                            stringResponseData,
                            MenuAccountDataModel::class.java
                        )
                    menuAccountResponseData.value = menuAccountDataResponseObject
                }
            })
    }

    fun getUserPhoto(id: String, imageContainer: ImageView, activity: Activity) {
        menuAccountAPI.getUserPhoto(id).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.code() != 404) {
                    val responseImage = BitmapFactory.decodeStream(response.body()!!.byteStream())
                    Glide.with(activity).asBitmap().load(responseImage).into(imageContainer)
                }
            }
        })
    }
}