package com.app.playup.match

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.NavArgument
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.app.playup.R
import kotlinx.android.synthetic.main.activity_match.*


class MatchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match)
        val status = intent.getStringExtra("status")
        this.findNavController(R.id.match_nav_host).navigate(R.id.action_global_findingMatchFragment,
            bundleOf("status" to status))
    }
}