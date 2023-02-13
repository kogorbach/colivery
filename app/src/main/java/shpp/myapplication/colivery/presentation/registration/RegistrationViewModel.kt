package shpp.myapplication.colivery.presentation.registration

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
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

    private val _signUpResult = MutableSharedFlow<SignUpResult>()
    val signUpResult: SharedFlow<SignUpResult> = _signUpResult

    fun signUp(email: String?, password: String?) {
        if (email == null || password == null) {
            return
        }
        _signUpResult.emit(SignUpResult.Loading)
        repository.signUp(
            email,
            password,
            user = UserModel(
                email = email,
                nickname = nicknameValidator.input.value,
                telegram = telegramValidator.input.value,
            ),
            onSuccess = {
                repository.signIn(email, password, onSuccess = {
                    _signUpResult.value = SignUpResult.Success
                })
            },
            onFailure = {error ->
                _signUpResult.value = SignUpResult.Error(error)
            }
        )
    }

    sealed class SignUpResult {
        data class Error(val error: String): SignUpResult()
        object Loading: SignUpResult()
        object Success: SignUpResult()
        object Idle: SignUpResult()
    }
}