package com.xyz.app.ui.currency_list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xyz.app.data.ApplicationException.SortBeforeFetchException
import com.xyz.app.data.db.CurrencyInfoFactory
import com.xyz.app.data.repository.CurrencyInfoRepository
import com.xyz.app.data.vo.Resource
import com.xyz.app.domain.CurrencyInfo
import com.xyz.app.infrastructure.SimpleDi
import kotlinx.coroutines.*

private const val TAG = "CurrencyListViewModel"

class CurrencyListViewModel : ViewModel() {
    private val currencyInfoRepository: CurrencyInfoRepository by SimpleDi
    private lateinit var currencyList: List<CurrencyInfo>
    private var job: Job? = null

    val currencyInfos: LiveData<Resource<List<CurrencyInfo>>>
        get() = _currencyInfos

    val selectedCurrencyInfo: LiveData<CurrencyInfo> get() = _selectedCurrencyInfo

    private val _currencyInfos = MutableLiveData<Resource<List<CurrencyInfo>>>()
    fun fetchCurrencyInfo() {
        _currencyInfos.value = Resource.loading(null)
        viewModelScope.launch {
            if (shouldLoadTestData()) {
                currencyInfoRepository.update(CurrencyInfoFactory.list())
            }
            currencyList = currencyInfoRepository.list().also {
                _currencyInfos.value = Resource.success(it)
            }
        }
    }

    private val _selectedCurrencyInfo = MutableLiveData<CurrencyInfo>()
    fun selectCurrencyInfo(currencyInfo: CurrencyInfo) {
        _selectedCurrencyInfo.value = currencyInfo
    }

    private suspend fun shouldLoadTestData() = withContext(Dispatchers.IO) {
        currencyInfoRepository.list().isEmpty()
    }

    fun sortCurrencyInfo() {
        if (!::currencyList.isInitialized) {
            _currencyInfos.value = Resource.error(
                msg = SortBeforeFetchException.reason,
                data = null,
                exception = SortBeforeFetchException
            )
            return
        }

        if (job?.isCompleted == false) {
            Log.d(TAG, "Do nothing cause it is sorting")
            return
        }

        _currencyInfos.value = Resource.loading(null)

        job = viewModelScope.launch(Dispatchers.Main) {
            val sorted = sortCurrencyList(currencyList)
            _currencyInfos.value = Resource.success(sorted)
        }
    }

    private suspend fun sortCurrencyList(origin: List<CurrencyInfo>): List<CurrencyInfo> =
        withContext(Dispatchers.IO) {
            // Make a delay to simulate the io operation because we usually sort large data by the backend api
            delay(1000)

            // Make a copy in order not to break the order of the original list on other thread
            val copy = origin.toMutableList()
            copy.sortedBy {
                it.name
            }
        }

    override fun onCleared() {
        job?.cancel()
        super.onCleared()
    }
}