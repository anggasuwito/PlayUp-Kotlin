package com.app.playup.user.repository

import androidx.lifecycle.MutableLiveData
import com.app.playup.user.api.UserRegisterAPI
import com.app.playup.user.model.UserRegisterModel
import com.app.playup.utils.Wrapper
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class UserRegisterRepository @Inject constructor(val userRegisterAPI: UserRegisterAPI) {
    var userRegisterResponse = MutableLiveData<Wrapper>()

    fun registerNewUser(userRegisterModel: UserRegisterModel) {
        userRegisterAPI.registerNewUser(userRegisterModel).enqueue(object : Callback<Wrapper> {
            override fun onFailure(call: Call<Wrapper>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<Wrapper>, response: Response<Wrapper>) {
                val response = response.body()
                val stringResponse = Gson().toJson(response)
                val userRegisterResponseObject =
                    Gson().fromJson<Wrapper>(stringResponse, Wrapper::class.java)
                userRegisterResponse.value = userRegisterResponseObject
            }
        })
    }
}