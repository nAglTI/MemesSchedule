package com.nagl.memesschedule.ui.schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nagl.memesschedule.R
import com.nagl.memesschedule.data.model.UniPair
import com.nagl.memesschedule.data.model.UniPair.Companion.uniPairKey
import com.nagl.memesschedule.databinding.FragmentScheduleDayBinding
import com.nagl.memesschedule.utils.PairUtils
import com.nagl.memesschedule.utils.getDayOfWeekDate
import com.nagl.memesschedule.utils.parcelableArrayList

class ScheduleDayFragment : Fragment() {

    private lateinit var binding: FragmentScheduleDayBinding
    private val pairAdapter = PairAdapter()

    private val pairList: ArrayList<UniPair> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentScheduleDayBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey(uniPairKey) }?.apply {
            pairList.clear()
            pairList.addAll(parcelableArrayList<UniPair>(uniPairKey) as ArrayList<UniPair>)
            binding.dayOfWeekText.text = getString(R.string.str_day_of_week_with_date, PairUtils.getDayOfWeekByNumber(pairList[0].dayNumber), getDayOfWeekDate(pairList[0].dayNumber))
            binding.pairsListRecyclerView.adapter = pairAdapter
            pairAdapter.submitList(pairList)
        }
    }
}