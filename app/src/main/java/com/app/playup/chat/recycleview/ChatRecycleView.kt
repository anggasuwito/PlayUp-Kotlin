package com.app.playup.chat.recycleview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView
import com.app.playup.R
import com.app.playup.chat.model.ChatModel
import com.app.playup.schedule.recycleview.ScheduleViewHolder
import com.google.android.material.card.MaterialCardView

class ChatRecycleView(val chatList: List<ChatModel>, val userId: String) :
    RecyclerView.Adapter<ChatViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycle_view_chat_details, parent, false)
        return ChatViewHolder(view)
    }

    override fun getItemCount(): Int {
        return chatList.size
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        if (userId == chatList[position].chat_sender_id) {
            holder.chatDetailsUserOneText.text = chatList[position].chat_message
            holder.chatDetailsUserTwoCardView.visibility = View.GONE
        } else {
            holder.chatDetailsUserTwoText.text = chatList[position].chat_message
            holder.chatDetailsUserOneCardView.visibility = View.GONE
        }
    }
}

class ChatViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    val chatDetailsUserOneText = v.findViewById<TextView>(R.id.chatDetailsUserOneText)
    val chatDetailsUserTwoText = v.findViewById<TextView>(R.id.chatDetailsUserTwoText)
    val chatDetailsUserOneCardView =
        v.findViewById<MaterialCardView>(R.id.chatDetailsUserOneCardView)
    val chatDetailsUserTwoCardView =
        v.findViewById<MaterialCardView>(R.id.chatDetailsUserTwoCardView)
}