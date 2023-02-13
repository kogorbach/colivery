package shpp.myapplication.colivery.auth

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import shpp.myapplication.colivery.R
import shpp.myapplication.colivery.presentation.auth.AuthComposable
import shpp.myapplication.colivery.presentation.auth.AuthState
import shpp.myapplication.colivery.utils.EmailValidator
import shpp.myapplication.colivery.utils.PasswordValidator
import shpp.myapplication.colivery.utils.Semantics


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class AuthTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    private val context = ApplicationProvider.getApplicationContext<Context>()

    private var authState = mutableStateOf(AuthState.SIGN_UP)

    @Before
    fun init() {
        composeTestRule.setContent {
            AuthComposable(
                authState = authState.value,
                changeState = { authState.value = authState.value.changeState() },
                emailValidator = EmailValidator(),
                passwordValidator = PasswordValidator()
            )
        }
    }

    @Test
    fun initialState_displaySignUp() {
        changeActionText.assertTextEquals(getString(R.string.alreadyHaveAccount))
        authActionButton.assertTextEquals(getString(R.string.authActionSignUp))
    }

    @Test
    fun changeAuthState_displaySignIn() {
        changeActionText.performClick()
        changeActionText.assertTextEquals(getString(R.string.newToTheApp))
        authActionButton.assertTextEquals(getString(R.string.authActionSignIn))
    }

    @Test
    fun unFocusInvalidEmail_displayError() {
        emailError.assertDoesNotExist()
        emailTextInput.performTextInput("email")
        emailError.assertDoesNotExist()
        passwordTextInput.performClick()
        emailError.assertIsDisplayed()

        emailTextInput.performTextInput("email@gmail.com")
        emailError.assertDoesNotExist()
    }

    @Test
    fun unFocusInvalidPassword_displayError() {
        passwordError.assertDoesNotExist()
        passwordTextInput.performTextInput("pass")
        passwordError.assertDoesNotExist()
        emailTextInput.performClick()
        passwordError.assertIsDisplayed()

        passwordTextInput.performTextInput("password")
        passwordError.assertDoesNotExist()
    }

    @Test
    fun signUpInvalidEmail_displayError() {
        emailTextInput.performTextInput("email")
        authActionButton.performClick()
        emailError.assertIsDisplayed()
    }

    @Test
    fun signUpInvalidPassword_displayError() {
        passwordTextInput.performTextInput("pass")
        authActionButton.performClick()
        passwordError.assertIsDisplayed()
    }

    @Test
    fun fixInvalidEmail_hideError() {
        emailTextInput.performTextInput("email")
        passwordTextInput.performClick()
        emailTextInput.performTextInput("email@gmail.com")
        emailError.assertDoesNotExist()
    }

    @Test
    fun fixInvalidPassword_hideError() {
        passwordTextInput.performTextInput("pass")
        emailTextInput.performClick()
        passwordTextInput.performTextInput("password")
        passwordError.assertDoesNotExist()
    }

    @Test
    fun emptyFieldsSignUp_displayBothErrors() {
        authActionButton.performClick()
        //then show both errors
        emailError.assertIsDisplayed()
        passwordError.assertIsDisplayed()
    }

    private val changeActionText by lazy {
        composeTestRule.onNode(hasContentDescription(Semantics.AUTH_CHANGE_OPTION))
    }

    private val authActionButton by lazy {
        composeTestRule.onNode(hasContentDescription(Semantics.AUTH_BUTTON))
    }

    private val emailTextInput by lazy {
        composeTestRule.onNode(hasContentDescription(Semantics.EMAIL_INPUT))
    }

    private val passwordTextInput by lazy {
        composeTestRule.onNode(hasContentDescription(Semantics.PASSWORD_INPUT))
    }

    private val emailError by lazy {
        composeTestRule.onNode(hasContentDescription(Semantics.EMAIL_ERROR))
    }

    private val passwordError by lazy {
        composeTestRule.onNode(hasContentDescription(Semantics.PASSWORD_ERROR))
    }

    private fun getString(@StringRes id: Int): String {
        return context.getString(id)
    }

}