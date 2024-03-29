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
import shpp.myapplication.colivery.domain.repo.AuthRepository
import shpp.myapplication.colivery.utils.EmailValidator
import shpp.myapplication.colivery.utils.PasswordValidator
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepo: AuthRepository
) : ViewModel() {

    val emailValidator = EmailValidator()
    val passwordValidator = PasswordValidator()

    var state by mutableStateOf(AuthState.SIGN_UP)
        private set
    var isLoading by mutableStateOf(false)
        private set
    var signInComplete by mutableStateOf(false)
        private set
    var signInError by mutableStateOf<String?>(null)
        private set
    var authButtonClicked by mutableStateOf(false)

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
            authRepo.signIn(emailValidator.input, passwordValidator.input).collectLatest {
                when (it) {
                    is Response.Failure -> {
                        isLoading = false
                        signInError = it.message
                    }

                    Response.Loading -> {
                        signInError = null
                        isLoading = true
                    }

                    is Response.Success -> {
                        signInError = null
                        isLoading = false
                        signInComplete = true
                    }
                }
            }
        }
    }

    fun signInWithGoogle() {
        // todo implement [by Kostyan:]
    }
}