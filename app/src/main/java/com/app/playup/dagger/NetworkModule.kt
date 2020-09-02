package com.app.playup.dagger

import com.app.playup.config.RetrofitBuilder
import com.app.playup.user.api.UserRegisterAPI
import dagger.Module
import dagger.Provides
import retrofit2.create

@Module
class NetworkModule {
    @Provides
    fun provideUserRegisterAPI(): UserRegisterAPI {
        return RetrofitBuilder.createRetrofit().create(UserRegisterAPI::class.java)
    }
}