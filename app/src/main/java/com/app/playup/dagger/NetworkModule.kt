package com.app.playup.dagger

import com.app.playup.config.RetrofitBuilder
import com.app.playup.match.api.MatchAPI
import com.app.playup.menu.api.MenuAccountAPI
import com.app.playup.schedule.api.ScheduleAPI
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

    @Provides
    fun provideMatchAPI(): MatchAPI {
        return RetrofitBuilder.createRetrofit().create(MatchAPI::class.java)
    }

    @Provides
    fun provideMenuAccountAPI(): MenuAccountAPI {
        return RetrofitBuilder.createRetrofit().create(MenuAccountAPI::class.java)
    }

    @Provides
    fun provideScheduleAPI(): ScheduleAPI {
        return RetrofitBuilder.createRetrofit().create(ScheduleAPI::class.java)
    }
}