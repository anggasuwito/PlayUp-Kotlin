package com.app.playup.user.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.app.playup.R
import kotlinx.android.synthetic.main.fragment_user_login.*
import kotlinx.android.synthetic.main.fragment_user_register.*
import java.util.*
import javax.xml.datatype.DatatypeConstants.MONTHS

class UserRegisterFragment : Fragment(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        userRegisterToLogin.setOnClickListener(this)
        userRegisterButton.setOnClickListener(this)
        userRegisterBirthday.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            userRegisterToLogin -> {
                v?.findNavController()?.navigate(R.id.action_global_userLoginFragment)
            }
            userRegisterButton ->{

            }
            userRegisterBirthday->{
                val c = Calendar.getInstance()
                val year = c.get(Calendar.YEAR)
                val month = c.get(Calendar.MONTH)
                val day = c.get(Calendar.DAY_OF_MONTH)


                val dpd = DatePickerDialog(this.requireContext(), DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    // Display Selected date in textbox
                    userRegisterBirthday.setText("" + dayOfMonth + " " + MONTHS + ", " + year)
                }, year, month, day)

                dpd.show()
            }
        }
    }
}