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
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import shpp.myapplication.colivery.R

@Composable
fun RegistrationComposable(email: String?, password: String?) {
    val viewModel: RegistrationViewModel = viewModel()
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(horizontal = 8.dp)
            .semantics {contentDescription = "registration screen"},
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            NicknameTextField { viewModel.nickname = it }
            TelegramTextField { viewModel.telegram = it }
        }
        Button(modifier = Modifier.align(Alignment.CenterHorizontally), onClick = {
            viewModel.signUp(email, password)
        }) {
            Text(text = "Complete")
        }
    }
}

@Composable
fun TelegramTextField(onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = "",
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        leadingIcon = {
            Image(
                painter = painterResource(id = R.drawable.ic_telegram),
                contentDescription = null
            )
        },
        label = { Text(text = "telegram nickname") })
}

@Composable
fun NicknameTextField(onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = "",
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = "Nickname") })
}

@Preview(showBackground = true)
@Composable
fun RegistrationComposablePreview() {
    RegistrationComposable("email", "password")
}