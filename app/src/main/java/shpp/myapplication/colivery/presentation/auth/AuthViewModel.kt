package shpp.myapplication.colivery.presentation.auth

import androidx.annotation.StringRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.mapLatest
import shpp.myapplication.colivery.R
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class AuthViewModel @Inject constructor() : ViewModel() {

    companion object {
        const val PASSWORD_LENGTH = 8
    }

    var state by mutableStateOf(AuthState.SIGN_UP)
    var email = mutableStateOf("")
    var password = mutableStateOf("")
    var passwordError = snapshotFlow {email}.mapLatest {
        password.value.length in 1 until PASSWORD_LENGTH
    }
    var emailError =
        mutableStateOf(
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email.value).matches()
                    && email.value.isNotEmpty()
        )

    fun changeState() {
        state = if (state == AuthState.SIGN_IN) {
            AuthState.SIGN_UP
        } else {
            AuthState.SIGN_IN
        }
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