package com.app.playup.chat.api

import com.app.playup.chat.model.ChatModel
import com.app.playup.utils.Wrapper
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ChatAPI {
    @GET("chat/{id}")
    fun getChat(@Path("id") id: String): Call<Wrapper>

    @POST("chat")
    fun sendChat( @Body chatModel: ChatModel): Call<Wrapper>
}