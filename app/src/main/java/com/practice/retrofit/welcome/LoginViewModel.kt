package com.practice.retrofit.welcome

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.practice.retrofit.model.Account
import com.practice.retrofit.model.DefaultResponse
import com.practice.retrofit.network.ApiFactory
import com.practice.retrofit.network.RequestState
import com.practice.retrofit.network.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val _state = MutableLiveData<RequestState>()
    val state: LiveData<RequestState>
        get() = _state

    private val _response = MutableLiveData<DefaultResponse<Account>>()
    val response: LiveData<DefaultResponse<Account>>
        get() = _response

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    private var job = Job()
    private val uiScope = CoroutineScope(job+ Dispatchers.Main)

    fun doLogin(email: String? = null, password: String? = null) {
        _state.postValue(RequestState.REQUEST_START)
        uiScope.launch {
            try {
                when(val response = ApiFactory.login(email, password)) {
                    is Result.Success -> {
                        _state.postValue(RequestState.REQUEST_END)
                        _response.postValue(response.data)
                    }
                    is Result.Error -> {
                        _state.postValue(RequestState.REQUEST_ERROR)
                        _response.postValue(Gson().fromJson(response.exception, DefaultResponse::class.java) as DefaultResponse<Account>)
                    }
                }
            } catch (t: Throwable) {
                _state.postValue(RequestState.REQUEST_ERROR)
                _error.postValue(t.localizedMessage)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}