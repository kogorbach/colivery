package shpp.myapplication.colivery.presentation.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

@Composable
fun AuthComposable(
    navController: NavController = rememberNavController()
) {
    val viewModel = viewModel(AuthViewModel::class.java)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            EmailTextField(viewModel.email)
            PasswordTextField(viewModel.password)
            Spacer(modifier = Modifier.height(8.dp))
            AuthButton(
                viewModel.state,
                Modifier
                    .align(Alignment.CenterHorizontally),
                onClick = {
                    if (viewModel.state == AuthViewModel.AuthState.SIGN_UP) {
                        navController.navigate("registrationScreen/${viewModel.email.value}/${viewModel.password.value}")
                    } else {
                        // TODO intent to MainActivity [by ratrider:]
                    }
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(modifier = Modifier.align(Alignment.CenterHorizontally), text = "OR")
            Spacer(modifier = Modifier.height(8.dp))
            GoogleAuthButton(Modifier.align(Alignment.CenterHorizontally), onAuthClick = {
                viewModel.signInWithGoogle()
            })
        }
        ChangeAuthActionText(
            Modifier
                .align(Alignment.CenterHorizontally)
                .semantics { contentDescription = "change auth action" }
                .padding(8.dp), onClick = {
                viewModel.changeState()
            }, viewModel.state
        )
    }
}

@Composable
private fun EmailTextField(emailState: MutableState<String>) {
    var input by remember { emailState }
    var error = !android.util.Patterns.EMAIL_ADDRESS.matcher(input).matches()
    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = input,
            modifier = Modifier
                .fillMaxWidth()
                .semantics { contentDescription = "email input" },
            onValueChange = {
                input = it
                emailState.value = it
            },
            label = { Text(text = "Email") })
        if (error) {
            Text(
                text = "invalid email",
                color = MaterialTheme.colors.error,
                modifier = Modifier.semantics { contentDescription = "email error" })
        }
    }
}

@Composable
private fun PasswordTextField(passwordState: MutableState<String>) {
    var input by remember { passwordState }
    var error = input.length in 1 until AuthViewModel.PASSWORD_LENGTH
    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = input,
            modifier = Modifier
                .fillMaxWidth()
                .semantics { contentDescription = "password input" },
            onValueChange = {
                input = it
                passwordState.value = it
            },
            label = { Text(text = "Password") },
            isError = error
        )
        if (error) {
            Text(
                text = "invalid password",
                color = MaterialTheme.colors.error,
                modifier = Modifier.semantics { contentDescription = "password error" })
        }
    }
}

@Composable
private fun AuthButton(
    authState: AuthViewModel.AuthState,
    modifier: Modifier,
    onClick: () -> Unit
) {
    Button(modifier = modifier.semantics { contentDescription = "auth action button" }, onClick = {
        onClick()
    }) {
        Text(authState.text)
    }
}


@Composable
private fun ChangeAuthActionText(
    modifier: Modifier,
    onClick: () -> Unit,
    state: AuthViewModel.AuthState
) {
    ClickableText(modifier = modifier,
        text = AnnotatedString(
            stringResource(
                id = if (state == AuthViewModel.AuthState.SIGN_UP) {
                    R.string.alreadyHaveAccount
                } else {
                    R.string.newToTheApp
                }
            )
        ),
        onClick = {
            onClick()
        })
}

@Composable
private fun GoogleAuthButton(modifier: Modifier, onAuthClick: () -> Unit) {
    Button(modifier = modifier, onClick = {
        onAuthClick()
    }) {
        Image(
            painter = painterResource(id = R.drawable.ic_google), contentDescription = "Google auth"
        )
        Text(text = "Sign in with Google")
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAuth() {
    AuthComposable()
}