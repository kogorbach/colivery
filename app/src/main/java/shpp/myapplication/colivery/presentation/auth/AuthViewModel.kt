package shpp.myapplication.colivery.presentation.auth

import androidx.annotation.StringRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import shpp.myapplication.colivery.R
import shpp.myapplication.colivery.data.FirebaseService
import shpp.myapplication.colivery.utils.InputValidator
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val firebase: FirebaseService
) : ViewModel() {

    companion object {
        const val PASSWORD_LENGTH = 8
    }

    val emailValidator = object : InputValidator() {
        override fun checkError(query: String): Boolean {
            return !android.util.Patterns.EMAIL_ADDRESS.matcher(query).matches()
        }
    }

    val passwordValidator = object : InputValidator() {
        override fun checkError(query: String): Boolean {
            return query.length in 0 until PASSWORD_LENGTH
        }
    }

    var state by mutableStateOf(AuthState.SIGN_UP)

    fun changeState() {
        state = if (state == AuthState.SIGN_IN) {
            AuthState.SIGN_UP
        } else {
            AuthState.SIGN_IN
        }
    }

    fun validate(): Boolean {
        emailValidator.validate()
        passwordValidator.validate()
        return !passwordValidator.error.value && !emailValidator.error.value
    }

    fun signIn(onSuccess: () -> Unit, onFailure: () -> Unit) {
        firebase.signIn(emailValidator.input.value, passwordValidator.input.value, onSuccess = {
            if (it.isSuccessful) {
                onSuccess()
            } else {
                onFailure()
            }
        })
    }

    fun signInWithGoogle() {
        // todo implement [by ratrider:]
    }

    enum class AuthState(val authAction: String, @StringRes val changeAuthAction: Int) {
        SIGN_IN("Sign in", R.string.newToTheApp),
        SIGN_UP("Sign up", R.string.alreadyHaveAccount)
    }
}