package com.app.playup.menu.fragments

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.database.Cursor
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.navigation.findNavController
import com.app.playup.R
import com.app.playup.dagger.MyApplication
import com.app.playup.menu.viewmodel.MenuAccountViewModel
import com.app.playup.user.viewmodel.UserLoginViewModel
import com.bumptech.glide.Glide
import com.facebook.Profile
import com.facebook.ProfileTracker
import com.facebook.login.LoginManager
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_menu_account.*
import kotlinx.android.synthetic.main.fragment_menu_home.*
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class MenuAccountFragment : Fragment(), View.OnClickListener {
    var sharedPreferences: SharedPreferences? = null
    val OPEN_CAMERA_REQUEST_CODE = 13
    lateinit var photoFile: File
    lateinit var currentPhotoPath: String
    var username: String? = ""
    var photo: String? = ""
    var id: String? = ""
    var rankIdUser: String? = ""
    var rankMatchUser: String? = ""
    var rankGradeUser: String? = ""
    var loginMethod: String? = ""

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
        if (loginMethod == "appLogin") {
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
            menuAccountLogout.setOnClickListener(this)
            userLoginViewModel.getUserById(id!!)
            userLoginViewModel.userByIdResponseData.observe(
                viewLifecycleOwner,
                androidx.lifecycle.Observer {
                    if (it.rank_user_match_count == "DEFAULT") {
                        it.rank_user_match_count = "0"
                    }
                    if (it.rank_user_grade_count == "DEFAULT") {
                        it.rank_user_grade_count = "0"
                    }
                    var rankKalah = (it.rank_user_match_count?.toInt()
                        ?.minus(it.rank_user_grade_count!!.toInt())).toString()
                    menuAccountText.text =
                        "$username\nMatch : ${it.rank_user_match_count}\nMenang : ${it.rank_user_grade_count}\nKalah : $rankKalah"
                })
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

        if (loginMethod == "googleLogin") {
            googleProfileResponse()
            menuAccountSettingProfile.setOnClickListener {
                Toast.makeText(
                    this.context,
                    "Tidak bisa ubah profil dengan google login",
                    Toast.LENGTH_SHORT
                ).show()
            }
            menuAccountImage.setOnClickListener {
                Toast.makeText(
                    this.context,
                    "Tidak bisa ubah gambar dengan google login",
                    Toast.LENGTH_SHORT
                ).show()
            }
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
            facebookProfileResponse()
            menuAccountSettingProfile.setOnClickListener {
                Toast.makeText(
                    this.context,
                    "Tidak bisa ubah profil dengan facebook login",
                    Toast.LENGTH_SHORT
                ).show()
            }
            menuAccountImage.setOnClickListener {
                Toast.makeText(
                    this.context,
                    "Tidak bisa ubah gambar dengan facebook login",
                    Toast.LENGTH_SHORT
                ).show()
            }
            menuAccountLogout.setOnClickListener {
                LoginManager.getInstance().logOut()
                with(sharedPreferences?.edit()) {
                    this?.clear()
                    this?.commit()
                }
                activity?.finish()
            }
        } else {
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
            menuAccountLogout.setOnClickListener(this)
            userLoginViewModel.getUserById(id!!)
            userLoginViewModel.userByIdResponseData.observe(
                viewLifecycleOwner,
                androidx.lifecycle.Observer {
                    if (it.rank_user_match_count == "DEFAULT") {
                        it.rank_user_match_count = "0"
                    }
                    if (it.rank_user_grade_count == "DEFAULT") {
                        it.rank_user_grade_count = "0"
                    }
                    var rankKalah = (it.rank_user_match_count?.toInt()
                        ?.minus(it.rank_user_grade_count!!.toInt())).toString()
                    menuAccountText.text =
                        "$username\nMatch : ${it.rank_user_match_count}\nMenang : ${it.rank_user_grade_count}\nKalah : $rankKalah"
                })

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
            if (personName != "" || personName != null) {
                if (personPhoto == null) {
                    Picasso.get().load(R.drawable.google_icon_jpg).into(menuAccountImage)
                } else {
                    Picasso.get().load(personPhoto).into(menuAccountImage)
                }
                menuAccountText.text = "$personName\nMatch : 0\nMenang : 0\nKalah : 0"
            }
        }
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
                    var facebookProfileId = currentProfile.id
                    var facebookProfileFname = currentProfile.firstName
                    var facebookProfileMname = currentProfile.middleName
                    var facebookProfileLname = currentProfile.lastName
                    var facebookProfileName = currentProfile.name
                    var facebookProfileLinkUri = currentProfile.linkUri
                    var facebookProfilePicture =
                        currentProfile.getProfilePictureUri(150, 150)
                    if (facebookProfileName != null || facebookProfileName != "") {
                        if (facebookProfilePicture == null) {
                            Picasso.get().load(R.drawable.facebook_icon_jpg).into(menuAccountImage)
                        } else {
                            Picasso.get().load(facebookProfilePicture).into(menuAccountImage)
                        }
                        menuAccountText.text =
                            "$facebookProfileName\nMatch : 0\nMenang : 0\nKalah : 0"
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
                if (facebookProfilePicture == null) {
                    Picasso.get().load(R.drawable.facebook_icon_jpg).into(menuAccountImage)
                } else {
                    Picasso.get().load(facebookProfilePicture).into(menuAccountImage)
                }
                menuAccountText.text =
                    "$facebookProfileName\nMatch : 0\nMenang : 0\nKalah : 0"
            }
        }
    }
}

