package com.app.playup.match.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.navigation.findNavController
import com.app.playup.R
import com.app.playup.dagger.MyApplication
import com.app.playup.match.viewmodel.MatchViewModel
import kotlinx.android.synthetic.main.fragment_found_match.*
import javax.inject.Inject

class FoundMatchFragment : Fragment(),View.OnClickListener {
    @Inject
    lateinit var matchViewModel: MatchViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as MyApplication).applicationComponent.inject(this)
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
            matchViewModel.resetRoom()
            requireActivity().finish()
        }
    }

    override fun onClick(v: View?) {
        when(v){
            matchFoundButton->{
                matchViewModel.resetRoom()
                v?.findNavController()?.navigate(R.id.action_global_chatDetailsFragment)
            }
        }
    }
}