package com.app.playup.schedule.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.app.playup.R
import kotlinx.android.synthetic.main.fragment_schedule_create.*


class ScheduleCreateFragment : Fragment(),View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        scheduleCreateCancelButton.setOnClickListener(this)
        scheduleCreateSaveButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v){
            scheduleCreateCancelButton->{
                activity?.finish()
            }
            scheduleCreateSaveButton->{
                v?.findNavController()?.navigate(R.id.action_global_scheduleCreateSuccessFragment)
            }
        }
    }
}