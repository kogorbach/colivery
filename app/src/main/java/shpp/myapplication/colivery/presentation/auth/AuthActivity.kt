package shpp.myapplication.colivery.presentation.auth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import shpp.myapplication.colivery.presentation.registration.RegistrationComposable
import shpp.myapplication.colivery.presentation.ui.theme.ColiveryTheme
import shpp.myapplication.colivery.utils.Semantics

const val EMAIL_NAV_KEY = "email"
const val PASSWORD_NAV_KEY = "password"

@AndroidEntryPoint
class AuthActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AuthNavHost(rememberNavController())
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        ColiveryTheme {
            AuthComposable(
                authState = AuthState.SIGN_UP
            )
        }
    }

}

@Composable
fun AuthNavHost(navController: NavHostController) {
    ColiveryTheme {
        // A surface container using the 'background' color from the theme
        NavHost(navController = navController, startDestination = Semantics.AUTH_COMPOSABLE) {
            composable(Semantics.AUTH_COMPOSABLE) {
                AuthComposable(
                    onNavigateToRegistration = { email, password ->
                        navController.navigate("registrationScreen/$email/$password")
                    }
                )
            }
            composable(
                route = "registrationScreen/{email}/{password}",
                arguments = listOf(navArgument(EMAIL_NAV_KEY) { type = NavType.StringType },
                    navArgument(PASSWORD_NAV_KEY) { type = NavType.StringType })
            ) {
                RegistrationComposable(
                    email = it.arguments?.getString(EMAIL_NAV_KEY),
                    password = it.arguments?.getString(PASSWORD_NAV_KEY)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ColiveryTheme {
        AuthComposable(
            authState = AuthState.SIGN_UP
        )
    }
}
