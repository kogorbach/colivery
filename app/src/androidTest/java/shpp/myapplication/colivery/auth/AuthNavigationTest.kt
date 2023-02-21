package shpp.myapplication.colivery.auth

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
//import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import shpp.myapplication.colivery.domain.repo.FirebaseRepository
import shpp.myapplication.colivery.presentation.auth.AuthActivity
import shpp.myapplication.colivery.utils.Semantics
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class AuthNavigationTest {

    @get:Rule
    val rule = createAndroidComposeRule<AuthActivity>()

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var authRepository: FirebaseRepository

    @Test
    fun startDestination() {
        authComposable.assertIsDisplayed()
        authActionButton.assertTextEquals()
    }

    @Test
    fun navigateToRegistration() {
        emailTextInput.performTextInput("validEmail@gmail.com")
        passwordTextInput.performTextInput("myp@ssWord23")
        // then inputs are valid
        emailError.assertDoesNotExist()
        passwordError.assertDoesNotExist()

        authActionButton.performClick()
        // then navigation is performed
        registrationComposable.assertIsDisplayed()
    }

    // todo enable after mocking repository
//    @Test
//    fun signIn() {
//        changeActionText.performClick()
//        emailTextInput.performTextInput("validEmail@gmail.com")
//        passwordTextInput.performTextInput("myp@ssWord23")
//        // then
//        emailError.assertDoesNotExist()
//        passwordError.assertDoesNotExist()
//        // launch main activity
//        authActionButton.performClick()
//        mainActivity.assertIsDisplayed()
//    }


    private val changeActionText by lazy {
        rule.onNode(hasContentDescription(Semantics.AUTH_CHANGE_OPTION))
    }

    private val authActionButton by lazy {
        rule.onNode(hasContentDescription(Semantics.AUTH_BUTTON))
    }

    private val emailTextInput by lazy {
        rule.onNode(hasContentDescription(Semantics.EMAIL_INPUT))
    }

    private val passwordTextInput by lazy {
        rule.onNode(hasContentDescription(Semantics.PASSWORD_INPUT))
    }

    private val emailError by lazy {
        rule.onNode(hasContentDescription(Semantics.EMAIL_ERROR))
    }

    private val passwordError by lazy {
        rule.onNode(hasContentDescription(Semantics.PASSWORD_ERROR))
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

//    private val route by lazy {
//        navController.currentBackStackEntry?.destination?.route
//    }
}