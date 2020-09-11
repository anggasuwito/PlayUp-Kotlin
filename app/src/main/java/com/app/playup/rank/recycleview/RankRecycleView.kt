package com.app.playup.rank.recycleview

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.playup.R
import com.app.playup.dagger.MyApplication
import com.app.playup.menu.viewmodel.MenuAccountViewModel
import com.app.playup.rank.model.RankModel
import com.app.playup.schedule.model.ScheduleModel
import com.app.playup.schedule.recycleview.ScheduleViewHolder
import de.hdodenhof.circleimageview.CircleImageView
import javax.inject.Inject

class RankRecycleView(val rankList: List<RankModel>, activity: Activity) :
    RecyclerView.Adapter<RankViewHolder>() {
    var activity = activity
    @Inject
    lateinit var menuAccountViewModel: MenuAccountViewModel

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankViewHolder {
        (activity?.applicationContext as MyApplication).applicationComponent.inject(this)
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycle_view_rank, parent, false)
        return RankViewHolder(view)
    }

    override fun getItemCount(): Int {
        return rankList.size
    }

    override fun onBindViewHolder(holder: RankViewHolder, position: Int) {
        val username = rankList[position].rank_user_name
        val match = rankList[position].rank_match_count
        val grade = rankList[position].rank_grade_count
        val photo = rankList[position].rank_user_photo_profile
        var rankKalah = (match?.toInt()?.minus(grade!!.toInt())).toString()
        holder.rankListText.text = "$username\nMatch : $match\nMenang : $grade\nKalah : $rankKalah"
        menuAccountViewModel.getUserPhoto(photo,holder.rankListImage,activity)
    }
}

class RankViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    val rankListText = v.findViewById<TextView>(R.id.rankListText)
    val rankListImage = v.findViewById<CircleImageView>(R.id.rankListImage)
}