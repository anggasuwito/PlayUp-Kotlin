package com.app.playup.config

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitBuilder {
    companion object {
        val BASE_URL = "http://10.0.2.2:7000/"
//        val BASE_URL = "http://ec2-54-85-182-189.compute-1.amazonaws.com:3000/"
        fun createRetrofit(): Retrofit {
            val retrofit = Retrofit
                .Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit
        }
    }
}