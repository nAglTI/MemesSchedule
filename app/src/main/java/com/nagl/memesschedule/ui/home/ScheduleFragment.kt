package com.nagl.memesschedule.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.nagl.memesschedule.data.model.Schedule
import com.nagl.memesschedule.data.model.UniPair
import com.nagl.memesschedule.databinding.FragmentScheduleBinding
import com.nagl.memesschedule.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScheduleFragment : BaseFragment() {

    // TODO: create schedule view (mb ViewPager or hardcoded)
    private lateinit var binding: FragmentScheduleBinding

    private val viewModel by viewModels<ScheduleViewModel> { viewModelFactoryProvider }
    private val schedulePairList = arrayListOf<UniPair>()
    //private val scheduleCollectionAdapter by lazy {  }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScheduleBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getUserGroup()
        val viewPager = binding.scheduleViewPager
        viewPager.adapter = ScheduleCollectionAdapter(schedulePairList, this)
        observeViewModel()
        initListeners()
    }

    private fun observeViewModel() {
        with(viewModel) {
            groupNumber.observe(viewLifecycleOwner) { groupNumber ->
                groupNumber.let {
                    if (it.isNotEmpty()) {
                        //binding.textHome.text = groupNumber
                        binding.group = groupNumber
                        viewModel.getUserScheduleByGroup(groupNumber)
                    }
                }
            }

            isLoading.observe(viewLifecycleOwner) { state ->
                when (state) {
                    true -> showLoading()
                    false -> hideLoading()
                }
            }

            schedule.observe(viewLifecycleOwner) {
                if (it != null) {
                    initViewPager(it)
                }
            }
        }
    }

    // TODO: перед отправкой данных с лист, проверить, не кончились ли учебные дни на этой неделе
    private fun initViewPager(schedule: Schedule) {
        schedulePairList.clear()
        schedulePairList.addAll(if (schedule.isCurrentWeekEven) schedule.evenWeek else schedule.oddWeek)
        binding.scheduleViewPager.adapter?.notifyDataSetChanged()
        
        binding.scheduleViewPager.visibility = View.VISIBLE
    }

    // TODO: mb do not use [binding.group] variable ¯\_(ツ)_/¯ (but how? XD)
    private fun initListeners() {
        binding.scheduleFragmentSwipeRefresh.setOnRefreshListener {
            showLoading()
            if (binding.group.isNullOrEmpty()) {
                viewModel.getUserGroup()
            } else {
                viewModel.refreshSchedule(binding.group!!)
            }
            binding.scheduleFragmentSwipeRefresh.isRefreshing = false
        }
    }

    private fun showLoading() {
        binding.apply {
            progressBar.visibility = View.VISIBLE
            scheduleViewPager.visibility = View.GONE
        }
    }

    private fun hideLoading() {
        binding.apply {
            progressBar.visibility = View.GONE
        }
    }
}