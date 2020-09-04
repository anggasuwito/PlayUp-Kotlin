package com.app.playup.match.api

import com.app.playup.match.model.FindingMatchModel
import com.app.playup.utils.Wrapper
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface MatchAPI {
    @POST("play/badmintonS/find")
    fun findOpponentSingleBadminton(@Body findingMatchModel: FindingMatchModel): Call<Wrapper>

    @POST("play/badmintonS/be")
    fun waitOpponentSingleBadminton(@Body findingMatchModel: FindingMatchModel): Call<Wrapper>
}