package com.nagl.memesschedule.ui.schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.nagl.memesschedule.data.model.Schedule
import com.nagl.memesschedule.data.model.UniPair
import com.nagl.memesschedule.databinding.FragmentScheduleBinding
import com.nagl.memesschedule.ui.BaseFragment
import com.nagl.memesschedule.utils.isNextWeek
import com.nagl.memesschedule.utils.isOddWeek
import dagger.hilt.android.AndroidEntryPoint
import org.joda.time.LocalDate

@AndroidEntryPoint
class ScheduleFragment : BaseFragment() {

    private var _binding: FragmentScheduleBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<ScheduleViewModel> { viewModelFactoryProvider }
    private val schedulePairList = arrayListOf<UniPair>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScheduleBinding.inflate(inflater)
        observeViewModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getUserGroup()
        initListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeViewModel() {
        with(viewModel) {
            groupNumber.observe(viewLifecycleOwner) { groupNumber ->
                groupNumber.let {
                    if (it.isNotEmpty()) {
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

    private fun initViewPager(schedule: Schedule) {
        schedulePairList.clear()
        schedulePairList.addAll(if (isOddWeek(schedule)) schedule.oddWeek else schedule.evenWeek)
        val viewPager = binding.scheduleViewPager
        viewPager.adapter = ScheduleCollectionAdapter(schedulePairList, this)
        viewPager.offscreenPageLimit = 6
        viewPager.adapter?.notifyDataSetChanged()
        viewPager.setCurrentItem(
            if (isNextWeek) 0 else LocalDate.now().dayOfWeek - 1,
            false
        )
        viewPager.visibility = View.VISIBLE
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
            scheduleFragmentSwipeRefresh.isRefreshing = false
        }
    }
}