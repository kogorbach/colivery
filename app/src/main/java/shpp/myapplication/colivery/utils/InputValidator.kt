package shpp.myapplication.colivery.utils

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData

abstract class InputValidator {

    companion object {
        // for previews
        fun mockValidator() = object: InputValidator() {
            override fun checkError(query: String): Boolean {
                return false
            }
        }
    }

    var input = mutableStateOf("")
    var error = mutableStateOf(false)

    var wasFocused = false
    var focusLost = false

    fun onInputChange(query: String = input.value) {
        input.value = query
        error.value = checkError() && focusLost
    }

    fun onFocus() {
        wasFocused = true
    }

    fun onUnfocus(forced: Boolean = false) {
        if (wasFocused || forced) {
            focusLost = true
            onInputChange()
        }
    }

    fun validate() {
        onUnfocus(forced = true)
    }

    protected abstract fun checkError(query: String = input.value): Boolean
}