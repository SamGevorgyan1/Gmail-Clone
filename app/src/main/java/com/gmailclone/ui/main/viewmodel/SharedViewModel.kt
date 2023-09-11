package com.gmailclone.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    private val _enteredText = MutableLiveData<String>()
    val enteredText: LiveData<String>
        get() = _enteredText

    fun updateText(text: String) {
        _enteredText.value = text
    }
}
