package com.example.flows

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.flows.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.btnStart.setOnClickListener {
           val job= CoroutineScope(Dispatchers.Main).launch {
                //here we collect data
                val data = produce()

               data.first()//it return first value
               data.toList()//it will store all value in list and then return the list

                data.onStart {
                    Toast.makeText(this@MainActivity, "started", Toast.LENGTH_SHORT).show()
                }.onCompletion {
                    Toast.makeText(this@MainActivity, "completed", Toast.LENGTH_SHORT).show()

                } .onEach {

                }
                    .collect {
                    binding.mainText.text = it.toString()
                }
            }

            //========this will cancel the flow======//
//            CoroutineScope(Dispatchers.Default).launch {
//               delay(3000)
//                job.cancel()
//            }
        }

    }

    //this function produce coroutines
    private fun produce() = flow {
        val list = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9,10,11,12,13,14,15,16)
        list.forEach {
            delay(1000)
            emit(it)
        }
    }
}