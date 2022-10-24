package shpp.myapplication.colivery.presentation.auth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import shpp.myapplication.colivery.presentation.ui.theme.ColiveryTheme
import shpp.myapplication.colivery.utils.Semantics

@AndroidEntryPoint
class AuthActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ColiveryTheme {
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Semantics.AUTH_COMPOSABLE) {
                    composable(Semantics.AUTH_COMPOSABLE) {
                        AuthComposable()
                    }
                    composable(
                        "registrationScreen/{email}/{password}",
                        arguments = listOf(navArgument("email") { type = NavType.StringType },
                            navArgument("password") { type = NavType.StringType })
                    ) {
                        RegistrationComposable(
                            it.arguments?.getString("email"), it.arguments?.getString("password")
                        )
                    }
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        ColiveryTheme {
            AuthComposable()
        }
    }
}
