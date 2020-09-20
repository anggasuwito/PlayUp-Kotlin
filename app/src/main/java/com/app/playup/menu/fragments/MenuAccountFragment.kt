package com.app.playup.menu.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.findNavController
import com.app.playup.R
import com.app.playup.dagger.MyApplication
import com.app.playup.menu.viewmodel.MenuAccountViewModel
import com.app.playup.user.model.UserLoginModel
import com.app.playup.user.model.UserLoginResponseDataModel
import com.app.playup.user.viewmodel.UserLoginViewModel
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_menu_account.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class MenuAccountFragment : Fragment(), View.OnClickListener {
    var sharedPreferences: SharedPreferences? = null
    val OPEN_CAMERA_REQUEST_CODE = 13
    val GOOGLE_SIGN_IN_REQUEST = 666
    var callbackManager: CallbackManager? = null
    lateinit var photoFile: File
    lateinit var currentPhotoPath: String
    var username: String? = ""
    var photo: String? = ""
    var id: String? = ""
    var rankIdUser: String? = ""
    var rankMatchUser: String? = ""
    var rankGradeUser: String? = ""
    var loginMethod: String? = ""
    var googleAccountID: String? = ""
    var facebookAccountID: String? = ""

    @Inject
    lateinit var menuAccountViewModel: MenuAccountViewModel

    @Inject
    lateinit var userLoginViewModel: UserLoginViewModel
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
        return inflater.inflate(R.layout.fragment_menu_account, container, false)
    }

    override fun onResume() {
        super.onResume()
        username = sharedPreferences?.getString(
            getString(R.string.username_key),
            getString(R.string.default_value)
        )
        photo = sharedPreferences?.getString(
            getString(R.string.photo_key),
            getString(R.string.default_value)
        )
        rankMatchUser = sharedPreferences?.getString(
            getString(R.string.rank_user_match_count_key),
            getString(R.string.default_value)
        )
        rankGradeUser = sharedPreferences?.getString(
            getString(R.string.rank_user_grade_count_key),
            getString(R.string.default_value)
        )
        loginMethod = sharedPreferences?.getString(
            getString(R.string.login_method_key),
            getString(R.string.default_value)
        )
        if (photo == "facebookPhotoDefault.jpg") {
            Picasso.get().load(R.drawable.facebook_icon_jpg).into(menuAccountImage)
        } else if (photo == "googlePhotoDefault.jpg") {
            Picasso.get().load(R.drawable.google_icon_jpg).into(menuAccountImage)
        } else if (photo == "defaultUserPhoto.jpg") {
            Picasso.get().load(R.drawable.user_icon_jpg).into(menuAccountImage)
        } else {
            menuAccountViewModel.getUserPhoto(
                photo!!,
                menuAccountImage,
                this.requireActivity()
            )
            println("IMAGE FETCH")
        }
        menuAccountSettingProfile.setOnClickListener(this)
        menuAccountImage.setOnClickListener(this)
        userLoginViewModel.getUserById(id!!)
        userLoginViewModel.userByIdResponseData.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer {
                if (it.google_account != "") {
                    println("MASUK GOOGLE IF")
                    menuAccountGoogleButton.text = "PUTUSKAN DARI GOOGLE"
                    menuAccountGoogleButton.setOnClickListener {
                        userLoginViewModel.updateGoogleAccount(
                            UserLoginResponseDataModel(
                                google_account = "", id = id!!
                            )
                        )
                        Toast.makeText(
                            requireContext(),
                            "Akun ini berhasil diputuskan dari google",
                            Toast.LENGTH_SHORT
                        ).show()
                        refreshFragment()
                    }
                } else {
                    println("MASUK GOOGLE ELSE")
                    menuAccountGoogleButton.text = "HUBUNGKAN DENGAN GOOGLE"
                    menuAccountGoogleButton.setOnClickListener {
                        googleSignIn()
                    }
                }

                if (it.facebook_account != "") {
                    println("MASUK FACEBOOK IF")
                    menuAccountFacebookButtonMaterial.text = "PUTUSKAN DARI FACEBOOK"
                    menuAccountFacebookButton.setOnClickListener {

                    }
                    menuAccountFacebookButtonMaterial.setOnClickListener {
                        userLoginViewModel.updateFacebookAccount(
                            UserLoginResponseDataModel(
                                facebook_account = "", id = id!!
                            )
                        )
                        Toast.makeText(
                            requireContext(),
                            "Akun ini berhasil diputuskan dari facebook",
                            Toast.LENGTH_SHORT
                        ).show()
                        refreshFragment()
                    }
                } else {
                    println("MASUK FACEBOOK ELSE")
                    menuAccountFacebookButtonMaterial.text = "HUBUNGKAN DENGAN FACEBOOK"
                    menuAccountFacebookButton.setOnClickListener {
                        facebookSignIn()
                    }
                    menuAccountFacebookButtonMaterial.setOnClickListener {
                        menuAccountFacebookButton.performClick()
                    }
                }

                if (it.rank_user_match_count == "DEFAULT") {
                    it.rank_user_match_count = "0"
                }
                if (it.rank_user_grade_count == "DEFAULT") {
                    it.rank_user_grade_count = "0"
                }
                var rankKalah = (it.rank_user_match_count?.toInt()
                    ?.minus(it.rank_user_grade_count!!.toInt())).toString()
                menuAccountUsernameText.text = "$username"
                menuAccountMatchText.text = "Pertandingan : ${it.rank_user_match_count}"
                menuAccountWinText.text = "Menang : ${it.rank_user_grade_count}"
                menuAccountLoseText.text = "Kalah : $rankKalah"
            })
        userLoginViewModel.userLoginFacebookResponseData.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer {
                if (it != null) {
                    LoginManager.getInstance().logOut()
                    Toast.makeText(
                        requireContext(),
                        "Akun facebook ini sudah terhubung dengan akun lain",
                        Toast.LENGTH_SHORT
                    ).show()
                    refreshFragment()
                } else {
                    userLoginViewModel.updateFacebookAccount(
                        UserLoginResponseDataModel(
                            facebook_account = facebookAccountID!!, id = id!!
                        )
                    )
                    Toast.makeText(
                        requireContext(),
                        "Akun ini berhasil terhubung dengan facebook",
                        Toast.LENGTH_SHORT
                    ).show()
                    LoginManager.getInstance().logOut()
                    refreshFragment()
                }
            })
        userLoginViewModel.userLoginGoogleResponseData.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer {
                if (it != null) {
                    googleSignOutWithButton()
                    revokeAccessGoogleSignOut()
                    Toast.makeText(
                        requireContext(),
                        "Akun google ini sudah terhubung dengan akun lain",
                        Toast.LENGTH_SHORT
                    ).show()
                    refreshFragment()
                } else {
                    userLoginViewModel.updateGoogleAccount(
                        UserLoginResponseDataModel(
                            google_account = googleAccountID!!,
                            id = id!!
                        )
                    )
                    Toast.makeText(
                        requireContext(),
                        "Akun ini berhasil terhubung dengan google",
                        Toast.LENGTH_SHORT
                    ).show()
                    googleSignOutWithButton()
                    revokeAccessGoogleSignOut()
                    refreshFragment()
                }
            })

        if (loginMethod == "googleLogin") {
            menuAccountLogout.setOnClickListener {
                googleSignOutWithButton()
                revokeAccessGoogleSignOut()
                with(sharedPreferences?.edit()) {
                    this?.clear()
                    this?.commit()
                }
                activity?.finish()
            }
        } else if (loginMethod == "facebookLogin") {
            menuAccountLogout.setOnClickListener {
                LoginManager.getInstance().logOut()
                with(sharedPreferences?.edit()) {
                    this?.clear()
                    this?.commit()
                }
                activity?.finish()
            }
        } else {
            menuAccountLogout.setOnClickListener(this)
        }
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
        rankIdUser = sharedPreferences?.getString(
            getString(R.string.rank_id_key),
            getString(R.string.default_value)
        )
        rankMatchUser = sharedPreferences?.getString(
            getString(R.string.rank_user_match_count_key),
            getString(R.string.default_value)
        )
        rankGradeUser = sharedPreferences?.getString(
            getString(R.string.rank_user_grade_count_key),
            getString(R.string.default_value)
        )
        loginMethod = sharedPreferences?.getString(
            getString(R.string.login_method_key),
            getString(R.string.default_value)
        )
        menuAccountViewModel.menuAccountResponseData.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer {
                if (it != null) {
                    with(sharedPreferences?.edit()) {
                        this?.putString(
                            getString(R.string.photo_key),
                            it.photo
                        )
                        this?.commit()
                    }
                    menuAccountViewModel.getUserPhoto(
                        it.photo,
                        menuAccountImage,
                        this.requireActivity()
                    )
                }
            })


        if (photo == "facebookPhotoDefault.jpg") {
            Picasso.get().load(R.drawable.facebook_icon_jpg).into(menuAccountImage)
        } else if (photo == "googlePhotoDefault.jpg") {
            Picasso.get().load(R.drawable.google_icon_jpg).into(menuAccountImage)
        } else if (photo == "defaultUserPhoto.jpg") {
            Picasso.get().load(R.drawable.user_icon_jpg).into(menuAccountImage)
        } else {
            menuAccountViewModel.getUserPhoto(
                photo!!,
                menuAccountImage,
                this.requireActivity()
            )
        }
        menuAccountSettingProfile.setOnClickListener(this)
        menuAccountImage.setOnClickListener(this)
        userLoginViewModel.getUserById(id!!)
        userLoginViewModel.userByIdResponseData.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer {
                if (it.google_account != "") {
                    println("MASUK GOOGLE IF")
                    menuAccountGoogleButton.text = "PUTUSKAN DARI GOOGLE"
                    menuAccountGoogleButton.setOnClickListener {
                        userLoginViewModel.updateGoogleAccount(
                            UserLoginResponseDataModel(
                                google_account = "", id = id!!
                            )
                        )
                        Toast.makeText(
                            requireContext(),
                            "Akun ini berhasil diputuskan dari google",
                            Toast.LENGTH_SHORT
                        ).show()
                        refreshFragment()
                    }
                } else {
                    println("MASUK GOOGLE ELSE")
                    menuAccountGoogleButton.text = "HUBUNGKAN DENGAN GOOGLE"
                    menuAccountGoogleButton.setOnClickListener {
                        googleSignIn()
                    }
                }

                if (it.facebook_account != "") {
                    println("MASUK FACEBOOK IF")
                    menuAccountFacebookButtonMaterial.text = "PUTUSKAN DARI FACEBOOK"
                    menuAccountFacebookButton.setOnClickListener {

                    }
                    menuAccountFacebookButtonMaterial.setOnClickListener {
                        userLoginViewModel.updateFacebookAccount(
                            UserLoginResponseDataModel(
                                facebook_account = "", id = id!!
                            )
                        )
                        Toast.makeText(
                            requireContext(),
                            "Akun ini berhasil diputuskan dari facebook",
                            Toast.LENGTH_SHORT
                        ).show()
                        refreshFragment()
                    }
                } else {
                    println("MASUK FACEBOOK ELSE")
                    menuAccountFacebookButtonMaterial.text = "HUBUNGKAN DENGAN FACEBOOK"
                    menuAccountFacebookButton.setOnClickListener {
                        facebookSignIn()
                    }
                    menuAccountFacebookButtonMaterial.setOnClickListener {
                        menuAccountFacebookButton.performClick()
                    }
                }

                if (it.rank_user_match_count == "DEFAULT") {
                    it.rank_user_match_count = "0"
                }
                if (it.rank_user_grade_count == "DEFAULT") {
                    it.rank_user_grade_count = "0"
                }
                var rankKalah = (it.rank_user_match_count?.toInt()
                    ?.minus(it.rank_user_grade_count!!.toInt())).toString()
                menuAccountUsernameText.text = "$username"
                menuAccountMatchText.text = "Pertandingan : ${it.rank_user_match_count}"
                menuAccountWinText.text = "Menang : ${it.rank_user_grade_count}"
                menuAccountLoseText.text = "Kalah : $rankKalah"
            })
        userLoginViewModel.userLoginFacebookResponseData.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer {
                if (it != null) {
                    LoginManager.getInstance().logOut()
                    Toast.makeText(
                        requireContext(),
                        "Akun facebook ini sudah terhubung dengan akun lain",
                        Toast.LENGTH_SHORT
                    ).show()
                    refreshFragment()
                } else {
                    userLoginViewModel.updateFacebookAccount(
                        UserLoginResponseDataModel(
                            facebook_account = facebookAccountID!!, id = id!!
                        )
                    )
                    Toast.makeText(
                        requireContext(),
                        "Akun ini berhasil terhubung dengan facebook",
                        Toast.LENGTH_SHORT
                    ).show()
                    LoginManager.getInstance().logOut()
                    refreshFragment()
                }
            })
        userLoginViewModel.userLoginGoogleResponseData.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer {
                if (it != null) {
                    googleSignOutWithButton()
                    revokeAccessGoogleSignOut()
                    Toast.makeText(
                        requireContext(),
                        "Akun google ini sudah terhubung dengan akun lain",
                        Toast.LENGTH_SHORT
                    ).show()
                    refreshFragment()
                } else {
                    userLoginViewModel.updateGoogleAccount(
                        UserLoginResponseDataModel(
                            google_account = googleAccountID!!,
                            id = id!!
                        )
                    )
                    Toast.makeText(
                        requireContext(),
                        "Akun ini berhasil terhubung dengan google",
                        Toast.LENGTH_SHORT
                    ).show()
                    googleSignOutWithButton()
                    revokeAccessGoogleSignOut()
                    refreshFragment()
                }
            })


        if (loginMethod == "googleLogin") {
            menuAccountLogout.setOnClickListener {
                googleSignOutWithButton()
                revokeAccessGoogleSignOut()
                with(sharedPreferences?.edit()) {
                    this?.clear()
                    this?.commit()
                }
                activity?.finish()
            }
        } else if (loginMethod == "facebookLogin") {
            menuAccountLogout.setOnClickListener {
                LoginManager.getInstance().logOut()
                with(sharedPreferences?.edit()) {
                    this?.clear()
                    this?.commit()
                }
                activity?.finish()
            }
        } else {
            menuAccountLogout.setOnClickListener(this)
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            menuAccountLogout -> {
                with(sharedPreferences?.edit()) {
                    this?.clear()
                    this?.commit()
                }
                activity?.finish()
            }
            menuAccountSettingProfile -> {
                v?.findNavController()?.navigate(R.id.action_global_userChangeProfileActivity)
            }
            menuAccountImage -> {
                //dialog maker
//                val changeImageDialog = AlertDialog.Builder(this.context)
//                changeImageDialog.setTitle(R.string.change_photo_prompt).setItems(
//                    R.array.change_photo_arrays,
//                    DialogInterface.OnClickListener { dialog, selectedOption ->
//                        //selectedOption menunjukan index item
//                        if (selectedOption == 0) {
//                            openCamera()
//                        } else if (selectedOption == 1) {
//                            browseImageFile()
//                        }
//                    }).show()
                imagePicker()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 66 && resultCode == Activity.RESULT_OK) {
            val getFile = ImagePicker.getFile(data)
            val requestBody = getFile?.asRequestBody("multipart".toMediaTypeOrNull())
            val imageFileChoosed =
                MultipartBody.Part.createFormData("image", getFile?.name, requestBody!!)
//            val data = MultipartBody.Part.createFormData(
//                "data",
//                """{"username":"${username}","image_name":"${photo}"}"""
//            )
            val userId = MultipartBody.Part.createFormData(
                "id",
                "$id"
            )
            menuAccountViewModel.menuAccountChangePhoto(imageFileChoosed, userId)
        }
        if (requestCode === GOOGLE_SIGN_IN_REQUEST) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task: Task<GoogleSignInAccount> =
                GoogleSignIn.getSignedInAccountFromIntent(data)
            handleGoogleSignInResult(task)
        }
        callbackManager?.onActivityResult(requestCode, resultCode, data)
    }

    //image picker library
    private fun imagePicker() {
        ImagePicker.with(this)
            .compress(1024)                     //Final image size will be less than 1 MB(Optional)
            .maxResultSize(
                1080,
                1080
            )     //Final image resolution will be less than 1080 x 1080(Optional)
//                    .cropSquare()
            .start(66)
    }

    //fungsi keluar google namun data google masih terhubung dengan aplikasi (unsecure)
    fun googleSignOutWithButton() {
        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
        val mGoogleSignInClient = GoogleSignIn.getClient(this.requireActivity(), gso);
        //        mGoogleSignInClient.signOut()
        //            .addOnCompleteListener(this, object : OnCompleteListener<Void?> {
        //                override fun onComplete(p0: Task<Void?>) {
        //
        //                }
        //            })
        mGoogleSignInClient.signOut()
            .addOnCompleteListener {
                println("LOGOUT IT = " + it)
            }
    }

    //fungsi keluar google dan memutuskan data google dengan aplikasi (secure and recommended)
    private fun revokeAccessGoogleSignOut() {
        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
        val mGoogleSignInClient = GoogleSignIn.getClient(this.requireActivity(), gso);
        //        mGoogleSignInClient.revokeAccess()
        //            .addOnCompleteListener(
        //                this,
        //                OnCompleteListener<Void?> {
        //                    // ...
        //                })
        mGoogleSignInClient.revokeAccess()
            .addOnCompleteListener {
                println("REVOKE IT = " + it)
            }
    }

    //facebook oauth login
    fun facebookSignIn() {

        callbackManager = CallbackManager.Factory.create();

        var loginButton = menuAccountFacebookButton as LoginButton
        loginButton.setReadPermissions("email")
        loginButton.setFragment(this)

        // Callback registration
        loginButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult?> {
            override fun onSuccess(loginResult: LoginResult?) {
                // App code
                println("SUCCESS FB = " + loginResult?.accessToken)
                facebookProfileResponse()
            }

            override fun onCancel() {
                // App code
                println("CANCEL FB")
            }

            override fun onError(exception: FacebookException) {
                // App code
                println("ERROR FB = " + exception)
            }
        })
    }

    //facebook profile response
    fun facebookProfileResponse() {
        if (Profile.getCurrentProfile() == null) {
            var mProfileTracker: ProfileTracker? = null
            mProfileTracker = object : ProfileTracker() {
                override fun onCurrentProfileChanged(
                    oldProfile: Profile?,
                    currentProfile: Profile
                ) {
                    mProfileTracker?.startTracking()
                    var facebookProfileId = currentProfile.id
                    var facebookProfileFname = currentProfile.firstName
                    var facebookProfileMname = currentProfile.middleName
                    var facebookProfileLname = currentProfile.lastName
                    var facebookProfileName = currentProfile.name
                    var facebookProfileLinkUri = currentProfile.linkUri
                    var facebookProfilePicture =
                        currentProfile.getProfilePictureUri(150, 150)
                    if (facebookProfileName != null || facebookProfileName != "") {
                        facebookAccountID = facebookProfileId
                        userLoginViewModel.loginWithFacebook(
                            UserLoginModel(
                                facebook_account = facebookProfileId,
                                google_account = ""
                            )
                        )
                    }
                    mProfileTracker?.stopTracking()
                }
            }
        } else {
            val profile: Profile = Profile.getCurrentProfile()
            var facebookProfileId = profile.id
            var facebookProfileFname = profile.firstName
            var facebookProfileMname = profile.middleName
            var facebookProfileLname = profile.lastName
            var facebookProfileName = profile.name
            var facebookProfileLinkUri = profile.linkUri
            var facebookProfilePicture =
                profile.getProfilePictureUri(150, 150)
            if (facebookProfileName != null || facebookProfileName != "") {
                facebookAccountID = facebookProfileId
                userLoginViewModel.loginWithFacebook(
                    UserLoginModel(
                        facebook_account = facebookProfileId,
                        google_account = ""
                    )
                )
            }
        }
    }

    //function for sign in
    fun googleSignIn() {
        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("1098032043964-lorva2qev51g3097t9jpa12kj31nva39.apps.googleusercontent.com")
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
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN_REQUEST)
    }

    //function ini dipanggil di onActivityResult
    fun handleGoogleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account =
                completedTask.getResult(ApiException::class.java)
            // Signed in successfully, show authenticated UI.
            //  updateUI(account)
            if (account != null) {
                googleProfileResponse()
            } else {
                Toast.makeText(this.context, "Gagal login dengan google", Toast.LENGTH_SHORT).show()
            }
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            // Log.w(TAG, "signInResult:failed code=" + e.statusCode)
            println("signInResult:failed code=" + e.statusCode)
            // updateUI(null)
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
            if (personName != null || personName != "") {
                googleAccountID = personId!!
                userLoginViewModel.loginWithGoogle(
                    UserLoginModel(
                        google_account = personId!!,
                        facebook_account = ""
                    )
                )
            }
        }
    }

    fun refreshFragment(){
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.menuAccountLayout, MenuAccountFragment())?.commit()
    }
}

