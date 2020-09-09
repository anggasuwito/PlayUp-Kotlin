package com.app.playup.match.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.navigation.findNavController
import com.app.playup.R
import kotlinx.android.synthetic.main.fragment_found_match.*

class FoundMatchFragment : Fragment(),View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_found_match, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        matchFoundButton.setOnClickListener(this)
        requireActivity().onBackPressedDispatcher.addCallback(this){
            requireActivity().finish()
        }
    }

    override fun onClick(v: View?) {
        when(v){
            matchFoundButton->{
                v?.findNavController()?.navigate(R.id.action_global_chatDetailsFragment)
            }
        }
    }
}