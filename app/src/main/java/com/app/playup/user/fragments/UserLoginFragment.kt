package com.app.playup.user.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.app.playup.R
import com.app.playup.dagger.MyApplication
import com.app.playup.user.model.UserLoginModel
import com.app.playup.user.viewmodel.UserLoginViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.fragment_user_login.*
import javax.inject.Inject


class UserLoginFragment : Fragment(), View.OnClickListener {
    val RC_SIGN_IN = 666
    var sharedPreferences: SharedPreferences? = null
    lateinit var navController: NavController

    @Inject
    lateinit var userLoginViewModel: UserLoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as MyApplication).applicationComponent.inject(this)
        sharedPreferences =
            activity?.getSharedPreferences(
                getString(R.string.shared_preference_name),
                Context.MODE_PRIVATE
            )
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
        if (sharedPreferences?.contains(getString(R.string.username_key))!! && sharedPreferences?.contains(
                getString(R.string.login_method_key)
            )!!
        ) {
            view?.findNavController().navigate(R.id.action_global_userMenuActivity)
        }
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
                    userLoginViewModel.userLoginResponseData.observe(viewLifecycleOwner, Observer {
                        if (it != null) {
                            with(sharedPreferences?.edit()) {
                                this?.putString(
                                    getString(R.string.id_key),
                                    it.id
                                )
                                this?.putString(
                                    getString(R.string.photo_key),
                                    it.photo
                                )
                                this?.putString(
                                    getString(R.string.username_key),
                                    it.username
                                )
                                this?.putString(
                                    getString(R.string.username_full_name_key),
                                    it.user_full_name
                                )
                                this?.putString(
                                    getString(R.string.gender_key),
                                    it.gender
                                )
                                this?.putString(
                                    getString(R.string.email_key),
                                    it.email
                                )
                                this?.putString(
                                    getString(R.string.login_method_key),
                                    "appLogin"
                                )
                                this?.commit()
                            }
                            navController.navigate(R.id.action_global_userMenuActivity)
                        }
                    })
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
                with(sharedPreferences?.edit()) {
                    this?.putString(
                        getString(R.string.photo_key),
                        "facebookPhotoDefault.jpg"
                    )
                    this?.putString(
                        getString(R.string.username_key),
                        "facebook"
                    )
                    this?.putString(
                        getString(R.string.gender_key),
                        "L"
                    )
                    this?.putString(
                        getString(R.string.login_method_key),
                        "facebookLogin"
                    )
                    this?.commit()
                }
                navController.navigate(R.id.action_global_userMenuActivity)
            }
            userLoginGoogleButton -> {
                googleSignIn()
                with(sharedPreferences?.edit()) {
                    this?.putString(
                        getString(R.string.photo_key),
                        "googlePhotoDefault.jpg"
                    )
                    this?.putString(
                        getString(R.string.username_key),
                        "google"
                    )
                    this?.putString(
                        getString(R.string.gender_key),
                        "P"
                    )
                    this?.putString(
                        getString(R.string.login_method_key),
                        "googleLogin"
                    )
                    this?.commit()
                }
            }
        }
    }

    //function for sign in
    fun googleSignIn() {
        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()

        val mGoogleSignInClient = GoogleSignIn.getClient(this.requireActivity(), gso);

        //check for last account
        val account = GoogleSignIn.getLastSignedInAccount(this.context)
        if (account != null) {
            Toast.makeText(
                this.context,
                "Sukses login dengan google last account",
                Toast.LENGTH_SHORT
            )
        } else {
            Toast.makeText(
                this.context,
                "Gagal login dengan google last account",
                Toast.LENGTH_SHORT
            )
        }

        val signInIntent: Intent = mGoogleSignInClient.getSignInIntent()
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task: Task<GoogleSignInAccount> =
                GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
            navController.navigate(R.id.action_global_userMenuActivity)
        }
    }

    fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account =
                completedTask.getResult(ApiException::class.java)

            // Signed in successfully, show authenticated UI.

            //  updateUI(account)
            if (account != null) {
                Toast.makeText(this.context, "Sukses login dengan google", Toast.LENGTH_SHORT)
            } else {
                Toast.makeText(this.context, "Gagal login dengan google", Toast.LENGTH_SHORT)
            }
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            // Log.w(TAG, "signInResult:failed code=" + e.statusCode)
            println("signInResult:failed code=" + e.statusCode)
            // updateUI(null)
        }
    }

}