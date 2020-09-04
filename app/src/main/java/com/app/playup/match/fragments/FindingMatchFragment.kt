package com.app.playup.match.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.app.playup.R
import com.app.playup.dagger.MyApplication
import com.app.playup.match.model.FindingMatchModel
import com.app.playup.match.viewmodel.MatchViewModel
import com.app.playup.user.viewmodel.UserLoginViewModel
import kotlinx.android.synthetic.main.fragment_finding_match.*
import kotlinx.coroutines.*
import javax.inject.Inject

class FindingMatchFragment : Fragment(), View.OnClickListener {
    val coroutineScope = CoroutineScope(Dispatchers.IO)
    var status: String? = ""
    var i = 0

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
        return inflater.inflate(R.layout.fragment_finding_match, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        status = arguments?.getString("status")
        matchFindingCancelButton.setOnClickListener(this)
        findMatchCoRoutine(1000)
        matchViewModel.matchFindResponse.observe(viewLifecycleOwner, Observer {
            if (it.code == 200.toString()) {
                coroutineScope.cancel()
                if (it != null) {
                    matchViewModel.matchFindResponseData.observe(viewLifecycleOwner, Observer {
                    })
                    view.findNavController().navigate(R.id.action_global_foundMatchFragment)
                }
            }
        })

        matchViewModel.matchWaitResponse.observe(viewLifecycleOwner, Observer {
            if (it.code == 200.toString()) {
                coroutineScope.cancel()
                if (it != null) {
                    matchViewModel.matchWaitResponseData.observe(viewLifecycleOwner, Observer {
                    })
                    view.findNavController().navigate(R.id.action_global_foundMatchFragment)
                }
            }
        })
    }

    override fun onClick(v: View?) {
        when (v) {
            matchFindingCancelButton -> {
                coroutineScope.cancel()
                activity?.finish()
            }
        }
    }

    fun findMatchCoRoutine(interval: Long) {
        val findingMatchModel = FindingMatchModel(
            id = "default",
            photo = "default",
            username = "default",
            user_full_name = "default",
            gender = "L",
            email = "a.gmail@gmail.com"
        )
        coroutineScope.launch {
            while (true) {
                i++
                println(i)
                delay(interval)
                if (status == "FIND") {
                    matchViewModel.findOpponentSingleBadminton(findingMatchModel)
                } else {
                    matchViewModel.waitOpponentSingleBadminton(findingMatchModel)
                }
            }
        }
    }
}