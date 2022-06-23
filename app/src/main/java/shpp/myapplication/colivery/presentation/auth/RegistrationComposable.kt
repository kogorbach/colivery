package shpp.myapplication.colivery.presentation.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
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
            .semantics { contentDescription = "registration screen" },
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            val nickname by remember { viewModel.nicknameState }
            NicknameTextField(
                nickname = nickname,
                onValueChange = { viewModel.onNickNameChange(it) }
            )
            TelegramTextField(
                telegram = viewModel.telegramLiveData.observeAsState(),
                error = viewModel.telegramError.observeAsState(),
                onValueChange = { viewModel.onTelegramChange(it) }
            )
        }
        Button(modifier = Modifier.align(Alignment.CenterHorizontally), onClick = {
            viewModel.signUp(email, password)
        }) {
            Text(text = "Complete")
        }
    }
}

@Composable
fun TelegramTextField(
    telegram: State<String?>,
    error: State<String?>,
    onValueChange: (String) -> Unit
) {
    Column {
        OutlinedTextField(
            value = telegram.value ?: "",
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .semantics { contentDescription = "telegram" },
            leadingIcon = {
                Image(
                    painter = painterResource(id = R.drawable.ic_telegram),
                    contentDescription = null
                )
            },
            label = { Text(text = "Telegram nickname") },
            isError = error.value != null
        )

        if (error.value != null) {
            Text(
                text = error.value!!,
                color = MaterialTheme.colors.error,
                modifier = Modifier.semantics { contentDescription = "telegramError" })
        }
    }
}

@Composable
fun NicknameTextField(nickname: String, onValueChange: (String) -> Unit) {
    Column {
        OutlinedTextField(
            value = nickname,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .semantics { contentDescription = "nickname" },
            label = { Text(text = "Nickname") },
            isError = nickname.isEmpty()
        )

        if (nickname.isEmpty()) {
            Text(
                text = "nickname cannot be empty",
                color = MaterialTheme.colors.error,
                modifier = Modifier.semantics { contentDescription = "nicknameError" }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegistrationComposablePreview() {
    RegistrationComposable("email", "password")
}