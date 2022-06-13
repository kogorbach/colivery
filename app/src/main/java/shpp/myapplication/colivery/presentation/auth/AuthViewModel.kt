package shpp.myapplication.colivery.presentation.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor() : ViewModel() {

    companion object {
        const val PASSWORD_LENGTH = 8
    }

    var state by mutableStateOf(AuthState.SIGN_UP)
    private set

    var email = mutableStateOf("")
    var password = mutableStateOf("")

    fun changeState() {
        state = if (state == AuthState.SIGN_IN) {
            AuthState.SIGN_UP
        } else {
            AuthState.SIGN_IN
        }
    }

    fun signInWithGoogle() {

    }

    enum class AuthState(val text: String) {
        SIGN_IN("Sign in"),
        SIGN_UP("Sign up")
    }
}