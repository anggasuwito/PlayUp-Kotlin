package com.app.playup.rank.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.playup.R
import com.app.playup.dagger.MyApplication
import com.app.playup.menu.viewmodel.MenuAccountViewModel
import com.app.playup.rank.recycleview.RankRecycleView
import com.app.playup.rank.viewmodel.RankViewModel
import com.app.playup.schedule.viewmodel.ScheduleViewModel
import kotlinx.android.synthetic.main.fragment_rank_single_badminton.*
import kotlinx.android.synthetic.main.fragment_schedule_active.*
import javax.inject.Inject


class RankSingleBadmintonFragment : Fragment() {
    @Inject
    lateinit var rankViewModel: RankViewModel
    @Inject
    lateinit var menuAccountViewModel: MenuAccountViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as MyApplication).applicationComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rank_single_badminton, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rankSingleBadmintonRecycleViewContainer.layoutManager = LinearLayoutManager(this.context)
        rankViewModel.getRankBySportId("1")
        rankViewModel.rankResponseData.observe(viewLifecycleOwner, Observer {
            rankSingleBadmintonRecycleViewContainer.adapter = RankRecycleView(it,requireActivity())
        })
    }
}