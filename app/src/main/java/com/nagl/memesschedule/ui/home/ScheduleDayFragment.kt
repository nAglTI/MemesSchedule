package com.nagl.memesschedule.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nagl.memesschedule.data.model.UniPair
import com.nagl.memesschedule.databinding.FragmentScheduleDayBinding
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
        arguments?.takeIf { it.containsKey("UniPair") }?.apply {
            pairList.addAll(parcelableArrayList<UniPair>("UniPair") as ArrayList<UniPair>)
            binding.textDashboard.text = pairList[0].dayNumber.toString()
            binding.pairsListRecyclerView.adapter = pairAdapter
            pairAdapter.submitList(pairList)
        }
    }
}