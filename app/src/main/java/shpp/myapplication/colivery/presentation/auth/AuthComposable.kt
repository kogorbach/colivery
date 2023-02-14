package shpp.myapplication.colivery.presentation.auth

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import shpp.myapplication.colivery.R
import shpp.myapplication.colivery.utils.EmailValidator
import shpp.myapplication.colivery.utils.InputValidator
import shpp.myapplication.colivery.utils.PasswordValidator
import shpp.myapplication.colivery.utils.Semantics

//stateful composable
@Composable
fun AuthComposable(
    onNavigateToRegistration: (String, String) -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    AuthComposable(
        emailValidator = viewModel.emailValidator,
        passwordValidator = viewModel.passwordValidator,
        authState = viewModel.state,
        onAuthButtonClick = {
            focusManager.clearFocus()
            onAuthButtonClick(
                viewModel = viewModel,
                onNavigateToRegistration = onNavigateToRegistration
            )
        },
        changeState = viewModel::changeState,
        isLoading = viewModel.loadingState == AuthViewModel.SignInEvent.LOADING
    )
}

//stateless composable
@Composable
fun AuthComposable(
    authState: AuthState,
    emailValidator: InputValidator = EmailValidator(),
    passwordValidator: InputValidator = PasswordValidator(),
    onAuthButtonClick: () -> Unit = {
        emailValidator.validate()
        passwordValidator.validate()
    },
    changeState: () -> Unit = {},
    signInWithGoogle: () -> Unit = {},
    isLoading: Boolean = false
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(visible = isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .semantics { contentDescription = Semantics.SIGN_IN_PROGRESS }
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(id = R.dimen.spacerNormal))
                .semantics { contentDescription = Semantics.AUTH_COMPOSABLE },
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                AuthTextField(
                    input = emailValidator.input,
                    isError = emailValidator.error,
                    onChange = { emailValidator.onInputChange(it) },
                    onUnfocus = { emailValidator.onUnfocus() },
                    onFocus = { emailValidator.onFocus() },
                    label = stringResource(R.string.emailInputLabel),
                    modifier = Modifier.fillMaxWidth()
                )
                AuthTextField(
                    input = passwordValidator.input,
                    isError = passwordValidator.error,
                    onChange = { passwordValidator.onInputChange(it) },
                    onUnfocus = { passwordValidator.onUnfocus() },
                    onFocus = { passwordValidator.onFocus() },
                    label = stringResource(R.string.passwordInputLabel),
                    modifier = Modifier.fillMaxWidth()
                )
                NormalSpacer()
                AuthButton(
                    authState = authState,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                    onClick = {
                        onAuthButtonClick()
                    }
                )
                NormalSpacer()
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = stringResource(R.string.authOrText)
                )
                NormalSpacer()
                GoogleAuthButton(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    onAuthClick = {
                        signInWithGoogle()
                    })
            }
            ChangeAuthActionText(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .semantics { contentDescription = Semantics.AUTH_CHANGE_OPTION }
                    .padding(dimensionResource(id = R.dimen.spacerNormal)),
                onClick = {
                    changeState()
                },
                state = authState
            )
        }
    }
}

@Composable
private fun NormalSpacer() {
    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacerNormal)))
}

@Composable
private fun AuthTextField(
    input: String,
    isError: Boolean,
    onChange: (String) -> Unit,
    onUnfocus: () -> Unit,
    onFocus: () -> Unit,
    modifier: Modifier,
    label: String,
) {

    Column(modifier = modifier) {
        OutlinedTextField(
            value = input,
            modifier = modifier
                .semantics { contentDescription = "$label input" }
                .onFocusChanged {
                    if (it.isFocused) {
                        onFocus()
                    }
                    if (!it.hasFocus) {
                        onUnfocus()
                    }
                },
            onValueChange = onChange,
            label = { Text(text = label) },
            isError = isError
        )

        if (isError) {
            Text(
                text = stringResource(R.string.authInvalidInput, label),
                color = MaterialTheme.colors.error,
                modifier = Modifier.semantics { contentDescription = "$label error" })
        }
    }
}

@Composable
private fun AuthButton(
    authState: AuthState,
    modifier: Modifier,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier.semantics { contentDescription = Semantics.AUTH_BUTTON },
        onClick = {
            onClick()
        }
    ) {
        Text(stringResource(id = authState.authAction))
    }
}


@Composable
private fun ChangeAuthActionText(
    modifier: Modifier,
    onClick: () -> Unit,
    state: AuthState
) {
    ClickableText(
        style = TextStyle(
            fontSize = 16.sp
        ),
        modifier = modifier,
        text = AnnotatedString(
            stringResource(
                id = state.changeAuthAction
            )
        ),
        onClick = {
            onClick()
        })
}

@Composable
private fun GoogleAuthButton(modifier: Modifier, onAuthClick: () -> Unit) {
    Button(
        modifier = modifier,
        onClick = {
            onAuthClick()
        }
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_google),
            contentDescription = Semantics.GOOGLE_AUTH,
            Modifier.padding(end = 8.dp)
        )
        Text(text = stringResource(R.string.googleAuthText))
    }
}

private fun onAuthButtonClick(
    viewModel: AuthViewModel,
    onNavigateToRegistration: (String, String) -> Unit
) {
    when (viewModel.state) {
        AuthState.SIGN_UP -> {
            if (viewModel.validate()) {
                onNavigateToRegistration(
                    viewModel.emailValidator.input,
                    viewModel.passwordValidator.input
                )
            }
        }
        AuthState.SIGN_IN -> {
            viewModel.signIn()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAuth() {
    AuthComposable(
        authState = AuthState.SIGN_UP
    )
}