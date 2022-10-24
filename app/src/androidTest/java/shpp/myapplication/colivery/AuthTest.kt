package shpp.myapplication.colivery

import androidx.annotation.StringRes
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import shpp.myapplication.colivery.presentation.auth.AuthActivity
import shpp.myapplication.colivery.presentation.auth.AuthComposable
import shpp.myapplication.colivery.utils.Semantics


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class AuthTest {

    @get:Rule(order = 0)
    val composeTestRule = createAndroidComposeRule<AuthActivity>()

//    @Before
//    fun init() {
//        composeTestRule.setContent {
//            AuthComposable()
//        }
//    }

    @Test
    fun initialState() {
        changeActionText().assertTextEquals(getString(R.string.newToTheApp))
        authActionButton().assertTextEquals("Sign up")
    }

    @Test
    fun changeAuthState() {
        changeActionText().performClick()
        changeActionText().assertTextEquals("New to the app? Sign up")
        authActionButton().assertTextEquals("Sign in")
    }

    @Test
    fun unFocusInvalidEmail() {
        emailError().assertDoesNotExist()
        emailTextInput().performTextInput("emawil")
        emailError().assertDoesNotExist()
        passwordTextInput().performClick()
        emailError().assertIsDisplayed()

        emailTextInput().performTextInput("email@gmail.com")
        emailError().assertDoesNotExist()
    }

    @Test
    fun unFocusInvalidPassword() {
        passwordError().assertDoesNotExist()
        passwordTextInput().performTextInput("pass")
        passwordError().assertDoesNotExist()
        emailTextInput().performClick()
        passwordError().assertIsDisplayed()

        passwordTextInput().performTextInput("password")
        passwordError().assertDoesNotExist()
    }

    @Test
    fun signUpInvalidEmail() {
        emailTextInput().performTextInput("email")
        authActionButton().performClick()
        emailError().assertIsDisplayed()
    }

    @Test
    fun signUpInvalidPassword() {
        passwordTextInput().performTextInput("pass")
        authActionButton().performClick()
        passwordError().assertIsDisplayed()
    }

    @Test
    fun fixInvalidEmail() {
        emailTextInput().performTextInput("email")
        passwordTextInput().performClick()
        emailTextInput().performTextInput("email@gmail.com")
        emailError().assertDoesNotExist()
    }

    @Test
    fun fixInvalidPassword() {
        passwordTextInput().performTextInput("pass")
        emailTextInput().performClick()
        passwordTextInput().performTextInput("password")
        passwordError().assertDoesNotExist()
    }

    @Test
    fun emptyFieldsSignup() {
        authActionButton().performClick()
        //then
        emailError().assertIsDisplayed()
        passwordError().assertIsDisplayed()
    }

    @Test
    fun signUp() {
        emailTextInput().performTextInput("validEmail@gmail.com")
        passwordTextInput().performTextInput("myp@ssWord23")
        // then inputs are valid
        emailError().assertDoesNotExist()
        passwordError().assertDoesNotExist()

        authActionButton().performClick()
        // then navigation is performed
        composeTestRule.onNode(hasContentDescription("registration screen")).assertIsDisplayed()
    }

    @Test
    fun signIn() {
        changeActionText().performClick()
        emailTextInput().performTextInput("validEmail@gmail.com")
        passwordTextInput().performTextInput("myp@ssWord23")
        // then
        emailError().assertDoesNotExist()
        passwordError().assertDoesNotExist()
        // launch main activity
        authActionButton().performClick()
        mainActivity().assertIsDisplayed()
    }

    private fun changeActionText(): SemanticsNodeInteraction {
        return composeTestRule.onNode(hasContentDescription(Semantics.AUTH_CHANGE_OPTION))
    }

    private fun authActionButton(): SemanticsNodeInteraction {
        return composeTestRule.onNode(hasContentDescription(Semantics.AUTH_BUTTON))
    }

    private fun emailTextInput(): SemanticsNodeInteraction {
        return composeTestRule.onNode(hasContentDescription("email input"))
    }

    private fun passwordTextInput(): SemanticsNodeInteraction {
        return composeTestRule.onNode(hasContentDescription("password input"))
    }

    private fun emailError(): SemanticsNodeInteraction {
        return composeTestRule.onNode(hasContentDescription("email error"))
    }

    private fun passwordError(): SemanticsNodeInteraction {
        return composeTestRule.onNode(hasContentDescription("password error"))
    }

    private fun mainActivity(): SemanticsNodeInteraction {
        return composeTestRule.onNode(hasContentDescription(Semantics.MAIN_ACTIVITY))
    }

    private fun getString(@StringRes id: Int): String {
        return composeTestRule.activity.getString(id)
    }

}