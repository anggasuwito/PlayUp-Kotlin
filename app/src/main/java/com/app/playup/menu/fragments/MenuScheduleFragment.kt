package com.app.playup.menu.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.playup.R
import com.app.playup.dagger.MyApplication
import com.app.playup.schedule.fragments.ScheduleActiveFragment
import com.app.playup.schedule.fragments.ScheduleInactiveFragment
import com.app.playup.schedule.recycleview.ScheduleRecycleView
import com.app.playup.schedule.viewmodel.ScheduleViewModel
import kotlinx.android.synthetic.main.fragment_menu_schedule.*
import javax.inject.Inject

class MenuScheduleFragment : Fragment(), View.OnClickListener {
    lateinit var scheduleActiveFragment: ScheduleActiveFragment
    lateinit var scheduleInactiveFragment: ScheduleInactiveFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu_schedule, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        menuScheduleActiveButton.setOnClickListener(this)
        menuScheduleHistoryButton.setOnClickListener(this)
        scheduleActiveFragment = ScheduleActiveFragment()
        scheduleInactiveFragment = ScheduleInactiveFragment()
        activity?.supportFragmentManager?.beginTransaction()
            ?.add(R.id.menuScheduleContainer, scheduleActiveFragment)
            ?.commit()
    }

    override fun onClick(v: View?) {
        when (v) {
            menuScheduleActiveButton -> {
                switchFragment(scheduleActiveFragment)
            }
            menuScheduleHistoryButton -> {
                switchFragment(scheduleInactiveFragment)
            }
        }
    }

    fun switchFragment(fragment: Fragment) {
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.menuScheduleContainer, fragment)?.commit()
    }
}