package shpp.myapplication.colivery.presentation.registration

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import shpp.myapplication.colivery.utils.InputValidator
import shpp.myapplication.colivery.utils.NicknameValidator
import shpp.myapplication.colivery.utils.TelegramValidator
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor() : ViewModel() {

    val nicknameValidator = NicknameValidator()
    val telegramValidator = TelegramValidator()

    fun signUp(email: String?, password: String?) {
        //todo implement
    }
}