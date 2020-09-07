package com.app.playup.schedule.repository

import androidx.lifecycle.MutableLiveData
import com.app.playup.menu.api.MenuAccountAPI
import com.app.playup.menu.model.MenuAccountDataModel
import com.app.playup.schedule.api.ScheduleAPI
import com.app.playup.schedule.model.ScheduleModel
import com.app.playup.utils.Wrapper
import com.google.gson.Gson
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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
                    }
                }
            })
    }
}