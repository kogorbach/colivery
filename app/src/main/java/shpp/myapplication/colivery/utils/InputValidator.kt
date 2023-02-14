package shpp.myapplication.colivery.utils

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.focus.FocusState

sealed class InputValidator {

    companion object {
        const val PASSWORD_LENGTH = 8
        const val TELEGRAM_MIN_LENGTH = 5
    }

    var input by mutableStateOf("")
    var error by mutableStateOf(false)

    var wasFocused = false
    var focusLost = false

    fun onInputChange(query: String = input) {
        input = query
        error = checkError() && focusLost
    }

    fun onFocusChange(state: FocusState) {
        if (state.isFocused) {
            onFocus()
        }
        if (!state.hasFocus) {
            onUnfocus()
        }
    }

    fun validate() {
        onUnfocus(forced = true)
    }

    protected abstract fun checkError(query: String = input): Boolean

    private fun onFocus() {
        wasFocused = true
    }

    private fun onUnfocus(forced: Boolean = false) {
        if (wasFocused || forced) {
            focusLost = true
            onInputChange()
        }
    }
}

class MockValidator : InputValidator() {
    override fun checkError(query: String): Boolean {
        return false
    }
}

class EmailValidator : InputValidator() {
    override fun checkError(query: String): Boolean {
        return !android.util.Patterns.EMAIL_ADDRESS.matcher(query).matches()
    }
}

class PasswordValidator : InputValidator() {
    override fun checkError(query: String): Boolean {
        return query.length in 0 until PASSWORD_LENGTH
    }
}

class NicknameValidator : InputValidator() {
    override fun checkError(query: String): Boolean {
        return query.isEmpty()
    }
}

class TelegramValidator : InputValidator() {
    override fun checkError(query: String): Boolean {
        return query.length < TELEGRAM_MIN_LENGTH
    }
}