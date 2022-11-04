package com.example.androidtv.searchrv

/**
 * Created by Dipak Kumar Mehta on 10/29/2021.
 */
class ExampleItem(imageResource: Int, text1: String, text2: String) {

    private val imageResource: Int
    private val text1: String
    private val text2: String
    fun getImageResource(): Int {
        return imageResource
    }

    fun getText1(): String {
        return text1
    }

    fun getText2(): String {
        return text2
    }

    init {
        this.imageResource = imageResource
        this.text1 = text1
        this.text2 = text2
    }
}