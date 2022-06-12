package shpp.myapplication.colivery

import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import shpp.myapplication.colivery.presentation.auth.AuthActivity
import shpp.myapplication.colivery.presentation.auth.AuthComposable

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class AuthTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<AuthActivity>()

//    @Test
//    fun useAppContext() {
//        // Context of the app under test.
//        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
//        assertEquals("shpp.myapplication.colivery", appContext.packageName)
//    }

    @Before
    fun init() {
        ActivityScenario.launch(AuthActivity::class.java)
        composeTestRule.setContent {
            AuthComposable()
        }
    }

    @Test
    fun initialState() {
        with(composeTestRule) {
            changeActionText().assertTextEquals(activity.getString(R.string.alreadyHaveAccount))
        }
    }

    private fun changeActionText(): SemanticsNodeInteraction {
        return composeTestRule.onNode(hasContentDescription("change auth action"))
    }
}