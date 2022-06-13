package shpp.myapplication.colivery.presentation.auth

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor() : ViewModel() {

    var nickname: String? = null
    var telegram: String? = null

    fun signUp(email: String?, password: String?) {
        //todo implement
    }
}