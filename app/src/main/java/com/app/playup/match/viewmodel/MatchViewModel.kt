package com.app.playup.match.viewmodel

import androidx.lifecycle.ViewModel
import com.app.playup.match.model.FindingMatchModel
import com.app.playup.match.repository.MatchRepository
import com.app.playup.user.model.UserLoginModel
import com.app.playup.user.repository.UserLoginRepository
import javax.inject.Inject

class MatchViewModel @Inject constructor(var matchRepository: MatchRepository) :
    ViewModel() {

    var status:String = ""

    var matchFindResponse = matchRepository.matchFindResponse
    var matchFindResponseData = matchRepository.matchFindResponseData
    fun findOpponentSingleBadminton(findingMatchModel: FindingMatchModel) {
        matchRepository.findOpponentSingleBadminton(findingMatchModel)
    }

//    var matchWaitResponse = matchRepository.matchWaitResponse
//    var matchWaitResponseData = matchRepository.matchWaitResponseData
//    fun waitOpponentSingleBadminton(findingMatchModel: FindingMatchModel) {
//        matchRepository.waitOpponentSingleBadminton(findingMatchModel)
//    }

}