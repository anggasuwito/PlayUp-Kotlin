package com.app.playup.schedule.recycleview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.playup.R
import com.app.playup.schedule.model.ScheduleModel
import com.google.android.material.card.MaterialCardView

class ScheduleRecycleView(val scheduleList: List<ScheduleModel>, statusSchedule: String) :
    RecyclerView.Adapter<ScheduleViewHolder>() {
    var statusSchedule = statusSchedule
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycle_view_schedule, parent, false)
        return ScheduleViewHolder(view)
    }

    override fun getItemCount(): Int {
        return scheduleList.size
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        holder.menuScheduleListText.text = scheduleList[position].schedule_location
        if (statusSchedule == "active") {
            holder.menuScheduleIconText.text = ">"
        }
        holder.menuScheduleRecycleCardView.setOnClickListener {
            println("hit = "+ scheduleList[position].schedule_location)
        }
    }
}

class ScheduleViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    val menuScheduleListText = v.findViewById<TextView>(R.id.menuScheduleListText)
    val menuScheduleIconText = v.findViewById<TextView>(R.id.menuScheduleIconText)
    val menuScheduleRecycleCardView = v.findViewById<MaterialCardView>(R.id.menuScheduleRecycleCardView)
}