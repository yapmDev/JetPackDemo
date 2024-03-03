package com.example.jetpackdemo.view_models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class ProgressViewModel : ViewModel() {

    private var _progress by mutableFloatStateOf(0.0f)

    private val format = { float: Float -> "%.${2}f".format(float).toFloat() }
    val progress: Float
        get() = format(_progress)

    fun increase() {
        if (progress != 1.0f) _progress += 0.1f
        if (progress == 1.0f) {
            _progress = 0.0f
            onCompleted()
        }
    }

    private fun onCompleted() {}
}