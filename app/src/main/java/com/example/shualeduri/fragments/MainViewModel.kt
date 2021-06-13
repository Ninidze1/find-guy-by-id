package com.example.shualeduri.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shualeduri.api.RetrofitInstance
import com.example.shualeduri.model.IpModel
import com.example.shualeduri.model.ResultHandle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _IpInfo = MutableLiveData<ResultHandle<IpModel>>()
    val IpInfo: LiveData<ResultHandle<IpModel>> = _IpInfo

    fun init(ipInput: String) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                val result = RetrofitInstance.api.getPost(ipInput)
                if (result.isSuccessful) {
                    val info = result.body()
                    _IpInfo.postValue(ResultHandle.success(info))
                } else {
                    _IpInfo.postValue(ResultHandle.error(result.message()))
                }
            }
        }
    }

}