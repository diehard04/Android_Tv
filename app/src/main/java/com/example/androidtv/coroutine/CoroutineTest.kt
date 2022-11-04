package com.example.androidtv.coroutine

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*

/**
 * Created by Dipak Kumar Mehta on 11/24/2021.
 */
class CoroutineTest :AppCompatActivity() {
    // Kotlin Program For better understanding of launch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GFG()
    }
    fun GFG() {
        var resultOne = "Android"
        var resultTwo = "Kotlin"
        println("launch " + "Before")

        GlobalScope.launch (Dispatchers.IO) {
            resultOne = function1()

        }

        GlobalScope.launch(Dispatchers.IO) {
            resultTwo = function2()
        }
        println("Launch "+ "After")
        val resultText = resultOne + resultTwo
        println("Launch$resultText")

    }

    suspend fun function1(): String {
        delay(1000L)
        val message = "function1"
        println("Launch$message")
        return message
    }

    suspend fun function2(): String {
        delay(100L)
        val message = "function2"
        println("Launch$message")
        return message
    }
}
fun main(array: Array<String>) {
    CoroutineTest()
}

