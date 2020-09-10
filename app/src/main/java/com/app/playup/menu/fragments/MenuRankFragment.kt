package com.app.playup.menu.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.playup.R
import com.app.playup.rank.fragments.*
import kotlinx.android.synthetic.main.fragment_menu_rank.*

class MenuRankFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu_rank, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.supportFragmentManager?.beginTransaction()
            ?.add(R.id.menuRankContainer, RankSingleBadmintonFragment())
            ?.commit()
        menuRankBadmintonSingle.setOnClickListener {
            switchFragment(RankSingleBadmintonFragment())
        }
        menuRankBadmintonDouble.setOnClickListener {
            switchFragment(RankDoubleBadmintonFragment())
        }
        menuRankBilliardSingle.setOnClickListener {
            switchFragment(RankSingleBilliardFragment())
        }
        menuRankBasketBallTriple.setOnClickListener {
            switchFragment(RankTripleBasketBallFragment())
        }
        menuRankTennisBorderSingle.setOnClickListener {
            switchFragment(RankSingleTennisBorderFragment())
        }
    }

    fun switchFragment(fragment: Fragment) {
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.menuRankContainer, fragment)?.commit()
    }
}