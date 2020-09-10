package com.app.playup.rank.repository

import androidx.lifecycle.MutableLiveData
import com.app.playup.rank.api.RankAPI
import com.app.playup.rank.model.RankModel
import com.app.playup.schedule.api.ScheduleAPI
import com.app.playup.schedule.model.ScheduleModel
import com.app.playup.utils.Wrapper
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Path
import javax.inject.Inject

class RankRespository @Inject constructor(val rankAPI: RankAPI) {
    val rankResponseData = MutableLiveData<List<RankModel>>()
    fun getRankBySportId(id:String){
        rankAPI.getRankBySportId(id).enqueue(object :Callback<Wrapper>{
            override fun onFailure(call: Call<Wrapper>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<Wrapper>, response: Response<Wrapper>) {
                val response = response.body()
                val stringResponse = Gson().toJson(response)
                val stringResponseData = Gson().toJson(response?.data)
                val rankResponseObject =
                    Gson().fromJson<Wrapper>(stringResponse, Wrapper::class.java)
                if (stringResponseData != "null") {
                    val rankResponseDataObject: List<RankModel> =
                        Gson().fromJson(stringResponseData, Array<RankModel>::class.java)
                            .toList()
                    rankResponseData.value = rankResponseDataObject
                }
            }
        })
    }
}