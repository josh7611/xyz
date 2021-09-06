package com.xyz.app.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xyz.app.data.repository.CurrencyInfoRepository
import com.xyz.app.data.vo.Resource
import com.xyz.app.domain.CurrencyInfo
import com.xyz.app.infrastructure.SimpleDi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val TAG = "CurrencyListViewModel"

class CurrencyListViewModel : ViewModel() {
    private val currencyInfoRepository: CurrencyInfoRepository by SimpleDi

    val currencyInfos: LiveData<Resource<List<CurrencyInfo>>>
        get() = _currencyInfos

    private val _currencyInfos = MutableLiveData<Resource<List<CurrencyInfo>>>()
    fun fetchCurrencyInfo() {
        _currencyInfos.value = Resource.loading(null)
        viewModelScope.launch {
            if (shouldLoadTestData()) {
                currencyInfoRepository.update(CurrencyInfoFactory.list())
            }
            _currencyInfos.value = Resource.success(currencyInfoRepository.list())
        }
    }

    private suspend fun shouldLoadTestData() = withContext(Dispatchers.IO) {
        currencyInfoRepository.list().isEmpty()
    }
}