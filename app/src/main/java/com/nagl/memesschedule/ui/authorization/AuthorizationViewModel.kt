package com.nagl.memesschedule.ui.authorization

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nagl.memesschedule.data.model.net.UserRequest
import com.nagl.memesschedule.data.source.repository.DataStoreRepository
import com.nagl.memesschedule.data.source.repository.IDataRepository
import com.nagl.memesschedule.utils.Result
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthorizationViewModel @Inject constructor(
    private val dataRepository: IDataRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isAuthPassed = MutableLiveData<Boolean>()
    val isAuthPassed: LiveData<Boolean> = _isAuthPassed

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    private val _isAuth = MutableLiveData<Boolean>()
    val isAuth: LiveData<Boolean> = _isAuth

//    private val _userLogin = MutableLiveData<String>()
//    private val _userPassword = MutableLiveData<String>()

    fun isUserAuth() {
        _isLoading.value = true
        viewModelScope.launch {
            dataStoreRepository.isUserAuth.collect {
                _isAuth.postValue(it)
                _isLoading.postValue(false)
            }
        }
    }

    fun getUserInfo(requestData: UserRequest) {
        _isLoading.value = true
        _isError.value = false
        viewModelScope.launch {
            when (val result = dataRepository.getUserInfo(requestData)) {
                is Result.Success -> {
                    _isLoading.value = false
                    if (result.data != null) {
                        dataStoreRepository.apply {
                            saveUserGroup(result.data.description)
                            saveUserAuth(true)
                        }
                        _isAuthPassed.value = true
                    } else {
                        _isAuthPassed.value = false
                    }
                }
                is Result.Error -> {
                    _isLoading.value = false
                    _isAuthPassed.value = false
                    _isError.value = true
                }
                is Result.Loading ->
                    _isLoading.postValue(true)
            }
        }
    }
}