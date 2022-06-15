package shpp.myapplication.colivery.presentation

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import shpp.myapplication.colivery.presentation.ui.theme.ColiveryTheme

@AndroidEntryPoint
class MainActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ColiveryTheme {
                val navController = rememberNavController()
//                NavHost(navController, graph = startDestination = SpacesList)
            }
        }
    }
}