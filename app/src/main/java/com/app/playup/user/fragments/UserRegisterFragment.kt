package com.app.playup.user.fragments

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.app.playup.R
import com.app.playup.dagger.MyApplication
import com.app.playup.user.model.UserRegisterModel
import com.app.playup.user.viewmodel.UserRegisterViewModel
import kotlinx.android.synthetic.main.fragment_user_login.*
import kotlinx.android.synthetic.main.fragment_user_register.*
import kotlinx.android.synthetic.main.fragment_user_register.view.*
import java.util.*
import javax.inject.Inject
import javax.xml.datatype.DatatypeConstants.MONTHS

class UserRegisterFragment : Fragment(), View.OnClickListener {
    @Inject
    lateinit var userRegisterViewModel: UserRegisterViewModel
    lateinit var navController: NavController
    var gender: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as MyApplication).applicationComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        userRegisterToLogin.setOnClickListener(this)
        userRegisterButton.setOnClickListener(this)
        userRegisterViewModel.userRegisterResponse.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer {
                if (it.code == 500.toString()) {
                    Toast.makeText(
                        this.context,
                        "Username atau email sudah terdaftar",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(this.context, "Buat akun sukses", Toast.LENGTH_SHORT)
                        .show()
                    navController.navigate(R.id.action_global_userSuccessRegisterFragment)
                }
            })
    }

    override fun onClick(v: View?) {
        when (v) {
            userRegisterToLogin -> {
                navController.navigate(R.id.action_global_userLoginFragment)
            }
            userRegisterButton -> {
                if (userRegisterRadioGender.checkedRadioButtonId == userRegisterMale.id) {
                    gender = "L"
                } else if (userRegisterRadioGender.checkedRadioButtonId == userRegisterFemale.id) {
                    gender = "P"
                } else {
                    gender = ""
                }

                val userRegisterModel = UserRegisterModel(
                    username = userRegisterUsername.text.toString(),
                    user_full_name = userRegisterFullName.text.toString(),
                    gender = gender,
                    email = userRegisterEmail.text.toString(),
                    password = userRegisterPassword.text.toString()
                )

                if (userRegisterFullName.text.toString() == "" ||
                    userRegisterEmail.text.toString() == "" ||
                    userRegisterUsername.text.toString() == "" ||
                    userRegisterPassword.text.toString() == "" ||
                    userRegisterConfirmPassword.text.toString() == ""
                ) {
                    Toast.makeText(this.context, "Isi semua form", Toast.LENGTH_SHORT).show()
                } else if (userRegisterPassword.text.toString() !=
                    userRegisterConfirmPassword.text.toString()
                ) {
                    Toast.makeText(
                        this.context,
                        "Konfirmasi password tidak sesuai",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (gender == "") {
                    Toast.makeText(
                        this.context,
                        "Pilih jenis kelamin",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    userRegisterViewModel.registerNewUser(userRegisterModel)
                }
            }
        }
    }
}