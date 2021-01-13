package com.teema.pinlock.main

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.teema.pinlock.databinding.ActivityMainBinding
import com.teema.pinlock.pinlock.PinLockFragment

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private lateinit var showPinLockButton: Button
    private lateinit var pinDisplayText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        bindView()
        bindListener()
        observeViewModelData()
    }

    private fun bindView() {
        showPinLockButton = binding.showPinLockButton
        pinDisplayText = binding.pinDisplayText
    }

    private fun bindListener() {
        showPinLockButton.setOnClickListener {
            viewModel.onClickShowPinLock()
        }
    }

    private fun showPinLockDialog() {
        val pinLockFragment = PinLockFragment.newInstance {
            val builder = StringBuilder()
            it.toList().joinTo(builder, " ")
            viewModel.onAllPinEntered(builder.toString())
        }
        pinLockFragment.show(supportFragmentManager, "pin_lock_fragment")
    }

    private fun observeViewModelData() {
        viewModel.showPinLock.observe(this, {
            if (it) {
                showPinLockDialog()
            }
        })

        viewModel.pinDisplay.observe(this, {
            pinDisplayText.text = it
        })
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}