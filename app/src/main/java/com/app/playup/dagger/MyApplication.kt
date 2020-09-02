package com.app.playup.dagger

import android.app.Application

class MyApplication : Application() {
    val applicationComponent = DaggerApplicationComponent.create()
}