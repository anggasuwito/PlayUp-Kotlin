package com.app.playup.schedule

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.app.playup.R

class ScheduleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule)
        val scheduleId = intent.getStringExtra("scheduleId")
        this.findNavController(R.id.schedule_nav_host).navigate(
            R.id.action_global_scheduleDetailsFragment,
            bundleOf(
                "scheduleId" to scheduleId
            )
        )
    }
}