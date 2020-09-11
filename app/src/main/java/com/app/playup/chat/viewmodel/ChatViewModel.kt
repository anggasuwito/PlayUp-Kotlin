package com.app.playup.chat.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.playup.chat.model.ChatModel
import com.app.playup.chat.repository.ChatRepository
import com.app.playup.schedule.repository.ScheduleRepository
import javax.inject.Inject

class ChatViewModel @Inject constructor(var chatRepository: ChatRepository) :
    ViewModel() {

    var getChatResponseData = chatRepository.getChatResponseData
    fun getChat(id: String) {
        chatRepository.getChat(id)
    }

    fun sendChat( chatModel: ChatModel) {
        chatRepository.sendChat( chatModel)
    }
}