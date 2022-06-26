package shpp.myapplication.colivery.presentation.auth

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import shpp.myapplication.colivery.utils.InputValidator
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor() : ViewModel() {

    companion object {
        const val TELEGRAM_MIN_LENGTH = 5
    }

    val nicknameValidator = object: InputValidator() {
        override fun checkError(query: String): Boolean {
            return query.isEmpty()
        }
    }

    val telegramValidator = object: InputValidator() {
        override fun checkError(query: String): Boolean {
            return query.length < TELEGRAM_MIN_LENGTH
        }
    }

    fun signUp(email: String?, password: String?) {
        //todo implement
    }
}