package com.app.playup.match.api

import com.app.playup.match.model.FindingMatchModel
import com.app.playup.utils.Wrapper
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface MatchAPI {
    @POST("play/badmintonS/find")
    fun findOpponentSingleBadminton(@Body findingMatchModel: FindingMatchModel): Call<Wrapper>

    @POST("play/badmintonS/cancel/{id}")
    fun cancelFindOpponentSingleBadminton(@Path("id") id: String): Call<Wrapper>

    @POST("play/badmintonS/reset")
    fun resetRoom():Call<Wrapper>
//    @POST("play/badmintonS/be")
//    fun waitOpponentSingleBadminton(@Body findingMatchModel: FindingMatchModel): Call<Wrapper>
}