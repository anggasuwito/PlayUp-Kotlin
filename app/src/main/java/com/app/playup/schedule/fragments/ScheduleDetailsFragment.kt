package com.app.playup.schedule.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.app.playup.R
import com.app.playup.dagger.MyApplication
import com.app.playup.menu.viewmodel.MenuAccountViewModel
import com.app.playup.schedule.viewmodel.ScheduleViewModel
import kotlinx.android.synthetic.main.fragment_schedule_details.*
import javax.inject.Inject

class ScheduleDetailsFragment : Fragment(), View.OnClickListener {
    var scheduleId: String? = ""

    @Inject
    lateinit var menuAccountViewModel: MenuAccountViewModel

    @Inject
    lateinit var scheduleViewModel: ScheduleViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as MyApplication).applicationComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedule_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            requireActivity().finish()
        }
        scheduleId = arguments?.getString("scheduleId")
        scheduleViewModel.getScheduleById(scheduleId!!)
        scheduleViewModel.scheduleByIdResponseData.observe(viewLifecycleOwner, Observer {
            menuAccountViewModel.getUserPhoto(
                it.schedule_user_photo,
                scheduleDetailsPlayerOneImage,
                requireActivity()
            )
            menuAccountViewModel.getUserPhoto(
                it.schedule_opponent_photo,
                scheduleDetailsPlayerTwoImage,
                requireActivity()
            )
            scheduleDetailsText.text = "${it.schedule_time}\n${it.schedule_location}"
        })

        scheduleDetailsPlayButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            scheduleDetailsPlayButton -> {
                v?.findNavController()?.navigate(R.id.action_global_playingMatchFragment)
            }
        }
    }
}