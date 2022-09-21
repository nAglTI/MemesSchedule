package com.nagl.memesschedule.ui.schedule

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.nagl.memesschedule.data.model.UniPair

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

    override fun createFragment(position: Int): Fragment {
        // Return a NEW fragment instance in createFragment(int)
        val fragment = ScheduleDayFragment()
        fragment.arguments = Bundle().apply {
            // Our object is just an integer :-P
            putParcelableArrayList("UniPair", ArrayList(schedule.filter { it.dayNumber == position + 1 }))
        }
        return fragment
    }
}
