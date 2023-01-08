package com.chami.flowapisampleapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StateFlowViewModel(startingNumber: Int) : ViewModel() {

    private var _total: MutableLiveData<Int> = MutableLiveData<Int>()
    val total: LiveData<Int> get() = _total

    init {
        _total.value = startingNumber
    }

    fun updateValue(input: Int) {
        _total.value = _total.value?.plus(input)
    }

}