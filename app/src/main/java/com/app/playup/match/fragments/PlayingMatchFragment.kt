package com.app.playup.match.fragments

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
import com.app.playup.schedule.viewmodel.ScheduleViewModel
import kotlinx.android.synthetic.main.fragment_playing_match.*
import javax.inject.Inject


class PlayingMatchFragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_playing_match, container, false)
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
        playingMatchResultButton.setOnClickListener {
            scheduleViewModel.getScheduleById(scheduleId!!)
            scheduleViewModel.scheduleByIdResponseData.observe(viewLifecycleOwner, Observer {
                if (it.schedule_result == "U") {
                    view.findNavController().navigate(R.id.action_global_scheduleResultFragment)
                } else {
                    if (id == it.schedule_user_id && it.schedule_result == "W") {
                        Toast.makeText(this.context, "Hasil sudah ditentukan", Toast.LENGTH_SHORT)
                            .show()
                        view.findNavController().navigate(R.id.action_global_resultWinFragment)
                    } else if (id == it.schedule_user_id && it.schedule_result == "L") {
                        Toast.makeText(this.context, "Hasil sudah ditentukan", Toast.LENGTH_SHORT)
                            .show()
                        view.findNavController().navigate(R.id.action_global_resultLoseFragment)
                    } else if (id == it.schedule_opponent_id && it.schedule_result == "W") {
                        Toast.makeText(this.context, "Hasil sudah ditentukan", Toast.LENGTH_SHORT)
                            .show()
                        view.findNavController().navigate(R.id.action_global_resultLoseFragment)
                    } else if (id == it.schedule_opponent_id && it.schedule_result == "L") {
                        Toast.makeText(this.context, "Hasil sudah ditentukan", Toast.LENGTH_SHORT)
                            .show()
                        view.findNavController().navigate(R.id.action_global_resultWinFragment)
                    }
                }
            })
        }
    }
}