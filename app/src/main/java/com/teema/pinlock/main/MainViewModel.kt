package com.teema.pinlock.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val _showPinLock: MutableLiveData<Boolean> = MutableLiveData()
    val showPinLock: LiveData<Boolean> = _showPinLock

    private val _pinDisplay: MutableLiveData<String> = MutableLiveData("No pin entered")
    val pinDisplay: LiveData<String> = _pinDisplay

    fun onClickShowPinLock() {
        _showPinLock.value = true
    }

    fun onAllPinEntered(pin: String) {
        _pinDisplay.value = pin
    }
}