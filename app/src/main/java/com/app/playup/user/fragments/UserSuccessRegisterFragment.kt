package com.app.playup.user.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.app.playup.R
import kotlinx.android.synthetic.main.fragment_user_success_register.*

class UserSuccessRegisterFragment : Fragment(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_success_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userRegisterSuccessButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            userRegisterSuccessButton -> {
                v?.findNavController()?.navigate(R.id.action_global_userLoginFragment)
            }
        }
    }
}