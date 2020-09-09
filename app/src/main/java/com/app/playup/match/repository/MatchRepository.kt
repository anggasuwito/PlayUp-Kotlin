package com.app.playup.match.repository

import androidx.lifecycle.MutableLiveData
import com.app.playup.match.api.MatchAPI
import com.app.playup.match.model.FindingMatchModel
import com.app.playup.match.model.FindingMatchResponseDataModel
import com.app.playup.utils.Wrapper
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class MatchRepository @Inject constructor(val matchAPI: MatchAPI) {
    var matchFindResponse = MutableLiveData<Wrapper>()
    var matchFindResponseData = MutableLiveData<FindingMatchResponseDataModel>()

    fun findOpponentSingleBadminton(findingMatchModel: FindingMatchModel) {
        matchAPI.findOpponentSingleBadminton(findingMatchModel).enqueue(object : Callback<Wrapper> {
            override fun onFailure(call: Call<Wrapper>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<Wrapper>, response: Response<Wrapper>) {
                println("MATCH ID "+response.body()!!.data.toString())
                val response = response.body()
                val stringResponse = Gson().toJson(response)
                val stringResponseData = Gson().toJson(response?.data)
                val matchFindResponseObject =
                    Gson().fromJson<Wrapper>(stringResponse, Wrapper::class.java)
                val matchFindResponseDataObject =
                    Gson().fromJson<FindingMatchResponseDataModel>(
                        stringResponseData,
                        FindingMatchResponseDataModel::class.java
                    )
                matchFindResponse.value = matchFindResponseObject
                matchFindResponseData.value = matchFindResponseDataObject
            }
        })
    }

//    var matchWaitResponse = MutableLiveData<Wrapper>()
//    var matchWaitResponseData = MutableLiveData<FindingMatchResponseDataModel>()
//
//    fun waitOpponentSingleBadminton(findingMatchModel: FindingMatchModel) {
//        matchAPI.waitOpponentSingleBadminton(findingMatchModel).enqueue(object : Callback<Wrapper> {
//            override fun onFailure(call: Call<Wrapper>, t: Throwable) {
////                t.printStackTrace()
//            }
//
//            override fun onResponse(call: Call<Wrapper>, response: Response<Wrapper>) {
//                val response = response.body()
//                val stringResponse = Gson().toJson(response)
//                val stringResponseData = Gson().toJson(response?.data)
//                val matchWaitResponseObject =
//                    Gson().fromJson<Wrapper>(stringResponse, Wrapper::class.java)
//                val matchWaitResponseDataObject =
//                    Gson().fromJson<FindingMatchResponseDataModel>(
//                        stringResponseData,
//                        FindingMatchResponseDataModel::class.java
//                    )
//                matchWaitResponse.value = matchWaitResponseObject
//                matchWaitResponseData.value = matchWaitResponseDataObject
//            }
//        })
//    }
}

