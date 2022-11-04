package com.example.androidtv.kotlinlogic

/**
 * Created by Dipak Kumar Mehta on 11/19/2021.
 */
class LazyTest {

    private val heavyClass:HeavyClass by lazy {
        println("Heavy Object initialised")
        HeavyClass()
    }

     fun accessObject() {
        println(heavyClass)
    }
}

fun main (args:Array<String>) {
    val lazyTest = LazyTest()
    println("lazy class initialised")
    lazyTest.accessObject()
    lazyTest.accessObject()
}