package com.app.playup.rank.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.playup.rank.model.RankModel
import com.app.playup.rank.repository.RankRespository
import com.app.playup.schedule.model.ScheduleModel
import com.app.playup.schedule.repository.ScheduleRepository
import javax.inject.Inject

class RankViewModel @Inject constructor(var rankRespository: RankRespository) :
    ViewModel() {

    val rankResponseData = rankRespository.rankResponseData
    fun getRankBySportId(id:String){
        rankRespository.getRankBySportId(id)
    }
}