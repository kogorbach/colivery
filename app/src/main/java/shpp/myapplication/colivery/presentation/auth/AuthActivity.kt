package shpp.myapplication.colivery.presentation.auth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import shpp.myapplication.colivery.presentation.ui.theme.ColiveryTheme

@AndroidEntryPoint
class AuthActivity : ComponentActivity() {

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ColiveryTheme {
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "authScreen") {
                    composable("authScreen") {
                        AuthComposable(navController, viewModel)
                    }
                    composable("registrationScreen") {
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
