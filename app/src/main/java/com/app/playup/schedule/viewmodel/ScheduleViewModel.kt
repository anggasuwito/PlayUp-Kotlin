package com.app.playup.schedule.viewmodel

import androidx.lifecycle.ViewModel
import com.app.playup.schedule.repository.ScheduleRepository
import com.app.playup.user.model.UserLoginModel
import com.app.playup.user.repository.UserLoginRepository
import javax.inject.Inject

class ScheduleViewModel @Inject constructor(var scheduleRepository: ScheduleRepository) :
    ViewModel() {

    var scheduleActiveResponseData = scheduleRepository.scheduleActiveResponseData
    fun getActiveSchedule(id: String) {
        scheduleRepository.getActiveSchedule(id)
    }
    var scheduleInactiveResponseData = scheduleRepository.scheduleInactiveResponseData
    fun getInactiveSchedule(id: String) {
        scheduleRepository.getInactiveSchedule(id)
    }
}