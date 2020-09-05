package com.app.playup.menu.viewmodel

import android.app.Activity
import android.widget.ImageView
import androidx.lifecycle.ViewModel
import com.app.playup.menu.repository.MenuAccountRepository
import okhttp3.MultipartBody
import java.io.File
import javax.inject.Inject

class MenuAccountViewModel @Inject constructor(var menuAccountRepository: MenuAccountRepository) :
    ViewModel() {
    var menuAccountResponseData = menuAccountRepository.menuAccountResponseData

    fun menuAccountChangePhoto(image: MultipartBody.Part,data:MultipartBody.Part) {
        menuAccountRepository.menuAccountChangePhoto(image,data)
    }
    fun getUserPhoto(id: String, imageContainer: ImageView,activity: Activity) {
        menuAccountRepository.getUserPhoto(id,imageContainer,activity)
    }
}