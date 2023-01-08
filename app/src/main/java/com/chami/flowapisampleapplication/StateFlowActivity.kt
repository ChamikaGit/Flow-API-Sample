package com.chami.flowapisampleapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.chami.flowapisampleapplication.viewmodel.StateFlowViewModel
import com.chami.flowapisampleapplication.viewmodel.StateFlowViewModelFactory

class StateFlowActivity : AppCompatActivity() {

    lateinit var etNumber: EditText
    lateinit var tvNumber: TextView
    lateinit var btnUpdate: Button

    lateinit var stateFlowViewModel: StateFlowViewModel
    lateinit var stateFlowViewModelFactory: StateFlowViewModelFactory


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_state_flow)

        etNumber = findViewById(R.id.etNumber)
        tvNumber = findViewById(R.id.tvNumber)
        btnUpdate = findViewById(R.id.btnUpdate)

        stateFlowViewModelFactory = StateFlowViewModelFactory(125, application)
        stateFlowViewModel =
            ViewModelProvider(this, stateFlowViewModelFactory)[StateFlowViewModel::class.java]

//        stateFlowViewModel.total.observe(this) {
//            tvNumber.text = it.toString()
//        }

        //flowTotal.collect is act the consumer here
        //
        lifecycleScope.launchWhenCreated {
            stateFlowViewModel.flowTotal.collect {
                tvNumber.text = it.toString()
            }
        }

        //only used to get update for one time like event
        lifecycleScope.launchWhenCreated {
            stateFlowViewModel.message.collect {
                Toast.makeText(this@StateFlowActivity, it, Toast.LENGTH_SHORT).show()
            }
        }

        btnUpdate.setOnClickListener {
            val count = etNumber.text.toString()
            stateFlowViewModel.updateValue(count.toInt())
        }

    }
}