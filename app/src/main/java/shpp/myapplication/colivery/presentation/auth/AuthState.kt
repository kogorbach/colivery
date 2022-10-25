package shpp.myapplication.colivery.presentation.auth

import androidx.annotation.StringRes
import shpp.myapplication.colivery.R

enum class AuthState(val authAction: String, @StringRes val changeAuthAction: Int) {
    SIGN_IN("Sign in", R.string.newToTheApp),
    SIGN_UP("Sign up", R.string.alreadyHaveAccount);

    fun changeState(): AuthState {
        return if (this == SIGN_IN) {
            SIGN_UP
        } else {
            SIGN_IN
        }
    }
}
