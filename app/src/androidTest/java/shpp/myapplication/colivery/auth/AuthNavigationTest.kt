package shpp.myapplication.colivery.auth

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import shpp.myapplication.colivery.presentation.auth.AuthNavHost
import shpp.myapplication.colivery.utils.Semantics

@RunWith(AndroidJUnit4::class)
class AuthNavigationTest {

    @get:Rule
    val rule = createComposeRule()
    lateinit var navController: TestNavHostController

    @Before
    fun setupAppNavHost() {
        rule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            AuthNavHost(navController)
        }
    }

    @Test
    fun startDestination() {

    }

    @Test
    fun signUp() {
        emailTextInput.performTextInput("validEmail@gmail.com")
        passwordTextInput.performTextInput("myp@ssWord23")
        // then inputs are valid
        emailError.assertDoesNotExist()
        passwordError.assertDoesNotExist()

        authActionButton.performClick()
        // then navigation is performed
        registrationComposable.assertIsDisplayed()
    }

    @Test
    fun signIn() {
        changeActionText.performClick()
        emailTextInput.performTextInput("validEmail@gmail.com")
        passwordTextInput.performTextInput("myp@ssWord23")
        // then
        emailError.assertDoesNotExist()
        passwordError.assertDoesNotExist()
        // launch main activity
        authActionButton.performClick()
        mainActivity.assertIsDisplayed()
    }


    private val changeActionText by lazy {
        rule.onNode(hasContentDescription(Semantics.AUTH_CHANGE_OPTION))
    }

    private val authActionButton by lazy {
        rule.onNode(hasContentDescription(Semantics.AUTH_BUTTON))
    }

    private val emailTextInput by lazy {
        rule.onNode(hasContentDescription("email input"))
    }

    private val passwordTextInput by lazy {
        rule.onNode(hasContentDescription("password input"))
    }

    private val emailError by lazy {
        rule.onNode(hasContentDescription("email error"))
    }

    private val passwordError by lazy {
        rule.onNode(hasContentDescription("password error"))
    }

    private val authComposable by lazy {
        rule.onNode(hasContentDescription(Semantics.AUTH_COMPOSABLE))
    }

    private val registrationComposable by lazy {
        rule.onNode(hasContentDescription(Semantics.REGISTRATION_COMPOSABLE))
    }

    private val mainActivity by lazy {
        rule.onNode(hasContentDescription(Semantics.MAIN_ACTIVITY))
    }

    private val route by lazy {
        navController.currentBackStackEntry?.destination?.route
    }
}