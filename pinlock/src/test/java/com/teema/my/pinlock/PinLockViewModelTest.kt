package com.teema.my.pinlock

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PinLockViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var pinLockViewModel: PinLockViewModel

    @Before
    fun setUp() {
        pinLockViewModel = PinLockViewModel()
    }

    @Test
    fun `When user click pin 1 2 3 respectively, the stack pop should be 3 2 1`() {
        pinLockViewModel.clickPin(1)
        pinLockViewModel.clickPin(2)
        pinLockViewModel.clickPin(3)
        pinLockViewModel.pinStack.observeForever {
            assertEquals(3, it.pop())
            assertEquals(2, it.pop())
            assertEquals(1, it.pop())
            assertTrue(it.isEmpty())
        }
    }

    @Test
    fun `When user click pin 1 2 3 4 respectively then click the delete button, the stack pop should be 3 2 1`() {
        pinLockViewModel.clickPin(1)
        pinLockViewModel.clickPin(2)
        pinLockViewModel.clickPin(3)
        pinLockViewModel.clickPin(4)
        pinLockViewModel.clickPin(10)
        pinLockViewModel.pinStack.observeForever {
            assertEquals(3, it.pop())
            assertEquals(2, it.pop())
            assertEquals(1, it.pop())
            assertTrue(it.isEmpty())
        }
    }



    @Test
    fun `When user enter 6 pin, 1 2 3 4 5 6, it should call the callback function with pinStack param that pop should be 6 5 4 3 2 1`() {
        pinLockViewModel.clickPin(1)
        pinLockViewModel.clickPin(2)
        pinLockViewModel.clickPin(3)
        pinLockViewModel.clickPin(4)
        pinLockViewModel.clickPin(5)
        pinLockViewModel.clickPin(6)
        pinLockViewModel.allPinEntered {
            assertEquals(6, it.size)
            assertEquals(6, it.pop())
            assertEquals(5, it.pop())
            assertEquals(4, it.pop())
            assertEquals(3, it.pop())
            assertEquals(2, it.pop())
            assertEquals(1, it.pop())
            assertTrue(it.isEmpty())
        }
    }
}