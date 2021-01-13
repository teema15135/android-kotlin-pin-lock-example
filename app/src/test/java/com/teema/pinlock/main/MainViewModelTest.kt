package com.teema.pinlock.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var mainViewModel: MainViewModel

    @Before
    fun setUp() {
        mainViewModel = MainViewModel()
    }

    @Test
    fun `When user click show pin lock, the showPinLock state should change to true`() {
        mainViewModel.onClickShowPinLock()
        mainViewModel.showPinLock.observeForever {
            assertTrue(it)
        }
    }

    @Test
    fun `When all 6 pin entered, the display pin should be like pin entered`() {
        val enteredPin = "1 2 3 4 5 6"
        mainViewModel.onAllPinEntered(enteredPin)
        mainViewModel.pinDisplay.observeForever {
            assertEquals(enteredPin, it)
        }
    }
}