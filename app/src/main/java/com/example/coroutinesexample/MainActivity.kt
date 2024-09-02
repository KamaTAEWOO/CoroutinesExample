package com.example.coroutinesexample

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        helloWorld()
    }

    // Hello World
    @OptIn(DelicateCoroutinesApi::class)
    private fun helloWorld() {
        GlobalScope.launch {
            delay(1000L)
            Log.d(TAG, "World!")
        }
        Log.d(TAG,"Hello,")
        Thread.sleep(2000L)
    }
}