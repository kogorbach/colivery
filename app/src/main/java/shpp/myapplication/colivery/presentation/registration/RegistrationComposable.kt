package shpp.myapplication.colivery.presentation.registration

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
import shpp.myapplication.colivery.utils.InputValidator
import shpp.myapplication.colivery.utils.NicknameValidator
import shpp.myapplication.colivery.utils.Semantics
import shpp.myapplication.colivery.utils.TelegramValidator

//stateful
@Composable
fun RegistrationComposable(
    email: String?,
    password: String?,
    viewModel: RegistrationViewModel = hiltViewModel()
) {
    RegistrationComposable(
        nicknameValidator = viewModel.nicknameValidator,
        telegramValidator = viewModel.telegramValidator,
        onComplete = { viewModel.signUp(email, password) }
    )
}

//stateless
@Composable
fun RegistrationComposable(
    nicknameValidator: NicknameValidator = NicknameValidator(),
    telegramValidator: TelegramValidator = TelegramValidator(),
    onComplete: () -> Unit = {}
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
                inputValidator = nicknameValidator
            )
            TelegramTextField(
                inputValidator = telegramValidator
            )
        }
        Button(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .semantics { contentDescription = Semantics.COMPLETE_BUTTON },
            onClick = {
                onComplete()
            }) {
            Text(text = stringResource(id = R.string.registrationCompleteButtonText))
        }
    }

}

@Composable
fun NicknameTextField(
    inputValidator: InputValidator
) {
    inputValidator.run {
        Column {
            OutlinedTextField(
                value = input,
                onValueChange = { onInputChange(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .semantics { contentDescription = Semantics.NICKNAME_INPUT }
                    .onFocusChanged {
                        onFocusChange(it)
                    },
                label = { Text(text = stringResource(id = R.string.nicknameHint)) },
                isError = error
            )

            if (error) {
                Text(
                    text = stringResource(id = R.string.nicknameEmptyError),
                    color = MaterialTheme.colors.error,
                    modifier = Modifier.semantics { contentDescription = Semantics.NICKNAME_ERROR }
                )
            }
        }
    }
}

@Composable
fun TelegramTextField(
    inputValidator: TelegramValidator
) {
    inputValidator.run {
        Column {
            OutlinedTextField(
                value = input,
                onValueChange = { onInputChange(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .semantics { contentDescription = Semantics.TELEGRAM_INPUT }
                    .onFocusChanged {
                        onFocusChange(it)
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
                    modifier = Modifier.semantics { contentDescription = Semantics.TELEGRAM_ERROR })
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegistrationComposablePreview() {
    RegistrationComposable("email", "password")
}