package shpp.myapplication.colivery.presentation.auth

import android.content.Intent
import android.widget.Toast
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import shpp.myapplication.colivery.R
import shpp.myapplication.colivery.presentation.MainActivity

@Composable
fun AuthComposable(
    viewModel: AuthViewModel = viewModel(),
    navController: NavController = rememberNavController()
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            AuthTextField(
                input = viewModel.emailValidator.input.value,
                error = viewModel.emailValidator.error.value,
                onChange = { viewModel.emailValidator.onInputChange(it) },
                onUnfocus = { viewModel.emailValidator.onUnfocus() },
                onFocus = { viewModel.emailValidator.onFocus() },
                label = "email",
                modifier = Modifier.fillMaxWidth()
            )
            AuthTextField(
                input = viewModel.passwordValidator.input.value,
                error = viewModel.passwordValidator.error.value,
                onChange = { viewModel.passwordValidator.onInputChange(it) },
                onUnfocus = { viewModel.passwordValidator.onUnfocus() },
                onFocus = { viewModel.passwordValidator.onFocus() },
                label = "password",
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            AuthButton(
                authState = viewModel.state,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                onClick = {
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
                                Toast.makeText(context, "Authentication failed", Toast.LENGTH_SHORT)
                                    .show()
                            })
                    }
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = "OR"
            )
            Spacer(modifier = Modifier.height(8.dp))
            GoogleAuthButton(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onAuthClick = {
                    viewModel.signInWithGoogle()
                })
        }
        ChangeAuthActionText(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .semantics { contentDescription = "change auth action" }
                .padding(8.dp),
            onClick = {
                viewModel.changeState()
            },
            state = viewModel.state
        )
    }
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
                text = "invalid $label",
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
        modifier = modifier.semantics { contentDescription = "auth action button" },
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
            contentDescription = "Google auth"
        )
        Text(text = "Sign in with Google")
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAuth() {
    AuthComposable()
}