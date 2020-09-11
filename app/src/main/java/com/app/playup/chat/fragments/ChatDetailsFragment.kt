package com.app.playup.chat.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.playup.R
import com.app.playup.chat.model.ChatModel
import com.app.playup.chat.recycleview.ChatRecycleView
import com.app.playup.chat.viewmodel.ChatViewModel
import com.app.playup.dagger.MyApplication
import kotlinx.android.synthetic.main.fragment_chat_details.*
import kotlinx.coroutines.*
import javax.inject.Inject

class ChatDetailsFragment : Fragment(), View.OnClickListener {
    var sharedPreferences: SharedPreferences? = null
    val coroutineScope = CoroutineScope(Dispatchers.IO)
    var userId: String? = ""
    var receiverUserId: String? = ""
    var senderUserId: String? = ""
    var userOne: String? = ""
    var userTwo: String? = ""
    var matchId: String? = ""
    lateinit var chatRecycleView: ChatRecycleView

    @Inject
    lateinit var chatViewModel: ChatViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as MyApplication).applicationComponent.inject(this)
        sharedPreferences = activity?.getSharedPreferences(
            getString(R.string.shared_preference_name),
            Context.MODE_PRIVATE
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chatDetailsRecycleViewContainer.layoutManager = LinearLayoutManager(this.context)
        chatDetailsSendButton.setOnClickListener(this)
        chatDetailsScheduleButton.setOnClickListener(this)
        userId = sharedPreferences?.getString(
            getString(R.string.id_key),
            getString(R.string.default_value)
        )
        userOne = sharedPreferences?.getString(
            getString(R.string.schedule_user_id_key),
            getString(R.string.default_value)
        )
        userTwo = sharedPreferences?.getString(
            getString(R.string.schedule_opponent_id_key),
            getString(R.string.default_value)
        )
        matchId = sharedPreferences?.getString(
            getString(R.string.match_id_key),
            getString(R.string.default_value)
        )
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            coroutineScope.cancel()
            requireActivity().finish()
        }
        getChatCoroutine(300)

        chatViewModel.getChatResponseData.observe(viewLifecycleOwner, Observer {
            chatRecycleView = ChatRecycleView(it, userId!!)

            val recyclerViewState =
                chatDetailsRecycleViewContainer.layoutManager?.onSaveInstanceState()
            chatDetailsRecycleViewContainer.layoutManager?.onRestoreInstanceState(
                recyclerViewState
            )

            chatDetailsRecycleViewContainer.adapter = chatRecycleView
        })
    }

    override fun onClick(v: View?) {
        when (v) {
            chatDetailsSendButton -> {
                val chatMessage = chatDetailsInputText.text.toString()
                if (userId == userOne && userId != userTwo) {
                    senderUserId = userOne
                    receiverUserId = userOne
                } else {
                    senderUserId = userTwo
                    receiverUserId = userTwo
                }
                val chatModel = ChatModel(
                    chat_match_id = matchId!!,
                    chat_sender_id = senderUserId!!,
                    chat_receiver_id = receiverUserId!!,
                    chat_message = chatMessage
                )
                if (chatMessage != "") {
                    chatViewModel.sendChat(chatModel)
                    chatDetailsInputText.setText("")
                }
            }
            chatDetailsScheduleButton -> {
                coroutineScope.cancel()
                v?.findNavController()?.navigate(R.id.action_global_scheduleCreateFragment)
            }
        }
    }

    fun getChatCoroutine(interval: Long) {
        coroutineScope.launch {
            while (true) {
                delay(interval)
                chatViewModel.getChat(matchId!!)
            }
        }
    }
}