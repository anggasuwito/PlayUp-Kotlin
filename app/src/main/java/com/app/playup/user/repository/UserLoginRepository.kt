package com.app.playup.user.repository

import androidx.lifecycle.MutableLiveData
import com.app.playup.user.api.UserLoginAPI
import com.app.playup.user.api.UserRegisterAPI
import com.app.playup.user.model.UserLoginModel
import com.app.playup.user.model.UserRegisterModel
import com.app.playup.utils.Wrapper
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class UserLoginRepository  @Inject constructor(val userLoginAPI: UserLoginAPI) {
    var userLoginResponse = MutableLiveData<Wrapper>()

    fun loginUser(userLoginModel: UserLoginModel) {
        userLoginAPI.loginUser(userLoginModel).enqueue(object : Callback<Wrapper> {
            override fun onFailure(call: Call<Wrapper>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<Wrapper>, response: Response<Wrapper>) {
                val response = response.body()
                val stringResponse = Gson().toJson(response)
                val userLoginResponseObject =
                    Gson().fromJson<Wrapper>(stringResponse, Wrapper::class.java)
                userLoginResponse.value = userLoginResponseObject
            }
        })
    }
}