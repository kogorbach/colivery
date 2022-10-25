package shpp.myapplication.colivery

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
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
                authState = remember {
                    authState.value
                },
                changeState = { authState.value = authState.value.changeState() },
                emailValidator = EmailValidator(),
                passwordValidator = PasswordValidator()
            )
        }
    }

    @Test
    fun initialState() {
        changeActionText.assertTextEquals(getString(R.string.alreadyHaveAccount))
        authActionButton.assertTextEquals("Sign up")
    }

    @Test
    fun changeAuthState() {
        changeActionText.performClick()
        changeActionText.assertTextEquals(getString(R.string.newToTheApp))
        authActionButton.assertTextEquals("Sign in")
    }

    @Test
    fun unFocusInvalidEmail() {
        emailError.assertDoesNotExist()
        emailTextInput.performTextInput("email")
        emailError.assertDoesNotExist()
        passwordTextInput.performClick()
        emailError.assertIsDisplayed()

        emailTextInput.performTextInput("email@gmail.com")
        emailError.assertDoesNotExist()
    }

    @Test
    fun unFocusInvalidPassword() {
        passwordError.assertDoesNotExist()
        passwordTextInput.performTextInput("pass")
        passwordError.assertDoesNotExist()
        emailTextInput.performClick()
        passwordError.assertIsDisplayed()

        passwordTextInput.performTextInput("password")
        passwordError.assertDoesNotExist()
    }

    @Test
    fun signUpInvalidEmail() {
        emailTextInput.performTextInput("email")
        authActionButton.performClick()
        emailError.assertIsDisplayed()
    }

    @Test
    fun signUpInvalidPassword() {
        passwordTextInput.performTextInput("pass")
        authActionButton.performClick()
        passwordError.assertIsDisplayed()
    }

    @Test
    fun fixInvalidEmail() {
        emailTextInput.performTextInput("email")
        passwordTextInput.performClick()
        emailTextInput.performTextInput("email@gmail.com")
        emailError.assertDoesNotExist()
    }

    @Test
    fun fixInvalidPassword() {
        passwordTextInput.performTextInput("pass")
        emailTextInput.performClick()
        passwordTextInput.performTextInput("password")
        passwordError.assertDoesNotExist()
    }

    @Test
    fun emptyFieldsSignup() {
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
        composeTestRule.onNode(hasContentDescription("email input"))
    }

    private val passwordTextInput by lazy {
        composeTestRule.onNode(hasContentDescription("password input"))
    }

    private val emailError by lazy {
        composeTestRule.onNode(hasContentDescription("email error"))
    }

    private val passwordError by lazy {
        composeTestRule.onNode(hasContentDescription("password error"))
    }

    private fun getString(@StringRes id: Int): String {
        return context.getString(id)
    }

}