package shpp.myapplication.colivery.presentation.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import shpp.myapplication.colivery.R

@Composable
fun RegistrationComposable(navController: NavController) {
    val viewModel: RegistrationViewModel = viewModel()
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(horizontal = 8.dp), verticalArrangement = Arrangement.SpaceBetween
    ) {
        NicknameTextField { viewModel.nickname = it }
        TelegramTextField { viewModel.telegram = it }
        Button(modifier = Modifier.align(Alignment.CenterHorizontally), onClick = {
            viewModel.completeRegistration()
        }) {
            Text(text = "Complete")
        }
    }
}

@Composable
fun TelegramTextField(onValueChange: (String) -> Unit) {
    Row(modifier = Modifier.fillMaxWidth()) {
//        TODO("change google icon with telegram")
        Image(
            painter = painterResource(id = R.drawable.ic_google),
            contentDescription = "Telegram icon"
        )
        OutlinedTextField(
            value = "",
            onValueChange = onValueChange,
            label = { Text(text = "telegram nickname") })
    }
}

@Composable
fun NicknameTextField(onValueChange: (String) -> Unit) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = "",
        onValueChange = onValueChange,
        label = { Text(text = "Nickname") })
}

@Preview(showBackground = true)
@Composable
fun RegistrationComposablePreview() {
    RegistrationComposable(rememberNavController())
}