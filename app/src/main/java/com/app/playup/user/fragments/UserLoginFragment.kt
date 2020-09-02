package com.app.playup.user.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.app.playup.R
import kotlinx.android.synthetic.main.fragment_user_login.*
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import java.lang.Exception

class UserLoginFragment : Fragment(), View.OnClickListener {
    val GOOGLE_SIGN_IN_REQUEST = 1001
    lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userLoginToRegister.setOnClickListener(this)
        userLoginButton.setOnClickListener(this)
        userLoginFacebookButton.setOnClickListener(this)
        userLoginGoogleButton.setOnClickListener(this)
        navController = Navigation.findNavController(view)
    }

    override fun onClick(v: View?) {
        when (v) {
            userLoginToRegister -> {
                v?.findNavController()?.navigate(R.id.action_global_userRegisterFragment)
            }
            userLoginButton -> {
                v?.findNavController()?.navigate(R.id.action_global_userMenuActivity)
            }
            userLoginFacebookButton -> {

            }
            userLoginGoogleButton -> {
                // Configure Google Sign In
                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build()

                val googleSignInClient = GoogleSignIn.getClient(this.requireActivity(), gso)
                val signInGoogleIntent = googleSignInClient.signInIntent
                startActivityForResult(signInGoogleIntent, GOOGLE_SIGN_IN_REQUEST)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val googleTask = GoogleSignIn.getSignedInAccountFromIntent(data)
        var userGoogleToken: String? = null

        try {
            val googleAccount: GoogleSignInAccount? = googleTask.getResult(ApiException::class.java)
            if (googleAccount != null) {
                userGoogleToken = googleAccount.idToken
            }
        } catch (exception: Exception) {
            println("CATCH = " + exception.toString())
        }

        println("TOKEN = " + userGoogleToken)
        if (requestCode == GOOGLE_SIGN_IN_REQUEST && userGoogleToken != null) {
            navController.navigate(R.id.action_global_userMenuActivity)
        }
    }

}