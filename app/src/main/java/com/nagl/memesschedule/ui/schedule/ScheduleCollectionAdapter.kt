package com.nagl.memesschedule.ui.schedule

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.nagl.memesschedule.data.model.UniPair
import com.nagl.memesschedule.data.model.UniPair.Companion.uniPairKey

class ScheduleCollectionAdapter(
    private val schedule: ArrayList<UniPair>,
    fragment: Fragment,
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        val set = mutableSetOf<Int>()
        schedule.forEach {
            set.add(it.dayNumber)
        }
        return set.size
    }

    private fun getDayNumbers(): Set<Int> {
        val set = mutableSetOf<Int>()
        schedule.forEach {
            set.add(it.dayNumber)
        }
        return set
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = ScheduleDayFragment()
        fragment.arguments = Bundle().apply {
            putParcelableArrayList(
                uniPairKey,
                ArrayList(schedule.filter { getDayNumbers().indexOf(it.dayNumber) == position })
            )
        }
        return fragment
    }
}
