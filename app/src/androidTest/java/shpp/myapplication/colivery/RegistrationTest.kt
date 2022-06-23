package shpp.myapplication.colivery

import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
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
    fun emptyNickName() {
        nickname().performTextInput("some input")
        nickname().performTextClearance()
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
}