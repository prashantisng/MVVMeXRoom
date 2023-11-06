package com.outthinking.roommvvmcrudapp

open class Event<out T>(private val content:T) {
    var hasBeenHandled=false
    private set //allow external read but not write


    fun getContentIfNotHandled():T?{
        return if (hasBeenHandled){
            null
        }else {
            hasBeenHandled=true;
            content
        }
    }

    fun peekContent():T=content
}