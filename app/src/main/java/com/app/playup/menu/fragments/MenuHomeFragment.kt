package com.app.playup.menu.fragments

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.playup.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import kotlinx.android.synthetic.main.fragment_menu_home.*


class MenuHomeFragment : Fragment() {
    var sharedPreferences: SharedPreferences? = null
    var googleUsername: String? = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = activity?.getSharedPreferences(
            getString(R.string.shared_preference_name),
            Context.MODE_PRIVATE
        )
        googleProfileResponse()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu_home, container, false)
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
        if (loginMethod == "googleLogin") {
            menuHomeText.text = "Selamat datang, $googleUsername"
        } else {
            menuHomeText.text = "Selamat datang, $username"
        }
    }

    //google profile response
    fun googleProfileResponse() {
        val acct = GoogleSignIn.getLastSignedInAccount(activity)
        if (acct != null) {
            val personName = acct.displayName
            val personGivenName = acct.givenName
            val personFamilyName = acct.familyName
            val personEmail = acct.email
            val personId = acct.id
            val personPhoto: Uri? = acct.photoUrl
            googleUsername = personName
        }
    }
}