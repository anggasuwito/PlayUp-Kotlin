package com.app.playup.menu.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import com.app.playup.R
import com.app.playup.dagger.MyApplication
import com.app.playup.match.viewmodel.MatchViewModel
import kotlinx.android.synthetic.main.fragment_menu_play.*
import javax.inject.Inject

class MenuPlayFragment : Fragment(),View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        menuPlayFindOpponentButton.setOnClickListener(this)
        menuPlayWaitOpponentButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v){
            menuPlayFindOpponentButton->{
                v?.findNavController()?.navigate(R.id.action_global_matchActivity, bundleOf("status" to "FIND"))
            }
            menuPlayWaitOpponentButton->{
                v?.findNavController()?.navigate(R.id.action_global_matchActivity, bundleOf("status" to "WAIT"))
            }
        }
    }
}