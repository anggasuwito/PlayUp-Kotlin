package com.app.playup.user.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.app.playup.R
import kotlinx.android.synthetic.main.fragment_user_change_profile_form.*
import kotlinx.android.synthetic.main.fragment_user_register.*
import kotlinx.android.synthetic.main.fragment_user_register.view.*

class UserChangeProfileFormFragment : Fragment(), View.OnClickListener {
    var gender: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_change_profile_form, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userChangeProfileSaveButton.setOnClickListener(this)
        userChangeProfileCancelButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            userChangeProfileSaveButton -> {
                if (gender == "") {
                    Toast.makeText(
                        this.context,
                        "Pilih jenis kelamin",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    v?.findNavController()
                        ?.navigate(R.id.action_global_userChangeProfileSuccessFragment)
                }
            }
            userChangeProfileCancelButton -> {
                activity?.finish()
            }
        }
    }
}