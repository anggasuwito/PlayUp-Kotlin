package com.app.playup.dagger

import com.app.playup.user.fragments.UserLoginFragment
import com.app.playup.user.fragments.UserRegisterFragment
import dagger.Component

@Component(modules = [NetworkModule::class])
interface ApplicationComponent {
    fun inject(userRegisterFragment: UserRegisterFragment)
    fun inject(userLoginFragment: UserLoginFragment)
}