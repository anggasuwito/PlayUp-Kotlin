package com.app.playup.dagger

import com.app.playup.config.RetrofitBuilder
import com.app.playup.user.api.UserLoginAPI
import com.app.playup.user.api.UserRegisterAPI
import dagger.Module
import dagger.Provides

@Module
class NetworkModule {
    @Provides
    fun provideUserRegisterAPI(): UserRegisterAPI {
        return RetrofitBuilder.createRetrofit().create(UserRegisterAPI::class.java)
    }

    @Provides
    fun provideUserLoginAPI(): UserLoginAPI {
        return RetrofitBuilder.createRetrofit().create(UserLoginAPI::class.java)
    }
}