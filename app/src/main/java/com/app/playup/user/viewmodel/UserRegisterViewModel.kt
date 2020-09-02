package com.app.playup.user.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.playup.user.model.UserRegisterModel
import com.app.playup.user.repository.UserRegisterRepository
import com.app.playup.utils.Wrapper
import javax.inject.Inject

class UserRegisterViewModel @Inject constructor(var userRegisterRepository: UserRegisterRepository) :
    ViewModel() {
    var userRegisterResponse = userRegisterRepository.userRegisterResponse
    fun registerNewUser(userRegisterModel: UserRegisterModel) {
        userRegisterRepository.registerNewUser(userRegisterModel)
    }
}