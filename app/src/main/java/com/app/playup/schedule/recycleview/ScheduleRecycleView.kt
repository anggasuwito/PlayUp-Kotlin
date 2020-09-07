package com.app.playup.schedule.recycleview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.playup.R
import com.app.playup.schedule.model.ScheduleModel

class ScheduleRecycleView(val scheduleList :List<ScheduleModel>):RecyclerView.Adapter<ScheduleViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycle_view_schedule, parent, false)
        return ScheduleViewHolder(view)
    }

    override fun getItemCount(): Int {
       return scheduleList.size
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        holder.menuScheduleListText.text =  scheduleList[position].schedule_location
        holder.menuScheduleStatusText.text = scheduleList[position].schedule_result
    }
}

class ScheduleViewHolder(v:View):RecyclerView.ViewHolder(v){
    val menuScheduleListText = v.findViewById<TextView>(R.id.menuScheduleListText)
    val menuScheduleStatusText = v.findViewById<TextView>(R.id.menuScheduleStatusText)
}