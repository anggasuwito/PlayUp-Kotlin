package com.app.playup.user.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.playup.user.model.UserLoginModel
import com.app.playup.user.model.UserLoginResponseDataModel
import com.app.playup.user.model.UserRegisterModel
import com.app.playup.user.repository.UserLoginRepository
import com.app.playup.user.repository.UserRegisterRepository
import javax.inject.Inject

class UserLoginViewModel @Inject constructor(var userLoginRepository: UserLoginRepository) :
    ViewModel() {
    var userLoginResponse = userLoginRepository.userLoginResponse
    var userLoginResponseData = userLoginRepository.userLoginResponseData
    fun loginUser(userLoginModel: UserLoginModel) {
        userLoginRepository.loginUser(userLoginModel)
    }

    var userByIdResponseData = userLoginRepository.userByIdResponseData
    fun getUserById(id: String){
        userLoginRepository.getUserById(id)
    }
}