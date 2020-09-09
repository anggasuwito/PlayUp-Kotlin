package com.app.playup.match.fragments

import android.content.Context
import android.content.SharedPreferences
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
    var sharedPreferences: SharedPreferences? = null
    var id: String? = ""
    var photo: String? = ""
    var username: String? = ""
    var user_full_name: String? = ""
    var gender: String? = ""
    var email: String? = ""

    @Inject
    lateinit var matchViewModel: MatchViewModel
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
        return inflater.inflate(R.layout.fragment_finding_match, container, false)
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
        user_full_name = sharedPreferences?.getString(
            getString(R.string.username_full_name_key),
            getString(R.string.default_value)
        )
        gender = sharedPreferences?.getString(
            getString(R.string.gender_key),
            getString(R.string.default_value)
        )
        email = sharedPreferences?.getString(
            getString(R.string.email_key),
            getString(R.string.default_value)
        )


//        status = arguments?.getString("status")

        matchFindingCancelButton.setOnClickListener(this)
        findMatchCoRoutine(1000)
        matchViewModel.matchFindResponse.observe(viewLifecycleOwner, Observer {
            if (it.code == 200.toString()) {
                coroutineScope.cancel()
                if (it != null) {
                    matchViewModel.matchFindResponseData.observe(viewLifecycleOwner, Observer {
                        if (it != null) {
                            with(sharedPreferences?.edit()) {
                                this?.putString(
                                    getString(R.string.match_id_key),
                                    it.match_id
                                )
                                this?.commit()
                            }
                            view.findNavController().navigate(R.id.action_global_foundMatchFragment)
                        }
                    })

                }
            }
        })

//        matchViewModel.matchWaitResponse.observe(viewLifecycleOwner, Observer {
//            if (it.code == 200.toString()) {
//                coroutineScope.cancel()
//                if (it != null) {
//                    matchViewModel.matchWaitResponseData.observe(viewLifecycleOwner, Observer {
//                    })
//                    view.findNavController().navigate(R.id.action_global_foundMatchFragment)
//                }
//            }
//        })
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
            id = id!!,
            photo = photo!!,
            username = username!!,
            user_full_name = user_full_name!!,
            gender = gender!!,
            email = email!!
        )
        coroutineScope.launch {
            while (true) {
                delay(interval)
//                if (status == "FIND") {
                matchViewModel.findOpponentSingleBadminton(findingMatchModel)
//                } else {
//                    matchViewModel.waitOpponentSingleBadminton(findingMatchModel)
//                }
            }
        }
    }
}