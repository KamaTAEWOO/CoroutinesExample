package com.example.coroutinesexample

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {
    private val TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        Thread.sleep(2000L)

        helloWorld()
        coroutineScopeExample()
        suspendFunction()
        addSuspendFunction()
    }

    // Hello World
    @OptIn(DelicateCoroutinesApi::class)
    private fun helloWorld() {
        // 1번
//        GlobalScope.launch {
//            delay(1000L)
//            Log.d(TAG, "World!")
//        }
//        Log.d(TAG,"Hello,")
//        Thread.sleep(2000L)

        // 2번
//        GlobalScope.launch {
//            delay(1000L)
//            Log.d(TAG, "World!")
//        }
//        Log.d(TAG,"Hello,")
//        runBlocking {
//            // 쓰레드가 멈춰버림. -> 사용 x
//            delay(2000L)
//        }

        // 3번
//        runBlocking {
//            GlobalScope.launch {
//                delay(1000L)
//                Log.d(TAG, "World!")
//            }
//            Log.d(TAG,"Hello,")
//            delay(2000L)
//        }

        // 4번
//        runBlocking {
//            val job = GlobalScope.launch {
//                delay(1000L)
//                Log.d(TAG, "World!")
//            }
//            Log.d(TAG,"Hello,")
//            job.join()
//        }
    }

    // Coroutine Scope
    private fun coroutineScopeExample() {
//        runBlocking {
//            launch {
//                delay(200L)
//                Log.d(TAG, "Task from runBlocking")
//            }
//
//            runBlocking {
//                // runBlocking과 다르게 자식 코루틴이 끝나면 부모 코루틴도 끝남.
//                launch {
//                    delay(500L)
//                    Log.d(TAG, "Task from nested launch")
//                }
//
//                delay(100L)
//                Log.d(TAG, "Task from coroutine scope")
//            }
//
//            Log.d(TAG, "Coroutine scope is over")
//        }
    }

    // suspend function
    private fun suspendFunction() {
//        runBlocking { // this: CoroutineScope
//            Log.d(TAG, "Hello,")
//            launch {
//                doWorld()
//            }
//        }
    }
//
//    private suspend fun doWorld() {
//        Log.d(TAG, "World!")
//        delay(1000L)
//    }

    // add
    private fun addSuspendFunction() {
        runBlocking {
            val job = GlobalScope.launch {
                Log.d(TAG, "Start")
                val result = add(4, 5)
                Log.d(TAG, "Result: $result")
            }
            job.join()
        }
    }

    // 1번
    private suspend fun add(data1: Int, data2: Int): Int {
        delay(1000L)
        return 4 + 5
    }

    // 2번
    private fun add1(data1: Int, data2: Int): Int {
        return 4 + 5
    }
}