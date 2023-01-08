package com.chami.flowapisampleapplication

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class StateFlowViewModelFactory(private val startingTotal : Int,private val app : Application) : ViewModelProvider.AndroidViewModelFactory(app) {


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(StateFlowViewModel::class.java)) {
            StateFlowViewModel(startingTotal) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }

    }
}