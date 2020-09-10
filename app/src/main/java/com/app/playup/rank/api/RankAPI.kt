package com.app.playup.rank.api

import com.app.playup.utils.Wrapper
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RankAPI {
    @GET("rank/{id}")
    fun getRankBySportId(@Path("id")id:String):Call<Wrapper>
}