package com.app.playup.user.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.findNavController
import com.app.playup.R
import com.app.playup.dagger.MyApplication
import com.app.playup.match.viewmodel.MatchViewModel
import com.app.playup.user.model.UserRegisterModel
import com.app.playup.user.viewmodel.UserRegisterViewModel
import kotlinx.android.synthetic.main.fragment_user_change_profile_form.*
import kotlinx.android.synthetic.main.fragment_user_register.*
import kotlinx.android.synthetic.main.fragment_user_register.view.*
import javax.inject.Inject

class UserChangeProfileFormFragment : Fragment(), View.OnClickListener {
    var sharedPreferences: SharedPreferences? = null

    @Inject
    lateinit var userRegisterViewModel: UserRegisterViewModel
    var id: String? = ""
    var photo: String? = ""
    var username: String? = ""
    var user_full_name: String? = ""
    var gender: String? = ""
    var email: String? = ""
    var login_method: String? = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as MyApplication).applicationComponent.inject(this)
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
        return inflater.inflate(R.layout.fragment_user_change_profile_form, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        id = sharedPreferences?.getString(
            getString(R.string.id_key),
            getString(R.string.default_value)
        )
        photo = sharedPreferences?.getString(
            getString(R.string.photo_key),
            getString(R.string.default_value)
        )
        username = sharedPreferences?.getString(
            getString(R.string.username_key),
            getString(R.string.default_value)
        )
        user_full_name = sharedPreferences?.getString(
            getString(R.string.username_full_name_key),
            getString(R.string.default_value)
        )
        gender = sharedPreferences?.getString(
            getString(R.string.gender_key),
            getString(R.string.default_value)
        )
        email = sharedPreferences?.getString(
            getString(R.string.email_key),
            getString(R.string.default_value)
        )
        login_method = sharedPreferences?.getString(
            getString(R.string.login_method_key),
            getString(R.string.default_value)
        )
        if (gender == "L") {
            userChangeProfileRadioGender.check(userChangeProfileMale.id)
        } else if (gender == "P") {
            userChangeProfileRadioGender.check(userChangeProfileFemale.id)
        }
        userChangeProfileFullNameText.setText(user_full_name)
        userChangeProfileEmailText.setText(email)
        userChangeProfileUsernameText.setText(username)
        userChangeProfileSaveButton.setOnClickListener(this)
        userChangeProfileCancelButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            userChangeProfileSaveButton -> {
                if (userChangeProfileRadioGender.checkedRadioButtonId == userChangeProfileMale.id) {
                    gender = "L"
                } else if (userChangeProfileRadioGender.checkedRadioButtonId == userChangeProfileFemale.id) {
                    gender = "P"
                } else {
                    gender = ""
                }

                if (gender == "") {
                    Toast.makeText(
                        this.context,
                        "Pilih jenis kelamin",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    user_full_name = userChangeProfileFullNameText.text.toString()
                    username = userChangeProfileUsernameText.text.toString()
                    email = userChangeProfileEmailText.text.toString()
                    if (user_full_name == "" || username == "" || email == "") {
                        Toast.makeText(this.context, "Isi semua form", Toast.LENGTH_SHORT).show()
                    } else {
                        userRegisterViewModel.updateUserProfil(
                            UserRegisterModel(
                                user_full_name = user_full_name!!,
                                username = username!!,
                                email = email!!,
                                gender = gender!!
                            ), requireContext()
                        )
                        with(sharedPreferences?.edit()) {
                            this?.putString(
                                getString(R.string.username_full_name_key),
                                user_full_name
                            )
                            this?.putString(
                                getString(R.string.username_key),
                                username
                            )
                            this?.putString(
                                getString(R.string.email_key),
                                email
                            )
                            this?.putString(
                                getString(R.string.gender_key),
                                gender
                            )
                            this?.commit()
                        }
                        v?.findNavController()
                            ?.navigate(R.id.action_global_userChangeProfileSuccessFragment)
                    }
                }
            }
            userChangeProfileCancelButton -> {
                activity?.finish()
            }
        }
    }
}