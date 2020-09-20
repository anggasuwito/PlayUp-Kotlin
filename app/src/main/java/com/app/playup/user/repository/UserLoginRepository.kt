package com.app.playup.user.repository

import androidx.lifecycle.MutableLiveData
import com.app.playup.user.api.UserLoginAPI
import com.app.playup.user.api.UserRegisterAPI
import com.app.playup.user.model.UserLoginModel
import com.app.playup.user.model.UserLoginResponseDataModel
import com.app.playup.user.model.UserRegisterModel
import com.app.playup.utils.Wrapper
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Path
import javax.inject.Inject

class UserLoginRepository @Inject constructor(val userLoginAPI: UserLoginAPI) {
    var userLoginResponse = MutableLiveData<Wrapper>()
    var userLoginResponseData = MutableLiveData<UserLoginResponseDataModel>()

    fun loginUser(userLoginModel: UserLoginModel) {
        userLoginAPI.loginUser(userLoginModel).enqueue(object : Callback<Wrapper> {
            override fun onFailure(call: Call<Wrapper>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<Wrapper>, response: Response<Wrapper>) {
                val response = response.body()
                val stringResponse = Gson().toJson(response)
                val stringResponseData = Gson().toJson(response?.data)
                val userLoginResponseObject =
                    Gson().fromJson<Wrapper>(stringResponse, Wrapper::class.java)
                val userLoginResponseDataObject =
                    Gson().fromJson<UserLoginResponseDataModel>(
                        stringResponseData,
                        UserLoginResponseDataModel::class.java
                    )
                userLoginResponse.value = userLoginResponseObject
                userLoginResponseData.value = userLoginResponseDataObject
            }
        })
    }

    var userByIdResponseData = MutableLiveData<UserLoginResponseDataModel>()
    fun getUserById(id: String) {
        userLoginAPI.getUserById(id).enqueue(object : Callback<Wrapper> {
            override fun onFailure(call: Call<Wrapper>, t: Throwable) {
                t.printStackTrace()
                println("GET USER BY ID FAIL")
            }

            override fun onResponse(call: Call<Wrapper>, response: Response<Wrapper>) {
                println("GET USER BY ID SUCCESS")

                val response = response.body()
                val stringResponse = Gson().toJson(response)
                val stringResponseData = Gson().toJson(response?.data)
                if (stringResponseData != "null") {
                    val userByIdResponseDataObject =
                        Gson().fromJson<UserLoginResponseDataModel>(
                            stringResponseData,
                            UserLoginResponseDataModel::class.java
                        )
                    userByIdResponseData.value = userByIdResponseDataObject
                }
            }
        })
    }

    var userLoginGoogleResponse = MutableLiveData<Wrapper>()
    var userLoginGoogleResponseData = MutableLiveData<UserLoginResponseDataModel>()
    fun loginWithGoogle(userLoginModel: UserLoginModel) {
        userLoginAPI.loginWithGoogle(userLoginModel).enqueue(object : Callback<Wrapper> {
            override fun onFailure(call: Call<Wrapper>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<Wrapper>, response: Response<Wrapper>) {
                val response = response.body()
                val stringResponse = Gson().toJson(response)
                val stringResponseData = Gson().toJson(response?.data)
                if (response?.data != null) {
                    println("GOOGLE NOT NULL" + stringResponseData)
                    val userLoginWithGoogleResponseObject =
                        Gson().fromJson<Wrapper>(stringResponse, Wrapper::class.java)
                    val userLoginWithGoogleResponseDataObject =
                        Gson().fromJson<UserLoginResponseDataModel>(
                            stringResponseData,
                            UserLoginResponseDataModel::class.java
                        )
                    userLoginGoogleResponse.value = userLoginWithGoogleResponseObject
                    userLoginGoogleResponseData.value = userLoginWithGoogleResponseDataObject
                } else {
                    println("GOOGLE NULL" + stringResponseData)
                    userLoginGoogleResponse.value = null
                    userLoginGoogleResponseData.value = null
                }
            }
        })
    }

    var userLoginFacebookResponse = MutableLiveData<Wrapper>()
    var userLoginFacebookResponseData = MutableLiveData<UserLoginResponseDataModel>()
    fun loginWithFacebook(userLoginModel: UserLoginModel) {
        userLoginAPI.loginWithFacebook(userLoginModel).enqueue(object : Callback<Wrapper> {
            override fun onFailure(call: Call<Wrapper>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<Wrapper>, response: Response<Wrapper>) {
                val response = response.body()
                val stringResponse = Gson().toJson(response)
                val stringResponseData = Gson().toJson(response?.data)
                if (response?.data != null) {
                    val userLoginWithFacebookResponseObject =
                        Gson().fromJson<Wrapper>(stringResponse, Wrapper::class.java)
                    val userLoginWithFacebookResponseDataObject =
                        Gson().fromJson<UserLoginResponseDataModel>(
                            stringResponseData,
                            UserLoginResponseDataModel::class.java
                        )
                    userLoginFacebookResponse.value = userLoginWithFacebookResponseObject
                    userLoginFacebookResponseData.value = userLoginWithFacebookResponseDataObject
                } else {
                    userLoginFacebookResponse.value = null
                    userLoginFacebookResponseData.value = null
                }
            }
        })
    }

    var userUpdateGoogleAccountResponse = MutableLiveData<Wrapper>()
    fun updateGoogleAccount(userLoginResponseDataModel: UserLoginResponseDataModel) {
        userLoginAPI.updateGoogleAccount(userLoginResponseDataModel)
            .enqueue(object : Callback<Wrapper> {
                override fun onFailure(call: Call<Wrapper>, t: Throwable) {
                    t.printStackTrace()
                }

                override fun onResponse(call: Call<Wrapper>, response: Response<Wrapper>) {
                    val response = response.body()
                    val stringResponse = Gson().toJson(response)
                    val stringResponseData = Gson().toJson(response?.data)

                    val userUpdateGoogleAccountResponseObject =
                        Gson().fromJson<Wrapper>(stringResponse, Wrapper::class.java)
                    val userUpdateGoogleAccountResponseDataObject =
                        Gson().fromJson<UserLoginResponseDataModel>(
                            stringResponseData,
                            UserLoginResponseDataModel::class.java
                        )
                    userUpdateGoogleAccountResponse.value = userUpdateGoogleAccountResponseObject
                }
            })
    }

    var userUpdateFacebookAccountResponse = MutableLiveData<Wrapper>()
    fun updateFacebookAccount(userLoginResponseDataModel: UserLoginResponseDataModel) {
        userLoginAPI.updateFacebookAccount(userLoginResponseDataModel)
            .enqueue(object : Callback<Wrapper> {
                override fun onFailure(call: Call<Wrapper>, t: Throwable) {
                    t.printStackTrace()
                }

                override fun onResponse(call: Call<Wrapper>, response: Response<Wrapper>) {
                    val response = response.body()
                    val stringResponse = Gson().toJson(response)
                    val stringResponseData = Gson().toJson(response?.data)

                    val userUpdateFacebookAccountResponseObject =
                        Gson().fromJson<Wrapper>(stringResponse, Wrapper::class.java)
                    val userUpdateFacebookAccountResponseDataObject =
                        Gson().fromJson<UserLoginResponseDataModel>(
                            stringResponseData,
                            UserLoginResponseDataModel::class.java
                        )
                    userUpdateFacebookAccountResponse.value = userUpdateFacebookAccountResponseObject
                }
            })
    }
}