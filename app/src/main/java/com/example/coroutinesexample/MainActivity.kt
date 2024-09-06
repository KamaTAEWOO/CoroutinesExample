package com.example.coroutinesexample

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull

class MainActivity : AppCompatActivity() {
    private val TAG = "TestExample::"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

//        helloWorld()
//        coroutineScopeExample()
//        suspendFunction()
//        addSuspendFunction()
//        cancellation()
//        timeoutCancellation()
//        channerExample()
//        producerConsumer()
        panoutExample()
    }

    private fun panoutExample() {
        runBlocking {
            val producer: ReceiveChannel<Int> = produceNumbers()
            repeat(5) { launchProcessor(it, producer) }
            delay(950L)
            producer.cancel()
        }
    }

    fun CoroutineScope.produceNumbers() = produce<Int> {
        var x = 1
        while (true) {
            send(x++)
            delay(100L)
        }
    }

    fun CoroutineScope.launchProcessor(id: Int, channel: ReceiveChannel<Int>) = launch {
        for (msg in channel) {
            Log.d(TAG, "Processor #$id received $msg")
        }
    }

//    private fun producerConsumer() {
//        runBlocking {
//            val numbers = produceNumbers(5)
//            val double = produceDouble(numbers)
//            double.consumeEach {
//                Log.d(TAG, it.toString())
//            }
//            Log.d(TAG, "Done!")
//
////            val squares = produceSquare(5)
////            squares.consumeEach {
////                Log.d(TAG, it.toString())
////            }
////            Log.d(TAG, "Done!")
//        }
//    }

    fun CoroutineScope.produceNumbers(max: Int): ReceiveChannel<Int> = produce {
        for (x in 1..max) {
            send(x)
        }
    }

    fun CoroutineScope.produceDouble(numbers: ReceiveChannel<Int>): ReceiveChannel<Int> = produce {
        numbers.consumeEach { send(it * 2) }
    }

    private fun CoroutineScope.produceSquare(max: Int) = produce {
        for (x in 1..max) {
            send(x * x)
        }
    }

    private fun channerExample() {
        val channel = Channel<Int>()

//        runBlocking {
//            launch {
//                for (x in 1..5) {
//                    channel.send(x * x)
//                }
//            }
//
//            repeat(5) {
//                Log.d(TAG, "1 - " + channel.receive().toString())
//            }
//            Log.d(TAG, "Done!")
//
//            channelReceive(channel)
//        }

        CoroutineScope(Dispatchers.Main).launch {
            launch {
                for (x in 1..5) {
                    channel.send(x * x)
                }
            }

            repeat(5) {
                Log.d(TAG, "1 - " + channel.receive().toString())
            }
            Log.d(TAG, "Done!")
        }

        CoroutineScope(Dispatchers.Main).launch {
            channelReceive(channel)
        }
    }

    private fun channelReceive(channel: Channel<Int>) {
        runBlocking {
            repeat(5) {
                Log.d(TAG, "2 - " +  channel.receive().toString())
            }
            Log.d(TAG, "Done!")
        }

    }

    private fun timeoutCancellation() {
        // 1번
        CoroutineScope(Dispatchers.Main).launch {
            val result = withTimeoutOrNull(1300L) {
                repeat(1000) { i ->
                    Log.d(TAG, "I'm sleeping $i ...")
                    delay(500L)
                }
                "Done"
            }
            Log.d(TAG, "Result is $result")
        }

        // 2번
        runBlocking {
            val result = withTimeoutOrNull(1300L) {
                repeat(1000) { i ->
                    Log.d(TAG, "I'm sleeping $i ...")
                    delay(500L)
                }
                "Done"
            }
            Log.d(TAG, "Result is $result")
        }
    }

    private fun cancellation() {
//        runBlocking {
//            val job = launch {
//                try {
//                    repeat(1000) { i ->
//                        Log.d(TAG, "I'm sleeping $i ...")
//                        delay(500L)
//                    }
//                } finally {
//                    Log.d(TAG, "main : I'm running finally!")
//                }
//            }
//
//            delay(1300L)
//            Log.d(TAG, "main : I'm tired of waiting!")
//            job.cancelAndJoin()
//            Log.d(TAG, "main : Now I can quit.")
//        }
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
//        runBlocking {
//            val job = GlobalScope.launch {
//                Log.d(TAG, "Start")
//                val result = add(4, 5)
//                Log.d(TAG, "Result: $result")
//            }
//            job.join()
//        }

        runBlocking {
//            GlobalScope.launch {
//                Log.d(TAG, "Start1")
//                val result = add1(4, 5)
//                Log.d(TAG, "Result1: $result")
//            }
//
//            // Dispatchers는 선점인지 비선점인지 상관없음.
//            CoroutineScope(Dispatchers.Main).launch {
//                Log.d(TAG, "Start2")
//                val result = add1(10, 5)
//                Log.d(TAG, "Result2: $result")
//            }

//            launch {
//                test1()
//            }
//            launch {
//                test2()
//            }
        }
    }

    // 1번
    private suspend fun add(data1: Int, data2: Int): Int {
        Log.d(TAG, "add delay before")
        delay(1000L)
        Log.d(TAG, "add delay after")
        // yield() // 선점형 스케줄러로 만들어줌
                // 즉 다른 코루틴에게 양보함.
        return 4 + 5
    }

    // 2번
    private fun add1(data1: Int, data2: Int): Int {
        return 4 + 5
    }

    // 2초마다 for문으로 1씩 증가
    private suspend fun test1() {
        for (i in 1..10) {
            Log.d(TAG, "i: $i")
            delay(2000L)
        }
    }

    private suspend fun test2() {
        Log.d(TAG, "add delay before")
        delay(20000L)
        Log.d(TAG, "add delay after")
    }
}