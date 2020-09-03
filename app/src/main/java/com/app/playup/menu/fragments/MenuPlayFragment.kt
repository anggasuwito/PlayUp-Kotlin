package com.app.playup.menu.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.app.playup.R
import kotlinx.android.synthetic.main.fragment_menu_play.*

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
                v?.findNavController()?.navigate(R.id.action_global_matchActivity)
            }
            menuPlayWaitOpponentButton->{
                v?.findNavController()?.navigate(R.id.action_global_matchActivity)
            }
        }
    }
}