package com.app.playup.chat.repository

import androidx.lifecycle.MutableLiveData
import com.app.playup.chat.api.ChatAPI
import com.app.playup.chat.model.ChatModel
import com.app.playup.schedule.api.ScheduleAPI
import com.app.playup.schedule.model.ScheduleModel
import com.app.playup.utils.Wrapper
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Path
import javax.inject.Inject

class ChatRepository @Inject constructor(val chatAPI: ChatAPI) {
    var getChatResponseData = MutableLiveData<List<ChatModel>>()
    fun getChat(id: String) {
        chatAPI.getChat(id).enqueue(object : Callback<Wrapper> {
            override fun onFailure(call: Call<Wrapper>, t: Throwable) {
                t.printStackTrace()
                println("FAIL GET CHAT")
            }

            override fun onResponse(call: Call<Wrapper>, response: Response<Wrapper>) {
                val response = response.body()
                val stringResponse = Gson().toJson(response)
                val stringResponseData = Gson().toJson(response?.data)
                val scheduleResponseObject =
                    Gson().fromJson<Wrapper>(stringResponse, Wrapper::class.java)
                if (stringResponseData != "null") {
                    val getChatResponseDataObject: List<ChatModel> =
                        Gson().fromJson(stringResponseData, Array<ChatModel>::class.java)
                            .toList()
                    getChatResponseData.value = getChatResponseDataObject
                }

            }
        })
    }

    fun sendChat(chatModel: ChatModel) {
        chatAPI.sendChat(chatModel).enqueue(object : Callback<Wrapper> {
            override fun onFailure(call: Call<Wrapper>, t: Throwable) {
                t.printStackTrace()
                println("FAIL SEND CHAT")
            }

            override fun onResponse(call: Call<Wrapper>, response: Response<Wrapper>) {
                if (response.code() != 404) {
                    println("SUCCESS SEND CHAT")
                } else {
                    println("SEND CHAT HAS PROBLEM")
                }
            }
        })
    }
}