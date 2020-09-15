package com.app.playup.schedule.fragments

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
import com.app.playup.schedule.recycleview.ScheduleRecycleView
import com.app.playup.schedule.viewmodel.ScheduleViewModel
import com.app.playup.utils.EmptyDataFragment
import kotlinx.android.synthetic.main.fragment_schedule_active.*
import kotlinx.android.synthetic.main.fragment_schedule_inactive.*
import javax.inject.Inject

class ScheduleInactiveFragment : Fragment() {
    lateinit var scheduleRecycleView: ScheduleRecycleView
    var sharedPreferences: SharedPreferences? = null
    var id: String? = ""

    @Inject
    lateinit var scheduleViewModel: ScheduleViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as MyApplication).applicationComponent.inject(this)
        sharedPreferences =
            activity?.getSharedPreferences(
                getString(R.string.shared_preference_name),
                Context.MODE_PRIVATE
            )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedule_inactive, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        id = sharedPreferences?.getString(
            getString(R.string.id_key),
            getString(R.string.default_value)
        )
        scheduleInactiveRecycleViewContainer.layoutManager = LinearLayoutManager(this.context)
        scheduleViewModel.getInactiveSchedule(id!!)
        scheduleViewModel.scheduleInactiveResponseData.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                scheduleRecycleView = ScheduleRecycleView(it, id!!)
                scheduleInactiveRecycleViewContainer.adapter = scheduleRecycleView
            } else {
                switchFragment(EmptyDataFragment())
            }
        })
    }

    fun switchFragment(fragment: Fragment) {
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.menuScheduleContainer, fragment)?.commit()
    }
}