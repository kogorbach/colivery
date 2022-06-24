package shpp.myapplication.colivery.presentation.auth

import androidx.annotation.StringRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import shpp.myapplication.colivery.R
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor() : ViewModel() {

    companion object {
        const val PASSWORD_LENGTH = 8
    }

    var emailFocusLost = false
    var emailWasFocused = false

    var passwordWasFocused = false
    var passwordFocusLost = false

    var state by mutableStateOf(AuthState.SIGN_UP)

    val emailLiveData = MutableLiveData("")
    val emailError = MediatorLiveData<Boolean>().apply {
        addSource(emailLiveData) { email ->
            value = !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
                    && emailFocusLost
        }
    }

    var passwordLiveData = MutableLiveData("")
    val passwordError = MediatorLiveData<Boolean>().apply {
        addSource(passwordLiveData) { password ->
            value = password.length in 0 until PASSWORD_LENGTH
                    && passwordFocusLost
        }
    }

    fun changeState() {
        state = if (state == AuthState.SIGN_IN) {
            AuthState.SIGN_UP
        } else {
            AuthState.SIGN_IN
        }
    }

    fun emailUnfocus(forced: Boolean = false) {
        if (emailWasFocused || forced) {
            emailFocusLost = true
            onEmailChange()
        }
    }

    fun passwordUnfocus(forced: Boolean = false) {
        if (passwordWasFocused || forced) {
            passwordFocusLost = true
            onPasswordChange()
        }
    }

    fun validate(): Boolean {
        emailUnfocus(forced = true)
        passwordUnfocus(forced = true)
        return passwordError.value == false && emailError.value == false
    }

    fun onEmailChange(input: String = emailLiveData.value!!) {
        emailLiveData.value = input
    }

    fun onPasswordChange(input: String = passwordLiveData.value!!) {
        passwordLiveData.value = input
    }

    fun signInWithGoogle() {

    }

    enum class AuthState(val text: AuthStateText) {
        SIGN_IN(AuthStateText("Sign in", R.string.newToTheApp)),
        SIGN_UP(AuthStateText("Sign up", R.string.alreadyHaveAccount))
    }

    data class AuthStateText(
        val authAction: String,
        @StringRes val changeAuthAction: Int
    )
}