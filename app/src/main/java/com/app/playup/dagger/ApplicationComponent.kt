package com.app.playup.dagger

import com.app.playup.match.MatchActivity
import com.app.playup.match.fragments.FindingMatchFragment
import com.app.playup.menu.fragments.MenuAccountFragment
import com.app.playup.menu.fragments.MenuPlayFragment
import com.app.playup.menu.fragments.MenuScheduleFragment
import com.app.playup.schedule.fragments.ScheduleActiveFragment
import com.app.playup.schedule.fragments.ScheduleInactiveFragment
import com.app.playup.user.fragments.UserLoginFragment
import com.app.playup.user.fragments.UserRegisterFragment
import dagger.Component

@Component(modules = [NetworkModule::class])
interface ApplicationComponent {
    fun inject(userRegisterFragment: UserRegisterFragment)
    fun inject(userLoginFragment: UserLoginFragment)
    fun inject(findingMatchFragment: FindingMatchFragment)
    fun inject(menuAccountFragment: MenuAccountFragment)
    fun inject(scheduleActiveFragment: ScheduleActiveFragment)
    fun inject(scheduleInactiveFragment: ScheduleInactiveFragment)
}