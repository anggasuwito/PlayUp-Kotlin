package com.app.playup.schedule.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.app.playup.R
import com.app.playup.dagger.MyApplication
import com.app.playup.schedule.model.ScheduleModel
import com.app.playup.schedule.viewmodel.ScheduleViewModel
import kotlinx.android.synthetic.main.fragment_schedule_create.*
import kotlinx.android.synthetic.main.fragment_user_login.*
import javax.inject.Inject


class ScheduleCreateFragment : Fragment(), View.OnClickListener {
    @Inject
    lateinit var scheduleViewModel: ScheduleViewModel
    var sharedPreferences: SharedPreferences? = null

    var schedule_match_id: String? = ""
    var schedule_user_id: String? = ""
    var schedule_user_name: String? = ""
    var schedule_location: String? = ""
    var schedule_time: String? = ""
    var schedule_status: String? = ""
    var schedule_result: String? = ""
    var schedule_opponent: String? = ""
    var schedule_opponent_id: String? = ""
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
        return inflater.inflate(R.layout.fragment_schedule_create, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        schedule_match_id = sharedPreferences?.getString(
            getString(R.string.match_id_key),
            getString(R.string.default_value)
        )
        schedule_user_id = sharedPreferences?.getString(
            getString(R.string.schedule_user_id_key),
            getString(R.string.default_value)
        )
        schedule_user_name = sharedPreferences?.getString(
            getString(R.string.schedule_username_key),
            getString(R.string.default_value)
        )
        schedule_opponent = sharedPreferences?.getString(
            getString(R.string.schedule_opponent_key),
            getString(R.string.default_value)
        )
        schedule_opponent_id = sharedPreferences?.getString(
            getString(R.string.schedule_opponent_id_key),
            getString(R.string.default_value)
        )
        schedule_status = "A"
        schedule_result = "U"
        scheduleCreateCancelButton.setOnClickListener(this)
        scheduleCreateSaveButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            scheduleCreateCancelButton -> {
                activity?.finish()
            }
            scheduleCreateSaveButton -> {
                println("save here")
                val schedule_location = scheduleCreateLocationText.text.toString()
                val schedule_time = scheduleCreateTimeText.text.toString()
                var scheduleModel = ScheduleModel(
                    schedule_match_id = schedule_match_id!!,
                    schedule_user_id = schedule_user_id!!,
                    schedule_user_name = schedule_user_name!!,
                    schedule_location = schedule_location,
                    schedule_time = schedule_time,
                    schedule_status = schedule_status!!,
                    schedule_result = schedule_result!!,
                    schedule_opponent = schedule_opponent!!,
                    schedule_opponent_id = schedule_opponent_id!!
                )
                if (schedule_location == "" ||
                    schedule_time == ""
                ) {
                    Toast.makeText(this.context, "Isi semua form", Toast.LENGTH_SHORT).show()
                } else {
                    scheduleViewModel.createNewSchedule(scheduleModel)
                    scheduleViewModel.createNewScheduleResponse.observe(
                        viewLifecycleOwner,
                        Observer {
                            if (it.code != 500.toString()) {
                                v?.findNavController()
                                    ?.navigate(R.id.action_global_scheduleCreateSuccessFragment)
                            } else {
                                Toast.makeText(
                                    this.context,
                                    "Jadwal sudah dibuat",
                                    Toast.LENGTH_SHORT
                                ).show()
                                requireActivity().finish()
                            }
                        })
                }
            }
        }
    }
}