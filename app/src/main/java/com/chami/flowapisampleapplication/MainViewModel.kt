package com.chami.flowapisampleapplication

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    val myFlow = flow<Int> {
        for (i in 1..100) {
            emit(i)
            delay(1000L)
        }
    }

    init {
        backPressureDemo2()
    }


    private fun backPressureDemo(){
        //Normal-Way
        val myFlow1 = flow<Int>{

            for (i in 1..10){
                Log.e("MYTAG", "producer: $i")
                emit(i)
                delay(1000L)
            }
        }
        viewModelScope.launch{
            myFlow1.collect{
                Log.e("MYTAG", "consumed: $it")
            }
        }
    }

    private fun backPressureDemo2(){
        //By default kotlin flow handle the backpressure
        //This is normal example for consumer producer sample

/*        2023-01-08 16:36:28.582 28842-28842/com.chami.flowapisampleapplication E/MYTAG: producer: 1
        2023-01-08 16:36:28.583 28842-28842/com.chami.flowapisampleapplication E/MYTAG: consumed: 1
        2023-01-08 16:36:31.587 28842-28842/com.chami.flowapisampleapplication E/MYTAG: producer: 2
        2023-01-08 16:36:31.587 28842-28842/com.chami.flowapisampleapplication E/MYTAG: consumed: 2
        2023-01-08 16:36:34.589 28842-28842/com.chami.flowapisampleapplication E/MYTAG: producer: 3
        2023-01-08 16:36:34.589 28842-28842/com.chami.flowapisampleapplication E/MYTAG: consumed: 3
        2023-01-08 16:36:37.594 28842-28842/com.chami.flowapisampleapplication E/MYTAG: producer: 4
        2023-01-08 16:36:37.594 28842-28842/com.chami.flowapisampleapplication E/MYTAG: consumed: 4
        2023-01-08 16:36:40.599 28842-28842/com.chami.flowapisampleapplication E/MYTAG: producer: 5
        2023-01-08 16:36:40.600 28842-28842/com.chami.flowapisampleapplication E/MYTAG: consumed: 5
        2023-01-08 16:36:43.603 28842-28842/com.chami.flowapisampleapplication E/MYTAG: producer: 6
        2023-01-08 16:36:43.604 28842-28842/com.chami.flowapisampleapplication E/MYTAG: consumed: 6
        2023-01-08 16:36:46.607 28842-28842/com.chami.flowapisampleapplication E/MYTAG: producer: 7
        2023-01-08 16:36:46.608 28842-28842/com.chami.flowapisampleapplication E/MYTAG: consumed: 7
        2023-01-08 16:36:49.611 28842-28842/com.chami.flowapisampleapplication E/MYTAG: producer: 8
        2023-01-08 16:36:49.612 28842-28842/com.chami.flowapisampleapplication E/MYTAG: consumed: 8
        2023-01-08 16:36:52.616 28842-28842/com.chami.flowapisampleapplication E/MYTAG: producer: 9
        2023-01-08 16:36:52.616 28842-28842/com.chami.flowapisampleapplication E/MYTAG: consumed: 9
        2023-01-08 16:36:55.619 28842-28842/com.chami.flowapisampleapplication E/MYTAG: producer: 10
        2023-01-08 16:36:55.620 28842-28842/com.chami.flowapisampleapplication E/MYTAG: consumed: 10*/

        //flow producer wait until consumer successful consumed that value and after that producer try to producing that data again

        val myFlow1 = flow<Int>{

            for (i in 1..10){
                Log.e("MYTAG", "producer: $i")
                emit(i)
                delay(1000L)
            }

        }

        viewModelScope.launch{
            myFlow1.collect{
                Log.e("MYTAG", "consumed: $it")
                delay(2000L)
            }
        }
    }

}