package com.app.playup.user.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.app.playup.R
import com.app.playup.dagger.MyApplication
import com.app.playup.user.model.UserLoginModel
import com.app.playup.user.model.UserRegisterModel
import com.app.playup.user.viewmodel.UserLoginViewModel
import com.app.playup.user.viewmodel.UserRegisterViewModel
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
import kotlinx.android.synthetic.main.fragment_user_register.*
import java.lang.Exception
import javax.inject.Inject

class UserLoginFragment : Fragment(), View.OnClickListener {
    val GOOGLE_SIGN_IN_REQUEST = 1001
    lateinit var navController: NavController

    @Inject
    lateinit var userLoginViewModel: UserLoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as MyApplication).applicationComponent.inject(this)
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

        navController = Navigation.findNavController(view)
        userLoginToRegister.setOnClickListener(this)
        userLoginButton.setOnClickListener(this)
        userLoginFacebookButton.setOnClickListener(this)
        userLoginGoogleButton.setOnClickListener(this)
        userLoginViewModel.userLoginResponse.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer {
                if (it.code == 500.toString()) {
                    Toast.makeText(
                        this.context,
                        "Username atau password tidak sesuai",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(this.context, "Login sukses", Toast.LENGTH_SHORT)
                        .show()
                    navController.navigate(R.id.action_global_userMenuActivity)
                }
            })
    }

    override fun onClick(v: View?) {
        when (v) {
            userLoginToRegister -> {
                navController.navigate(R.id.action_global_userRegisterFragment)
            }
            userLoginButton -> {
                val userLoginModel = UserLoginModel(
                    username = userLoginUsername.text.toString(),
                    password = userLoginPassword.text.toString()
                )
                if (userLoginUsername.text.toString() == "" ||
                    userLoginPassword.text.toString() == ""
                ) {
                    Toast.makeText(this.context, "Isi semua form", Toast.LENGTH_SHORT).show()
                } else {
                    userLoginViewModel.loginUser(userLoginModel)
                }
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