package shpp.myapplication.colivery

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import shpp.myapplication.colivery.presentation.auth.AuthComposable
import shpp.myapplication.colivery.presentation.auth.RegistrationComposable


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class AuthTest {

    @get:Rule(order = 0)
    val composeTestRule = createComposeRule()
    lateinit var navController: NavHostController

    @Before
    fun init() {
        composeTestRule.setContent {
            navController = rememberNavController()
            NavHost(navController = navController, startDestination = "authScreen") {
                composable("authScreen") {
                    AuthComposable(navController)
                }
                composable(
                    "registrationScreen/{email}/{password}",
                    arguments = listOf(
                        navArgument("email") { type = NavType.StringType },
                        navArgument("password") { type = NavType.StringType })
                ) {
                    RegistrationComposable(
                        it.arguments?.getString("email"),
                        it.arguments?.getString("password")
                    )
                }
            }
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
    fun unFocusInvalidEmail() {
        emailError().assertDoesNotExist()
        emailTextInput().performTextInput("email")
        emailError().assertDoesNotExist()
        passwordTextInput().performClick()
        emailError().assertIsDisplayed()
    }

    @Test
    fun unFocusInvalidPassword() {
        passwordError().assertDoesNotExist()
        passwordTextInput().performTextInput("pass")
        passwordError().assertDoesNotExist()
        emailTextInput().performClick()
        passwordError().assertIsDisplayed()
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

    private fun mainActivity(): SemanticsNodeInteraction {
        return composeTestRule.onNode(hasContentDescription("mainActivity"))
    }
}