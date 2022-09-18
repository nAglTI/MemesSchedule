package com.nagl.memesschedule.ui.home

import androidx.lifecycle.*
import com.nagl.memesschedule.data.model.Schedule
import com.nagl.memesschedule.data.source.repository.DataStoreRepository
import com.nagl.memesschedule.data.source.repository.IDataRepository
import com.nagl.memesschedule.utils.Result
import kotlinx.coroutines.launch
import javax.inject.Inject

class ScheduleViewModel @Inject constructor(
    private val dataRepository: IDataRepository,
    private val dataStoreRepository: DataStoreRepository,
) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    private val _groupNumber = MutableLiveData<String>().apply {
        value = ""
    }
    val groupNumber: LiveData<String> = _groupNumber

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

//    private val _isGetGroup = MutableLiveData<Boolean>()
//    val isGetGroup: LiveData<Boolean> = _isGetGroup

    private val _schedule = MutableLiveData<Schedule?>()
    val schedule: LiveData<Schedule?> = _schedule

    private val _scheduleState = MutableLiveData<Boolean>()
    val scheduleState: LiveData<Boolean> = _scheduleState

    fun getUserGroup() {
        viewModelScope.launch {
            dataStoreRepository.group.collect {
                _groupNumber.postValue(it)
            }
        }
    }

    // TODO: разобраться с апи уника насчет четных и нечетных недель,
    //  а также добавить проверку если на неделе кончились пары, чтобы подгрузить расписание на след. неделю
    fun getUserScheduleByGroup(group: String) {
        _isLoading.value = true
        viewModelScope.launch {
            when (val result = dataRepository.getUserScheduleByGroup(group, false)) {
                is Result.Success -> {
                    _isLoading.value = false
                    if (result.data != null) {
                        _schedule.value = result.data
                        _scheduleState.value = true
                    } else {
                        refreshSchedule(group)
                    }
                }
                is Result.Error -> {
                    _isLoading.value = false
                    _scheduleState.value = false
                }
                is Result.Loading ->
                    _isLoading.postValue(true)
            }
        }
    }

    fun refreshSchedule(group: String) {
        _isLoading.value = true
        viewModelScope.launch {
            when (val result = dataRepository.getUserScheduleByGroup(group, true)) {
                is Result.Success -> {
                    _isLoading.value = false
                    if (result.data != null) {
                        _schedule.value = result.data
                        dataRepository.deleteSchedule()
                        dataRepository.saveSchedule(result.data)
                    } else {
                        _schedule.postValue(null)
                        _scheduleState.postValue(false)
                    }
                }
                is Result.Error -> {
                    _isLoading.value = false
                    _scheduleState.value = false
                }
                is Result.Loading ->
                    _isLoading.postValue(true)
            }
        }
    }

}