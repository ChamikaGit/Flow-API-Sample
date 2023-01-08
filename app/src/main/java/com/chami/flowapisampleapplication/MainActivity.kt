package com.chami.flowapisampleapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //flow api act like producer - consumer pattern to to observe the data
        //flow api with traditional view system
        //this producer code part
        val myFlow  = flow<Int>{
            for (i in 1..100){
                emit(i)
                delay(1000L)
            }
        }

        val tvFlow : TextView= findViewById(R.id.tvFlow)
        //this is for consumer code part
        CoroutineScope(Dispatchers.Main).launch {
            myFlow.collect{
                tvFlow.text = "Collected text is : $it"
            }
        }



    }
}