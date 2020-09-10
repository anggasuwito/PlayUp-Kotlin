package com.app.playup.schedule.api

import com.app.playup.schedule.model.ScheduleModel
import com.app.playup.utils.Wrapper
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ScheduleAPI {
    @GET("schedule/active/{id}")
    fun getActiveSchedule(@Path("id") id: String): Call<Wrapper>

    @GET("schedule/inactive/{id}")
    fun getInactiveSchedule(@Path("id") id: String): Call<Wrapper>

    @GET("schedule/detail/{id}")
    fun getScheduleById(@Path("id") id: String): Call<Wrapper>

    @POST("schedule/create")
    fun createNewSchedule(@Body scheduleModel: ScheduleModel): Call<Wrapper>

    @PUT
    fun updateResultSchedule(@Body scheduleModel: ScheduleModel): Call<Wrapper>
}