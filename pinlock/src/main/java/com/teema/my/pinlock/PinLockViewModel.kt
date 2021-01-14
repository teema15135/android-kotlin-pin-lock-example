package com.teema.my.pinlock

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class PinLockViewModel : ViewModel() {

    private val _pinStack: MutableLiveData<Stack<Int>> = MutableLiveData(Stack<Int>())
    var pinStack: LiveData<Stack<Int>> = _pinStack

    fun clickPin(number: Int) {
        when (number) {
            in 0..9 -> _pinStack.value = addPin(_pinStack.value!!, number)
            10 -> _pinStack.value = delPin(_pinStack.value!!)
        }
    }

    fun allPinEntered(callback: ((pinStack: Stack<Int>) -> Unit)?) {
        callback?.invoke(_pinStack.value!!)
    }

    private fun addPin(stack: Stack<Int>, number: Int): Stack<Int> {
        val newStack = Stack<Int>()
        newStack.addAll(stack)
        if (newStack.size < 6) newStack.push(number)
        return newStack
    }

    private fun delPin(stack: Stack<Int>): Stack<Int> {
        val newStack = Stack<Int>()
        newStack.addAll(stack)
        if (newStack.isNotEmpty()) newStack.pop()
        return newStack
    }
}