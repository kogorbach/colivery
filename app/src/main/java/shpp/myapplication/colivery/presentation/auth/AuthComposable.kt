package shpp.myapplication.colivery.presentation.auth

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import shpp.myapplication.colivery.R
import shpp.myapplication.colivery.presentation.MainActivity
import shpp.myapplication.colivery.utils.InputValidator
import shpp.myapplication.colivery.utils.Semantics
import shpp.myapplication.colivery.utils.ext.toast

//stateful composable
@Composable
fun AuthComposable(
    viewModel: AuthViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val context = LocalContext.current

    AuthComposable(
        emailValidator = viewModel.emailValidator,
        passwordValidator = viewModel.passwordValidator,
        authState = viewModel.state,
        onAuthButtonClick = {
            onAuthButtonClick(
                viewModel = viewModel,
                navController = navController,
                context = context
            )
        },
        changeState = viewModel::changeState
    )
}

//stateless composable
@Composable
fun AuthComposable(
    authState: AuthViewModel.AuthState,
    emailValidator: InputValidator = InputValidator.mockValidator(),
    passwordValidator: InputValidator = InputValidator.mockValidator(),
    onAuthButtonClick: () -> Unit = {},
    changeState: () -> Unit = {},
    signInWithGoogle: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.spacerNormal))
            .semantics { contentDescription = Semantics.AUTH_COMPOSABLE },
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            AuthTextField(
                input = emailValidator.input.value,
                error = emailValidator.error.value,
                onChange = { emailValidator.onInputChange(it) },
                onUnfocus = { emailValidator.onUnfocus() },
                onFocus = { emailValidator.onFocus() },
                label = stringResource(R.string.emailInputLabel),
                modifier = Modifier.fillMaxWidth()
            )
            AuthTextField(
                input = passwordValidator.input.value,
                error = passwordValidator.error.value,
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

@Composable
private fun NormalSpacer() {
    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacerNormal)))
}

@Composable
private fun AuthTextField(
    input: String,
    error: Boolean,
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
            isError = error
        )

        if (error) {
            Text(
                text = stringResource(R.string.authInvalidInput, label),
                color = MaterialTheme.colors.error,
                modifier = Modifier.semantics { contentDescription = "$label error" })
        }
    }
}

@Composable
private fun AuthButton(
    authState: AuthViewModel.AuthState,
    modifier: Modifier,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier.semantics { contentDescription = Semantics.AUTH_BUTTON },
        onClick = {
            onClick()
        }
    ) {
        Text(authState.authAction)
    }
}


@Composable
private fun ChangeAuthActionText(
    modifier: Modifier,
    onClick: () -> Unit,
    state: AuthViewModel.AuthState
) {
    ClickableText(
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
            contentDescription = Semantics.GOOGLE_AUTH
        )
        Text(text = stringResource(R.string.googleAuthText))
    }
}

private fun onAuthButtonClick(
    viewModel: AuthViewModel,
    navController: NavController,
    context: Context
) {
    if (viewModel.state == AuthViewModel.AuthState.SIGN_UP) {
        if (viewModel.validate()) {
            navController.navigate("registrationScreen/${viewModel.emailValidator.input.value}/${viewModel.passwordValidator.input.value}")
        }
    } else {
        viewModel.signIn(
            onSuccess = {
                context.startActivity(Intent(context, MainActivity::class.java))
            },
            onFailure = {
                context.toast(R.string.authenticationFailedToast)
            })
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAuth() {
    AuthComposable(
       authState = AuthViewModel.AuthState.SIGN_UP
    )
}