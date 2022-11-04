package com.example.androidtv.kotlinlogic.scope

/**
 * Created by Dipak Kumar Mehta on 10/19/2022.
 */
class ScopeFunction {

}


fun main(args: Array<String>) {
    var user = User("Joe","London")
    val updatedUsername = user.let {
        it.name = "John"
        it.name
    }
    println("Updated Username : $updatedUsername")
}

data class User(var name: String, var address: String)