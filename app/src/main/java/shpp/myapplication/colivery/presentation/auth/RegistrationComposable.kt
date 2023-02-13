package shpp.myapplication.colivery.presentation.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import shpp.myapplication.colivery.R
import shpp.myapplication.colivery.utils.Semantics

@Composable
fun RegistrationComposable(
    email: String?,
    password: String?,
    viewModel: RegistrationViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(horizontal = 8.dp)
            .semantics { contentDescription = Semantics.REGISTRATION_COMPOSABLE },
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            NicknameTextField(
                nickname = viewModel.nicknameValidator.input.value,
                error = viewModel.nicknameValidator.error.value,
                onValueChange = { viewModel.nicknameValidator.onInputChange(it) },
                onFocus = { viewModel.nicknameValidator.onFocus() },
                onUnfocus = { viewModel.nicknameValidator.onUnfocus() }
            )
            TelegramTextField(
                telegram = viewModel.telegramValidator.input.value,
                error = viewModel.telegramValidator.error.value,
                onValueChange = { viewModel.telegramValidator.onInputChange(it) },
                onFocus = { viewModel.telegramValidator.onFocus() },
                onUnfocus = { viewModel.telegramValidator.onUnfocus() }
            )
        }
        Button(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .semantics { contentDescription = "completeButton" },
            onClick = {
                viewModel.signUp(email, password)
            }) {
            Text(text = stringResource(id = R.string.registrationCompleteButtonText))
        }
    }
}

@Composable
fun TelegramTextField(
    telegram: String,
    error: Boolean,
    onValueChange: (String) -> Unit,
    onFocus: () -> Unit,
    onUnfocus: () -> Unit
) {
    Column {
        OutlinedTextField(
            value = telegram,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .semantics { contentDescription = "telegram" }
                .onFocusChanged {
                    if (it.isFocused) {
                        onFocus()
                    }
                    if (!it.hasFocus) {
                        onUnfocus()
                    }
                },
            leadingIcon = {
                Image(
                    painter = painterResource(id = R.drawable.ic_telegram),
                    contentDescription = null
                )
            },
            label = { Text(text = stringResource(id = R.string.telegramHint)) },
            isError = error
        )

        if (error) {
            Text(
                text = stringResource(id = R.string.telegramNickEmptyError),
                color = MaterialTheme.colors.error,
                modifier = Modifier.semantics { contentDescription = "telegramError" })
        }
    }
}

@Composable
fun NicknameTextField(
    nickname: String,
    error: Boolean,
    onValueChange: (String) -> Unit,
    onFocus: () -> Unit,
    onUnfocus: () -> Unit
) {
    Column {
        OutlinedTextField(
            value = nickname,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .semantics { contentDescription = "nickname" }
                .onFocusChanged {
                    if (it.isFocused) {
                        onFocus()
                    }
                    if (!it.hasFocus) {
                        onUnfocus()
                    }
                },
            label = { Text(text = stringResource(id = R.string.nicknameHint)) },
            isError = error
        )

        if (error) {
            Text(
                text = stringResource(id = R.string.nicknameEmptyError),
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