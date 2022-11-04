package com.example.androidtv.kotlinlogic

/**
 * Created by Dipak Kumar Mehta on 11/19/2021.
 */
class InlineFunction {

    fun doSomething() {
        print("doSomething start")
        doSomethingElse()
        print("doSomething end")
    }

    inline fun doSomethingElse() {
        print("doSomethingElse")
    }
}