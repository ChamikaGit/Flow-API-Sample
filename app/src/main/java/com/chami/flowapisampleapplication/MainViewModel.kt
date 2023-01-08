package com.chami.flowapisampleapplication

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    val myFlow = flow<Int> {
        for (i in 1..100) {
            emit(i)
            delay(1000L)
        }
    }

    init {
        mapOperatorDemo()
    }


    private fun backPressureDemo() {
        //Normal-Way
        val myFlow1 = flow<Int> {

            for (i in 1..10) {
                Log.e("MYTAG", "producer: $i")
                emit(i)
                delay(1000L)
            }
        }
        viewModelScope.launch {
            myFlow1.collect {
                Log.e("MYTAG", "consumed: $it")
            }
        }
    }

    private fun backPressureDemo2() {
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

        val myFlow1 = flow<Int> {

            for (i in 1..10) {
                Log.e("MYTAG", "producer: $i")
                emit(i)
                delay(1000L)
            }

        }

        viewModelScope.launch {
            myFlow1.collect {
                Log.e("MYTAG", "consumed: $it")
                delay(2000L)
            }
        }
    }

    private fun backPressureDemo3() {
        //But there can be some situations where we need to get the flow without
        //waiting for the producer.

        //Think about the situation where we are getting live data from some online server.
        //to avoid producer waiting for consumer,we can use buffer operator

        //using Buffer operator run on separate coroutine block so this producer and consumer work can be run
        //on parallel(concurrently).

/*        2023-01-08 16:47:30.309 30883-30883/com.chami.flowapisampleapplication E/MYTAG: producer: 1
        2023-01-08 16:47:31.312 30883-30883/com.chami.flowapisampleapplication E/MYTAG: producer: 2
        2023-01-08 16:47:32.312 30883-30883/com.chami.flowapisampleapplication E/MYTAG: consumed: 1
        2023-01-08 16:47:32.313 30883-30883/com.chami.flowapisampleapplication E/MYTAG: producer: 3
        2023-01-08 16:47:33.315 30883-30883/com.chami.flowapisampleapplication E/MYTAG: producer: 4
        2023-01-08 16:47:34.315 30883-30883/com.chami.flowapisampleapplication E/MYTAG: consumed: 2
        2023-01-08 16:47:34.316 30883-30883/com.chami.flowapisampleapplication E/MYTAG: producer: 5
        2023-01-08 16:47:35.317 30883-30883/com.chami.flowapisampleapplication E/MYTAG: producer: 6
        2023-01-08 16:47:36.316 30883-30883/com.chami.flowapisampleapplication E/MYTAG: consumed: 3
        2023-01-08 16:47:36.318 30883-30883/com.chami.flowapisampleapplication E/MYTAG: producer: 7
        2023-01-08 16:47:37.321 30883-30883/com.chami.flowapisampleapplication E/MYTAG: producer: 8
        2023-01-08 16:47:38.318 30883-30883/com.chami.flowapisampleapplication E/MYTAG: consumed: 4
        2023-01-08 16:47:38.322 30883-30883/com.chami.flowapisampleapplication E/MYTAG: producer: 9
        2023-01-08 16:47:39.323 30883-30883/com.chami.flowapisampleapplication E/MYTAG: producer: 10
        2023-01-08 16:47:40.321 30883-30883/com.chami.flowapisampleapplication E/MYTAG: consumed: 5
        2023-01-08 16:47:42.324 30883-30883/com.chami.flowapisampleapplication E/MYTAG: consumed: 6
        2023-01-08 16:47:44.326 30883-30883/com.chami.flowapisampleapplication E/MYTAG: consumed: 7
        2023-01-08 16:47:46.327 30883-30883/com.chami.flowapisampleapplication E/MYTAG: consumed: 8
        2023-01-08 16:47:48.328 30883-30883/com.chami.flowapisampleapplication E/MYTAG: consumed: 9
        2023-01-08 16:47:50.330 30883-30883/com.chami.flowapisampleapplication E/MYTAG: consumed: 10*/

        val myFlow1 = flow<Int> {

            for (i in 1..10) {
                Log.e("MYTAG", "producer: $i")
                emit(i)
                delay(1000L)
            }
        }
        viewModelScope.launch {
            myFlow1.buffer().collect {
                delay(2000L)
                Log.e("MYTAG", "consumed: $it")
            }
        }
    }


    private fun backPressureDemo4() {
        //if want get the latest value of producer produced
        //we can use collectLatest{} operator
        //to display current score of a game.

/*        2023-01-08 16:54:19.770 31004-31004/com.chami.flowapisampleapplication E/MYTAG: producer: 1
        2023-01-08 16:54:20.775 31004-31004/com.chami.flowapisampleapplication E/MYTAG: producer: 2
        2023-01-08 16:54:21.779 31004-31004/com.chami.flowapisampleapplication E/MYTAG: producer: 3
        2023-01-08 16:54:22.783 31004-31004/com.chami.flowapisampleapplication E/MYTAG: producer: 4
        2023-01-08 16:54:23.789 31004-31004/com.chami.flowapisampleapplication E/MYTAG: producer: 5
        2023-01-08 16:54:24.793 31004-31004/com.chami.flowapisampleapplication E/MYTAG: producer: 6
        2023-01-08 16:54:25.798 31004-31004/com.chami.flowapisampleapplication E/MYTAG: producer: 7
        2023-01-08 16:54:26.803 31004-31004/com.chami.flowapisampleapplication E/MYTAG: producer: 8
        2023-01-08 16:54:27.808 31004-31004/com.chami.flowapisampleapplication E/MYTAG: producer: 9
        2023-01-08 16:54:28.810 31004-31004/com.chami.flowapisampleapplication E/MYTAG: producer: 10
        2023-01-08 16:54:30.815 31004-31004/com.chami.flowapisampleapplication E/MYTAG: consumed: 10*/

        val myFlow1 = flow<Int> {

            for (i in 1..10) {
                Log.e("MYTAG", "producer: $i")
                emit(i)
                delay(1000L)
            }
        }
        viewModelScope.launch {
            myFlow1.collectLatest {
                delay(2000L)
                Log.e("MYTAG", "consumed: $it")
            }
        }
    }


    private fun mapOperatorDemo() {
        //we can change the flow of values using map operator

/*        2023-01-08 21:24:00.498 5102-5102/com.chami.flowapisampleapplication E/MYTAG: producer: 1
        2023-01-08 21:24:00.499 5102-5102/com.chami.flowapisampleapplication E/MYTAG: consumed: Hello 1
        2023-01-08 21:24:01.501 5102-5102/com.chami.flowapisampleapplication E/MYTAG: producer: 2
        2023-01-08 21:24:01.501 5102-5102/com.chami.flowapisampleapplication E/MYTAG: consumed: Hello 2
        2023-01-08 21:24:02.503 5102-5102/com.chami.flowapisampleapplication E/MYTAG: producer: 3
        2023-01-08 21:24:02.503 5102-5102/com.chami.flowapisampleapplication E/MYTAG: consumed: Hello 3
        2023-01-08 21:24:03.505 5102-5102/com.chami.flowapisampleapplication E/MYTAG: producer: 4
        2023-01-08 21:24:03.506 5102-5102/com.chami.flowapisampleapplication E/MYTAG: consumed: Hello 4
        2023-01-08 21:24:04.509 5102-5102/com.chami.flowapisampleapplication E/MYTAG: producer: 5
        2023-01-08 21:24:04.509 5102-5102/com.chami.flowapisampleapplication E/MYTAG: consumed: Hello 5
        2023-01-08 21:24:05.511 5102-5102/com.chami.flowapisampleapplication E/MYTAG: producer: 6
        2023-01-08 21:24:05.511 5102-5102/com.chami.flowapisampleapplication E/MYTAG: consumed: Hello 6
        2023-01-08 21:24:06.513 5102-5102/com.chami.flowapisampleapplication E/MYTAG: producer: 7
        2023-01-08 21:24:06.513 5102-5102/com.chami.flowapisampleapplication E/MYTAG: consumed: Hello 7
        2023-01-08 21:24:07.516 5102-5102/com.chami.flowapisampleapplication E/MYTAG: producer: 8
        2023-01-08 21:24:07.516 5102-5102/com.chami.flowapisampleapplication E/MYTAG: consumed: Hello 8
        2023-01-08 21:24:08.518 5102-5102/com.chami.flowapisampleapplication E/MYTAG: producer: 9
        2023-01-08 21:24:08.518 5102-5102/com.chami.flowapisampleapplication E/MYTAG: consumed: Hello 9
        2023-01-08 21:24:09.520 5102-5102/com.chami.flowapisampleapplication E/MYTAG: producer: 10
        2023-01-08 21:24:09.520 5102-5102/com.chami.flowapisampleapplication E/MYTAG: consumed: Hello 10*/

        val myFlow1 = flow<Int> {

            for (i in 1..10) {
                Log.e("MYTAG", "producer: $i")
                emit(i)
                delay(1000L)
            }
        }
        viewModelScope.launch {
            myFlow1.map {
                addMessage(it)
            }.collect {
                Log.e("MYTAG", "consumed: $it")
            }
        }
    }

    fun addMessage(count : Int): String{
        return "Hello $count"
    }

}