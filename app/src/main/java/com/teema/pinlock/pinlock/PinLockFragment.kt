package com.teema.pinlock.pinlock

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Display
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.teema.pinlock.R
import com.teema.pinlock.databinding.PinLockFragmentBinding
import java.util.*
import kotlin.collections.ArrayList

class PinLockFragment(private var onAllPinEntered: (pinStack: Stack<Int>) -> Unit) :
    BottomSheetDialogFragment() {

    companion object {
        fun newInstance(onAllPinEntered: ((pinStack: Stack<Int>) -> Unit)) =
            PinLockFragment(onAllPinEntered)
    }

    private lateinit var viewModel: PinLockViewModel
    private val binding: PinLockFragmentBinding by lazy {
        PinLockFragmentBinding.inflate(
            layoutInflater
        )
    }

    private lateinit var layout: FrameLayout

    private lateinit var pinDisplays: ArrayList<ImageView>
    private lateinit var pinButtons: ArrayList<Button>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PinLockViewModel::class.java)

        bindView()
        bindListener()
        observeViewModelData()
    }

    private fun observeViewModelData() {
        viewModel.pinStack.observe(this, {
            for (idx in 0 until pinDisplays.size) {
                pinDisplays[idx].setImageDrawable(
                    ContextCompat.getDrawable(
                        context!!,
                        if (idx < it.size) R.drawable.pin_entered else R.drawable.pin_empty
                    )
                )
            }

            if (it.size == 6) {
                this.dismiss()
                viewModel.allPinEntered(onAllPinEntered)
            }
        })
    }

    private fun bindView() {
        layout = binding.root

        pinDisplays = buildArray {
            add(binding.pinDisplay1)
            add(binding.pinDisplay2)
            add(binding.pinDisplay3)
            add(binding.pinDisplay4)
            add(binding.pinDisplay5)
            add(binding.pinDisplay6)
        }

        pinButtons = buildArray {
            add(binding.pinKey0)
            add(binding.pinKey1)
            add(binding.pinKey2)
            add(binding.pinKey3)
            add(binding.pinKey4)
            add(binding.pinKey5)
            add(binding.pinKey6)
            add(binding.pinKey7)
            add(binding.pinKey8)
            add(binding.pinKey9)
            add(binding.pinKeyBack)
        }

        layout.layoutParams.height = getBottomSheetHeight()
    }

    private fun bindListener() {
        for ((idx, view) in pinButtons.withIndex()) {
            view.setOnClickListener {
                viewModel.clickPin(idx)
            }
        }
    }

    override fun getTheme(): Int {
        return R.style.PinLockBottomSheetDialog
    }

    private fun getBottomSheetHeight(): Int = getWindowHeight() * 80 / 100

    private fun getWindowHeight(): Int {
        val displayMetrics = DisplayMetrics()
        getDisplay().getRealMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }

    private fun getDisplay(): Display {
        return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            context!!.display!!
        } else {
            (context as AppCompatActivity).windowManager.defaultDisplay
        }
    }

    private fun <V> buildArray(build: ArrayList<V>.() -> Unit): ArrayList<V> {
        val arrayList = ArrayList<V>()
        arrayList.build()
        return arrayList
    }

}