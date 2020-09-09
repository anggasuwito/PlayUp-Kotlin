package com.app.playup.menu.fragments

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.playup.R
import com.facebook.Profile
import com.facebook.ProfileTracker
import com.google.android.gms.auth.api.signin.GoogleSignIn
import kotlinx.android.synthetic.main.fragment_menu_home.*


class MenuHomeFragment : Fragment() {
    var sharedPreferences: SharedPreferences? = null
    var googleUsername: String? = ""
    var facebookUsername: String? = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = activity?.getSharedPreferences(
            getString(R.string.shared_preference_name),
            Context.MODE_PRIVATE
        )
        googleProfileResponse()
        facebookProfileResponse()
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
        }  else if(loginMethod == "appLogin") {
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
                    menuHomeText.text = "Selamat datang, $facebookProfileName"
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
            menuHomeText.text = "Selamat datang, $facebookProfileName"
        }
    }
}