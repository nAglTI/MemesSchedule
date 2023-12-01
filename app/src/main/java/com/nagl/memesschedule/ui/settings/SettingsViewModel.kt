package com.nagl.memesschedule.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nagl.memesschedule.data.source.repository.DataRepository
import com.nagl.memesschedule.data.source.repository.DataStoreRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    private val dataRepository: DataRepository
) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Settings"
    }
    val text: LiveData<String> = _text

    private val _isLogOut = MutableLiveData<Boolean>()
    val isLogOut: LiveData<Boolean> = _isLogOut

    fun logOut() {
        viewModelScope.launch {
            dataStoreRepository.clearData()
            dataRepository.deleteSchedule()
            _isLogOut.value = true
        }
    }
}