package com.chami.flowapisampleapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.chami.flowapisampleapplication.ui.theme.FlowAPISampleApplicationTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MainComposeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val myFlow = flow<Int> {
            for (i in 1..100) {
                emit(i)
                delay(1000L)
            }
        }

        setContent {
            FlowAPISampleApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Greeting(myFlow)
                    }

                }
            }
        }
    }
}

@Composable
fun Greeting(myFlow: Flow<Int>) {
    //collectAsState function is the consumer here
    val currentValue = myFlow.collectAsState(initial = 0).value

    Text(text = "Hello your index is $currentValue", fontSize = 25.sp)
}

//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    FlowAPISampleApplicationTheme {
//        Greeting("Android", myFlow)
//    }
//}