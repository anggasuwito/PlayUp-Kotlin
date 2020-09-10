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
import kotlinx.android.synthetic.main.fragment_result_lose.*
import kotlinx.android.synthetic.main.fragment_result_win.*
import kotlinx.android.synthetic.main.fragment_schedule_result.*
import javax.inject.Inject

class ScheduleResultFragment : Fragment(), View.OnClickListener {
    @Inject
    lateinit var scheduleViewModel: ScheduleViewModel
    var sharedPreferences: SharedPreferences? = null
    var id: String? = ""
    var scheduleId: String? = ""
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
        return inflater.inflate(R.layout.fragment_schedule_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        id = sharedPreferences?.getString(
            getString(R.string.id_key),
            getString(R.string.default_value)
        )
        scheduleId = sharedPreferences?.getString(
            getString(R.string.schedule_id_key),
            getString(R.string.default_value)
        )
        scheduleResultLoseButton.setOnClickListener(this)
        scheduleResultWinButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            scheduleResultLoseButton -> {
                scheduleViewModel.getScheduleById(scheduleId!!)
                scheduleViewModel.scheduleByIdResponseData.observe(viewLifecycleOwner, Observer {
                    if (it.schedule_result == "U") {
                        if (id == it.schedule_user_id) {
                            scheduleViewModel.updateResultSchedule(
                                ScheduleModel(
                                    schedule_id = it.schedule_id,
                                    schedule_match_id = it.schedule_match_id,
                                    schedule_user_id = it.schedule_user_id,
                                    schedule_user_name = it.schedule_user_name,
                                    schedule_user_photo = it.schedule_user_photo,
                                    schedule_location = it.schedule_location,
                                    schedule_time = it.schedule_time,
                                    schedule_status = "I",
                                    schedule_result = "L",
                                    schedule_opponent = it.schedule_opponent,
                                    schedule_opponent_id = it.schedule_opponent_id,
                                    schedule_opponent_photo = it.schedule_opponent_photo
                                )
                            )
                            v?.findNavController()?.navigate(R.id.action_global_resultLoseFragment)
                        } else {
                            scheduleViewModel.updateResultSchedule(
                                ScheduleModel(
                                    schedule_id = it.schedule_id,
                                    schedule_match_id = it.schedule_match_id,
                                    schedule_user_id = it.schedule_user_id,
                                    schedule_user_name = it.schedule_user_name,
                                    schedule_user_photo = it.schedule_user_photo,
                                    schedule_location = it.schedule_location,
                                    schedule_time = it.schedule_time,
                                    schedule_status = "I",
                                    schedule_result = "W",
                                    schedule_opponent = it.schedule_opponent,
                                    schedule_opponent_id = it.schedule_opponent_id,
                                    schedule_opponent_photo = it.schedule_opponent_photo
                                )
                            )
                            v?.findNavController()?.navigate(R.id.action_global_resultLoseFragment)
                        }
                    } else {
                        if (it.schedule_result == "W") {
                            if (id == it.schedule_user_id) {
                                Toast.makeText(
                                    this.context,
                                    "Hasil sudah ditentukan",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                                v?.findNavController()
                                    ?.navigate(R.id.action_global_resultWinFragment)
                            } else{
                                Toast.makeText(
                                    this.context,
                                    "Hasil sudah ditentukan",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                                v?.findNavController()
                                    ?.navigate(R.id.action_global_resultLoseFragment)
                            }
                        }else{
                            if (id == it.schedule_user_id) {
                                Toast.makeText(
                                    this.context,
                                    "Hasil sudah ditentukan",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                                v?.findNavController()
                                    ?.navigate(R.id.action_global_resultLoseFragment)
                            } else{
                                Toast.makeText(
                                    this.context,
                                    "Hasil sudah ditentukan",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                                v?.findNavController()
                                    ?.navigate(R.id.action_global_resultWinFragment)
                            }
                        }
                    }
                })
            }
            scheduleResultWinButton -> {
                scheduleViewModel.getScheduleById(scheduleId!!)
                scheduleViewModel.scheduleByIdResponseData.observe(viewLifecycleOwner, Observer {
                    if (it.schedule_result == "U") {
                        if (id == it.schedule_user_id) {
                            scheduleViewModel.updateResultSchedule(
                                ScheduleModel(
                                    schedule_id = it.schedule_id,
                                    schedule_match_id = it.schedule_match_id,
                                    schedule_user_id = it.schedule_user_id,
                                    schedule_user_name = it.schedule_user_name,
                                    schedule_user_photo = it.schedule_user_photo,
                                    schedule_location = it.schedule_location,
                                    schedule_time = it.schedule_time,
                                    schedule_status = "I",
                                    schedule_result = "W",
                                    schedule_opponent = it.schedule_opponent,
                                    schedule_opponent_id = it.schedule_opponent_id,
                                    schedule_opponent_photo = it.schedule_opponent_photo
                                )
                            )
                            v?.findNavController()?.navigate(R.id.action_global_resultWinFragment)
                        } else {
                            scheduleViewModel.updateResultSchedule(
                                ScheduleModel(
                                    schedule_id = it.schedule_id,
                                    schedule_match_id = it.schedule_match_id,
                                    schedule_user_id = it.schedule_user_id,
                                    schedule_user_name = it.schedule_user_name,
                                    schedule_user_photo = it.schedule_user_photo,
                                    schedule_location = it.schedule_location,
                                    schedule_time = it.schedule_time,
                                    schedule_status = "I",
                                    schedule_result = "L",
                                    schedule_opponent = it.schedule_opponent,
                                    schedule_opponent_id = it.schedule_opponent_id,
                                    schedule_opponent_photo = it.schedule_opponent_photo
                                )
                            )
                            v?.findNavController()?.navigate(R.id.action_global_resultWinFragment)
                        }
                    } else {
                        if (it.schedule_result == "W") {
                            if (id == it.schedule_user_id) {
                                Toast.makeText(
                                    this.context,
                                    "Hasil sudah ditentukan",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                                v?.findNavController()
                                    ?.navigate(R.id.action_global_resultWinFragment)
                            } else{
                                Toast.makeText(
                                    this.context,
                                    "Hasil sudah ditentukan",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                                v?.findNavController()
                                    ?.navigate(R.id.action_global_resultLoseFragment)
                            }
                        }else{
                            if (id == it.schedule_user_id) {
                                Toast.makeText(
                                    this.context,
                                    "Hasil sudah ditentukan",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                                v?.findNavController()
                                    ?.navigate(R.id.action_global_resultLoseFragment)
                            } else{
                                Toast.makeText(
                                    this.context,
                                    "Hasil sudah ditentukan",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                                v?.findNavController()
                                    ?.navigate(R.id.action_global_resultWinFragment)
                            }
                        }
                    }
                })
            }
        }
    }
}