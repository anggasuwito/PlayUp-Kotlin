package com.app.playup.menu.repository

import com.app.playup.menu.api.MenuAccountAPI
import com.app.playup.menu.model.MenuAccountModel
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import javax.inject.Inject

class MenuAccountRepository @Inject constructor(val menuAccountAPI: MenuAccountAPI) {

    fun menuAccountChangePhoto(image: MultipartBody.Part,data:MultipartBody.Part) {
        menuAccountAPI.menuAccountChangePhoto(image,data)
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
}