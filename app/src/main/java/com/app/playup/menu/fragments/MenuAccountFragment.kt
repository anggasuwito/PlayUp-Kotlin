package com.app.playup.menu.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.playup.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_menu_account.*

class MenuAccountFragment : Fragment(), View.OnClickListener {
    var sharedPreferences: SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = activity?.getSharedPreferences(
            getString(R.string.shared_preference_name),
            Context.MODE_PRIVATE
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val username = sharedPreferences?.getString(
            getString(R.string.username_key),
            getString(R.string.default_value)
        )
        val loginMethod = sharedPreferences?.getString(
            getString(R.string.login_method_key),
            getString(R.string.default_value)
        )
        menuAccountText.text = "$username\nMatch : 100\nRank : 70\nLogin : $loginMethod"
        menuAccountLogout.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            menuAccountLogout -> {
                with(sharedPreferences?.edit()) {
                    this?.remove(getString(R.string.username_key))
                    this?.remove(getString(R.string.login_method_key))
                    this?.commit()
                }
                activity?.finish()
            }
        }
    }
}