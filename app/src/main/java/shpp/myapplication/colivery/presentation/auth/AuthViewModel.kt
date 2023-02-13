package shpp.myapplication.colivery.presentation.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import shpp.myapplication.colivery.domain.repo.FirebaseRepository
import shpp.myapplication.colivery.utils.EmailValidator
import shpp.myapplication.colivery.utils.PasswordValidator
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val firebase: FirebaseRepository
) : ViewModel() {

    val emailValidator = EmailValidator()
    val passwordValidator = PasswordValidator()

    var state by mutableStateOf(AuthState.SIGN_UP)

    fun changeState() {
        state = state.changeState()
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
        // todo implement [by Kostyan:]
    }
}