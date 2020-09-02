package com.app.playup.user.viewmodel

import androidx.lifecycle.ViewModel
import com.app.playup.user.model.UserLoginModel
import com.app.playup.user.model.UserRegisterModel
import com.app.playup.user.repository.UserLoginRepository
import com.app.playup.user.repository.UserRegisterRepository
import javax.inject.Inject

class UserLoginViewModel @Inject constructor(var userLoginRepository: UserLoginRepository) :
    ViewModel() {
    var userLoginResponse = userLoginRepository.userLoginResponse
    fun loginUser(userLoginModel: UserLoginModel) {
        userLoginRepository.loginUser(userLoginModel)
    }
}