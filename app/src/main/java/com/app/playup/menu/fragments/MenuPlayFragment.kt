package com.app.playup.menu.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import com.app.playup.R
import com.app.playup.dagger.MyApplication
import com.app.playup.match.viewmodel.MatchViewModel
import kotlinx.android.synthetic.main.fragment_menu_play.*
import javax.inject.Inject

class MenuPlayFragment : Fragment(),View.OnClickListener {
    var sharedPreferences: SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        return inflater.inflate(R.layout.fragment_menu_play, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val loginMethod = sharedPreferences?.getString(
            getString(R.string.login_method_key),
            getString(R.string.default_value)
        )
        if(loginMethod == "googleLogin"){
            menuPlayFindOpponentButton.setOnClickListener{
                Toast.makeText(
                    this.context,
                    "Tidak bisa main dengan login google",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }else if (loginMethod == "facebookLogin"){
            menuPlayFindOpponentButton.setOnClickListener{
                Toast.makeText(
                    this.context,
                    "Tidak bisa main dengan login facebook",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }else{
            menuPlayFindOpponentButton.setOnClickListener{
                view?.findNavController()?.navigate(R.id.action_global_matchActivity)
            }
        }

//        menuPlayWaitOpponentButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v){
//            menuPlayWaitOpponentButton->{
//                v?.findNavController()?.navigate(R.id.action_global_matchActivity, bundleOf("status" to "WAIT"))
//            }
        }
    }
}