package shpp.myapplication.colivery.presentation.auth

import androidx.annotation.StringRes
import shpp.myapplication.colivery.R

enum class AuthState(@StringRes val authAction: Int, @StringRes val changeAuthAction: Int) {
    SIGN_IN(R.string.authActionSignIn, R.string.newToTheApp),
    SIGN_UP(R.string.authActionSignUp, R.string.alreadyHaveAccount);

    fun changeState(): AuthState {
        return if (this == SIGN_IN) {
            SIGN_UP
        } else {
            SIGN_IN
        }
    }
}
