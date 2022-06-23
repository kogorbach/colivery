package shpp.myapplication.colivery.presentation.auth

import androidx.annotation.StringRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
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
    val email = MutableLiveData("")
    val emailValid = MediatorLiveData<Boolean>().apply {
        addSource(email) {
            value = !android.util.Patterns.EMAIL_ADDRESS.matcher(it).matches()
                    && email.value!!.isNotEmpty()
        }
    }

    var password = mutableStateOf("")
    var passwordError = snapshotFlow {email}.mapLatest {
        password.value.length in 1 until PASSWORD_LENGTH
    }

    fun changeState() {
        state = if (state == AuthState.SIGN_IN) {
            AuthState.SIGN_UP
        } else {
            AuthState.SIGN_IN
        }
    }

    fun onEmailChange(input: String) {
        email.value = input
    }

    fun onPasswordChange(input: String) {

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