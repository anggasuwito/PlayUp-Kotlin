package com.app.playup.schedule.repository

import androidx.lifecycle.MutableLiveData
import com.app.playup.menu.api.MenuAccountAPI
import com.app.playup.menu.model.MenuAccountDataModel
import com.app.playup.schedule.api.ScheduleAPI
import com.app.playup.schedule.model.ScheduleModel
import com.app.playup.user.model.UserLoginResponseDataModel
import com.app.playup.utils.Wrapper
import com.google.gson.Gson
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Path
import javax.inject.Inject

class ScheduleRepository @Inject constructor(val scheduleAPI: ScheduleAPI) {
    var scheduleActiveResponseData = MutableLiveData<List<ScheduleModel>>()
    fun getActiveSchedule(id: String) {
        scheduleAPI.getActiveSchedule(id)
            .enqueue(object : Callback<Wrapper> {
                override fun onFailure(call: Call<Wrapper>, t: Throwable) {
                    t.printStackTrace()
                }

                override fun onResponse(
                    call: Call<Wrapper>,
                    response: Response<Wrapper>
                ) {
                    val response = response.body()
                    val stringResponse = Gson().toJson(response)
                    val stringResponseData = Gson().toJson(response?.data)
                    val scheduleResponseObject =
                        Gson().fromJson<Wrapper>(stringResponse, Wrapper::class.java)
                    if (stringResponseData != "null") {
                        val scheduleResponseDataObject: List<ScheduleModel> =
                            Gson().fromJson(stringResponseData, Array<ScheduleModel>::class.java)
                                .toList()
                        scheduleActiveResponseData.value = scheduleResponseDataObject
                    }else{
                        scheduleActiveResponseData.value = null
                    }
                }
            })
    }

    var scheduleInactiveResponseData = MutableLiveData<List<ScheduleModel>>()
    fun getInactiveSchedule(id: String) {
        scheduleAPI.getInactiveSchedule(id)
            .enqueue(object : Callback<Wrapper> {
                override fun onFailure(call: Call<Wrapper>, t: Throwable) {
                    t.printStackTrace()
                }

                override fun onResponse(
                    call: Call<Wrapper>,
                    response: Response<Wrapper>
                ) {
                    val response = response.body()
                    val stringResponse = Gson().toJson(response)
                    val stringResponseData = Gson().toJson(response?.data)
                    val scheduleResponseObject =
                        Gson().fromJson<Wrapper>(stringResponse, Wrapper::class.java)
                    if (stringResponseData != "null") {
                        val scheduleResponseDataObject: List<ScheduleModel> =
                            Gson().fromJson(stringResponseData, Array<ScheduleModel>::class.java)
                                .toList()
                        scheduleInactiveResponseData.value = scheduleResponseDataObject
                    }else{
                        scheduleActiveResponseData.value = null
                    }
                }
            })
    }

    var createNewScheduleResponse = MutableLiveData<Wrapper>()
    var createNewScheduleResponseData = MutableLiveData<ScheduleModel>()
    fun createNewSchedule(scheduleModel: ScheduleModel) {
        scheduleAPI.createNewSchedule(scheduleModel).enqueue(object : Callback<Wrapper> {
            override fun onFailure(call: Call<Wrapper>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<Wrapper>, response: Response<Wrapper>) {
                println("RES = " + response.body().toString())
                val response = response.body()
                val stringResponse = Gson().toJson(response)
                val stringResponseData = Gson().toJson(response?.data)
                val createNewScheduleObject =
                    Gson().fromJson<Wrapper>(stringResponse, Wrapper::class.java)
                if (stringResponseData != "null") {
                    val createNewScheduleDataObject: ScheduleModel =
                        Gson().fromJson(stringResponseData, ScheduleModel::class.java)
                    createNewScheduleResponseData.value = createNewScheduleDataObject
                }
                createNewScheduleResponse.value = createNewScheduleObject
            }
        })
    }

    var scheduleByIdResponseData = MutableLiveData<ScheduleModel>()
    fun getScheduleById(id: String) {
        scheduleAPI.getScheduleById(id).enqueue(object : Callback<Wrapper> {
            override fun onFailure(call: Call<Wrapper>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<Wrapper>, response: Response<Wrapper>) {
                val response = response.body()
                val stringResponse = Gson().toJson(response)
                val stringResponseData = Gson().toJson(response?.data)
                val scheduleByIdResponseObject =
                    Gson().fromJson<Wrapper>(stringResponse, Wrapper::class.java)
                if (stringResponseData != "null") {
                    val scheduleByIdResponseDataObject: ScheduleModel =
                        Gson().fromJson(stringResponseData, ScheduleModel::class.java)
                    scheduleByIdResponseData.value = scheduleByIdResponseDataObject
                }
            }
        })
    }

    var updateResultScheduleResponse = MutableLiveData<Wrapper>()
    fun updateResultSchedule(scheduleModel: ScheduleModel) {
        scheduleAPI.updateResultSchedule(scheduleModel).enqueue(object : Callback<Wrapper> {
            override fun onFailure(call: Call<Wrapper>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<Wrapper>, response: Response<Wrapper>) {
                if (response.code() != 404) {
                    val response = response.body()
                    val stringResponse = Gson().toJson(response)
                    val updateResultScheduleObject =
                        Gson().fromJson<Wrapper>(stringResponse, Wrapper::class.java)
                    updateResultScheduleResponse.value = updateResultScheduleObject
                }
            }
        })
    }
}