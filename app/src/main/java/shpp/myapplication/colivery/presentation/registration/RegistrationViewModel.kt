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
import shpp.myapplication.colivery.domain.repo.AuthRepository
import shpp.myapplication.colivery.utils.NicknameValidator
import shpp.myapplication.colivery.utils.TelegramValidator
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    val nicknameValidator = NicknameValidator()
    val telegramValidator = TelegramValidator()

    var displayLoading by mutableStateOf(false)
        private set
    var signUpComplete by mutableStateOf(false)
        private set
    var signUpError by mutableStateOf<String?>(null)
        private set

    fun signUp(email: String?, password: String?) {
        if (email == null || password == null) {
            return
        }

        viewModelScope.launch {
            repository.signUp(
                email,
                password,
                UserModel(
                    nickname = nicknameValidator.input,
                    telegram = nicknameValidator.input // todo implement phone and image
                )
            ).collectLatest {
                when (it) {
                    is Response.Failure -> {
                        displayLoading = false
                        signUpError = it.message
                    }
                    Response.Loading -> {
                        signUpError = null
                        displayLoading = true
                    }
                    is Response.Success -> {
                        signUpError = null
                        displayLoading = false
                        signUpComplete = true
                    }
                }
            }
        }
    }
}