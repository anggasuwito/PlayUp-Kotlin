package com.app.playup.menu.viewmodel

import androidx.lifecycle.ViewModel
import com.app.playup.menu.repository.MenuAccountRepository
import okhttp3.MultipartBody
import java.io.File
import javax.inject.Inject

class MenuAccountViewModel @Inject constructor(var menuAccountRepository: MenuAccountRepository) :
    ViewModel() {
    fun menuAccountChangePhoto(image: MultipartBody.Part,data:MultipartBody.Part) {
        menuAccountRepository.menuAccountChangePhoto(image,data)
    }
}