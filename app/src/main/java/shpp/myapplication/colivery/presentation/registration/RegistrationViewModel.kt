package shpp.myapplication.colivery.presentation.registration

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import shpp.myapplication.colivery.data.network.Response
import shpp.myapplication.colivery.data.network.UserModel
import shpp.myapplication.colivery.domain.repo.FirebaseRepository
import shpp.myapplication.colivery.utils.NicknameValidator
import shpp.myapplication.colivery.utils.TelegramValidator
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val repository: FirebaseRepository
) : ViewModel() {

    val nicknameValidator = NicknameValidator()
    val telegramValidator = TelegramValidator()

    var displayLoadingState by mutableStateOf(false)
    var signUpResultState by mutableStateOf(SignUpResult.Idle)

    fun signUp(email: String?, password: String?) {
        if (email == null || password == null) {
            return
        }

        viewModelScope.launch {
            repository.signUp(
                email,
                password,
                UserModel(
                  nickname = nicknameValidator.input.value,
                  telegram = nicknameValidator.input.value // todo implement phone and image
                )
            ).collectLatest {
                when (it) {
                    is Response.Failure -> TODO()
                    Response.Loading -> displayLoadingState = true
                    is Response.Success -> TODO()
                }
            }
        }
    }

    sealed class SignUpResult {
        data class Error(val error: String) : SignUpResult()
        object Loading : SignUpResult()
        object Success : SignUpResult()
        object Idle : SignUpResult()
    }
}