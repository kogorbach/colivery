package shpp.myapplication.colivery

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import shpp.myapplication.colivery.presentation.auth.RegistrationComposable

@RunWith(JUnit4::class)
class RegistrationTest {

    @get:Rule(order = 0)
    val composeTestRule = createComposeRule()

    @Before
    fun init() {
        composeTestRule.setContent {
            RegistrationComposable(
                email = "email@gmail.com",
                password = "password"
            )
        }
    }

    @Test
    fun invalidNickname() {
        nickname().performClick()
        telegram().performClick()
        nicknameError().assertIsDisplayed()
    }

    @Test
    fun invalidTelegram() {
        telegramError().assertDoesNotExist()
        telegram().performTextInput("tel")
        nickname().performClick()
        telegramError().assertIsDisplayed()
    }

    @Test
    fun fixInvalidNickname() {
        nickname().performClick()
        telegram().performClick()
        nickname().performTextInput("nick")
        nicknameError().assertDoesNotExist()
    }

    @Test
    fun fixInvalidTelegram() {
        telegram().performTextInput("tel")
        nickname().performClick()
        telegram().performTextInput("telegram")
        telegramError().assertDoesNotExist()
    }

    private fun nickname(): SemanticsNodeInteraction {
        return composeTestRule.onNode(hasContentDescription("nickname"))
    }

    private fun telegram(): SemanticsNodeInteraction {
        return composeTestRule.onNode(hasContentDescription("telegram"))
    }

    private fun telegramError(): SemanticsNodeInteraction {
        return composeTestRule.onNode(hasContentDescription("telegramError"))
    }

    private fun nicknameError(): SemanticsNodeInteraction {
        return composeTestRule.onNode(hasContentDescription("nicknameError"))
    }

    private fun completeButton(): SemanticsNodeInteraction {
        return composeTestRule.onNode(hasContentDescription("completeButton"))
    }
}