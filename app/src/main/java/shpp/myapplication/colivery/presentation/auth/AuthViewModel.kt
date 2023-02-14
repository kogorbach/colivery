package shpp.myapplication.colivery.presentation.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import shpp.myapplication.colivery.data.network.Response
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
    var loadingState by mutableStateOf(false)
    var authError by mutableStateOf<String?>(null)

    fun changeState() {
        state = state.changeState()
    }

    fun validate(): Boolean {
        emailValidator.validate()
        passwordValidator.validate()
        return !passwordValidator.error && !emailValidator.error
    }

    fun signIn() {
        viewModelScope.launch {
            firebase.signIn(emailValidator.input, passwordValidator.input).collectLatest {
                when (it) {
                    is Response.Failure -> {
                        authError = it.message
                        loadingState = false
                    }

                    Response.Loading -> loadingState = true

                    is Response.Success -> {
                        loadingState = false
                    }
                }
            }
        }
    }

    fun signInWithGoogle() {
        // todo implement [by Kostyan:]
    }
}