package shpp.myapplication.colivery.presentation.main

import SpacesComposable
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import shpp.myapplication.colivery.presentation.ui.theme.ColiveryTheme
import shpp.myapplication.colivery.utils.Semantics

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ColiveryTheme {
                val navController = rememberNavController()
//                NavHost(navController, graph = startDestination = SpacesList)
                MainContent()
            }
        }
    }

    @Composable
    fun MainContent() {
        SpacesComposable()
    }
}