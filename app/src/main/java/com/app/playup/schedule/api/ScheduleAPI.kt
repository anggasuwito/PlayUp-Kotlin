package com.app.playup.schedule.api

import com.app.playup.schedule.model.ScheduleModel
import com.app.playup.utils.Wrapper
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ScheduleAPI {
    @GET("schedule/active/{id}")
    fun getActiveSchedule(@Path("id") id: String): Call<Wrapper>

    @GET("schedule/inactive/{id}")
    fun getInactiveSchedule(@Path("id") id: String): Call<Wrapper>

    @GET("schedule/active/detail/{id}")
    fun getScheduleById(@Path("id") id: String): Call<Wrapper>

    @POST("schedule/create")
    fun createNewSchedule(@Body scheduleModel: ScheduleModel): Call<Wrapper>
}