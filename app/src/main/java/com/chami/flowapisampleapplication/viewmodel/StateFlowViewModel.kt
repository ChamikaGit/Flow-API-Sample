package com.chami.flowapisampleapplication.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class StateFlowViewModel(startingNumber: Int) : ViewModel() {

//    private var _total: MutableLiveData<Int> = MutableLiveData<Int>()
//    val total: LiveData<Int> get() = _total

    private var _flowTotal: MutableStateFlow<Int> = MutableStateFlow(0)
    val flowTotal: StateFlow<Int> get() = _flowTotal

    init {
//        _total.value = startingNumber
        _flowTotal.value = startingNumber
    }

    //this act like producer for the stateFlow
    fun updateValue(input: Int) {
//        _total.value = _total.value?.plus(input)
        _flowTotal.value = _flowTotal.value.plus(input)
    }

}