package com.app.playup.menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.app.playup.R
import kotlinx.android.synthetic.main.activity_menu.*

class MenuActivity : AppCompatActivity() {
    lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        navController = (menu_nav_host as NavHostFragment).navController
        NavigationUI.setupWithNavController(bottomMenuNavigation, navController)
        bottomMenuNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menuHome -> {
                    navController.navigate(R.id.action_global_menuHomeFragment)
                    true
                }
                R.id.menuSchedule -> {
                    navController.navigate(R.id.action_global_menuScheduleFragment)
                    true
                }
                R.id.menuPlay -> {
                    navController.navigate(R.id.action_global_menuPlayFragment)
                    true
                }
                R.id.menuRank -> {
                    navController.navigate(R.id.action_global_menuRankFragment)
                    true
                }
                R.id.menuAccount -> {
                    navController.navigate(R.id.action_global_menuAccountFragment)
                    true
                }
                else -> {
                    true
                }
            }
        }
        chatButton.setOnClickListener{
//            navController.navigate(R.id.action_global_menuAccountFragment)
        }
    }
}