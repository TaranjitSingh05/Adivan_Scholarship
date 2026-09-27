package com.example.adivan.ui.jago

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class JagoViewModelFactory(
    private val screenContext: String,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(JagoViewModel::class.java)) {
            return JagoViewModel(screenContext) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
