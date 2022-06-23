package shpp.myapplication.colivery.presentation.auth

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor() : ViewModel() {

    companion object {
        const val TELEGRAM_MIN_LENGTH = 5
    }

    var nicknameState = mutableStateOf("")
    val telegramLiveData = MutableLiveData("")
    val telegramError = MediatorLiveData<String?>().apply {
        addSource(telegramLiveData) {
            value = if (it.length < TELEGRAM_MIN_LENGTH) {
                "telegram must be at least $TELEGRAM_MIN_LENGTH characters long"
            } else {
                null
            }
        }
    }

    private var telegramInputStarted = false
    private var nickNameInputStarted = false

    fun signUp(email: String?, password: String?) {
        //todo implement
    }

    fun onNickNameChange(input: String) {
        nickNameInputStarted = true
        nicknameState.value = input
    }

    fun onTelegramChange(input: String) {
        telegramInputStarted = true
        telegramLiveData.value = input
    }
}