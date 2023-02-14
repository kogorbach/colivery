package shpp.myapplication.colivery

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import shpp.myapplication.colivery.presentation.registration.RegistrationComposable
import shpp.myapplication.colivery.utils.Semantics

@RunWith(JUnit4::class)
class RegistrationTest {

    @get:Rule(order = 0)
    val composeTestRule = createComposeRule()

    @Before
    fun init() {
        composeTestRule.setContent {
            RegistrationComposable()
        }
    }

    @Test
    fun invalidNickname() {
        nickname.performClick()
        telegram.performClick()
        nicknameError.assertIsDisplayed()
    }

    @Test
    fun invalidTelegram() {
        telegramError.assertDoesNotExist()
        telegram.performTextInput("tel")
        nickname.performClick()
        telegramError.assertIsDisplayed()
    }

    @Test
    fun fixInvalidNickname() {
        nickname.performClick()
        telegram.performClick()
        nickname.performTextInput("nick")
        nicknameError.assertDoesNotExist()
    }

    @Test
    fun fixInvalidTelegram() {
        telegram.performTextInput("tel")
        nickname.performClick()
        telegram.performTextInput("telegram")
        telegramError.assertDoesNotExist()
    }

    private val nickname by lazy {
        composeTestRule.onNode(hasContentDescription(Semantics.NICKNAME_INPUT))
    }

    private val telegram by lazy {
        composeTestRule.onNode(hasContentDescription(Semantics.TELEGRAM_INPUT))
    }

    private val telegramError by lazy {
        composeTestRule.onNode(hasContentDescription(Semantics.TELEGRAM_ERROR))
    }

    private val nicknameError by lazy {
        composeTestRule.onNode(hasContentDescription(Semantics.NICKNAME_ERROR))
    }

    private val completeButton by lazy {
        composeTestRule.onNode(hasContentDescription(Semantics.COMPLETE_BUTTON))
    }
}