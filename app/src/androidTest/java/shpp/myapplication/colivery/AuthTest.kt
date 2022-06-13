package shpp.myapplication.colivery

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import shpp.myapplication.colivery.presentation.auth.AuthComposable


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class AuthTest {

    @get:Rule(order = 0)
    val composeTestRule = createComposeRule()

    @Before
    fun init() {
        composeTestRule.setContent {
            AuthComposable()
        }
    }

    @Test
    fun initialState() {
        changeActionText().assertTextEquals("Already have an account? Sign in")
        authActionButton().assertTextEquals("Sign up")
    }

    @Test
    fun changeAuthState() {
        changeActionText().performClick()
        changeActionText().assertTextEquals("New to the app? Sign up")
        authActionButton().assertTextEquals("Sign in")
    }

    @Test
    fun invalidEmail() {
        emailTextInput().performTextInput("email")
        emailError().assertIsDisplayed()
    }

    @Test
    fun invalidPassword() {
        passwordTextInput().performTextInput("pass")
        passwordError().assertIsDisplayed()
    }

    @Test
    fun signUp() {
        emailTextInput().performTextInput("validEmail@gmail.com")
        passwordTextInput().performTextInput("myp@ssWord23")
        authActionButton().performClick()
        // then
        emailError().assertIsNotDisplayed()
        passwordError().assertIsNotDisplayed()
        // todo verify navigation to RegistrationComposable
    }

    @Test
    fun signIn() {
        changeActionText().performClick()
        emailTextInput().performTextInput("validEmail@gmail.com")
        passwordTextInput().performTextInput("myp@ssWord23")
        authActionButton().performClick()
        // then
        emailError().assertIsNotDisplayed()
        passwordError().assertIsNotDisplayed()
        //todo verify intent launch
    }

    private fun changeActionText(): SemanticsNodeInteraction {
        return composeTestRule.onNode(hasContentDescription("change auth action"))
    }

    private fun authActionButton(): SemanticsNodeInteraction {
        return composeTestRule.onNode(hasContentDescription("auth action button"))
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
}