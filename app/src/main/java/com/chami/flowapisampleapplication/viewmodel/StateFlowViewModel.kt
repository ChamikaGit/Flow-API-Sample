package com.chami.flowapisampleapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class StateFlowViewModel(startingNumber: Int) : ViewModel() {

//    private var _total: MutableLiveData<Int> = MutableLiveData<Int>()
//    val total: LiveData<Int> get() = _total

    private var _flowTotal: MutableStateFlow<Int> = MutableStateFlow(0)
    val flowTotal: StateFlow<Int> get() = _flowTotal

    private var _message: MutableSharedFlow<String> = MutableSharedFlow()
    val message: SharedFlow<String> get() = _message

    init {
//        _total.value = startingNumber
        _flowTotal.value = startingNumber
    }

    //this act like producer for the stateFlow
    fun updateValue(input: Int) {
//        _total.value = _total.value?.plus(input)
        _flowTotal.value = _flowTotal.value.plus(input)

        viewModelScope.launch {
            _message.emit("Value updated successful")
        }
    }

}