package shpp.myapplication.colivery.presentation.auth

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor() : ViewModel() {

    var nickname: String = ""
    var telegram: String = ""

    fun signUp() {
        // todo implement
    }

    fun completeRegistration() {
        TODO("Not yet implemented")
    }
}