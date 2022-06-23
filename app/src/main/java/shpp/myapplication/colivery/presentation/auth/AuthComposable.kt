package shpp.myapplication.colivery.presentation.auth

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
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
    navController: NavController = rememberNavController()
) {
    val viewModel = viewModel(AuthViewModel::class.java)
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            EmailTextField(
                emailState = viewModel.emailLiveData.observeAsState(),
                errorState = viewModel.emailError.observeAsState(),
                modifier = Modifier.fillMaxWidth(),
                onChange = { viewModel.onEmailChange(it) }
            )
            PasswordTextField(
                passwordState = viewModel.passwordLiveData.observeAsState(),
                errorState = viewModel.passwordError.observeAsState(),
                onChange = { viewModel.onPasswordChange(it) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            AuthButton(
                authState = viewModel.state,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                onClick = {
                    if (viewModel.state == AuthViewModel.AuthState.SIGN_UP) {
                        navController.navigate("registrationScreen/${viewModel.emailLiveData.value}/${viewModel.passwordLiveData.value}")
                    } else {
                        context.startActivity(Intent(context, MainActivity::class.java))
                        // TODO add firebase authentication [by ratrider:]
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
private fun EmailTextField(
    emailState: State<String?>,
    errorState: State<Boolean?>,
    onChange: (String) -> Unit,
    modifier: Modifier
) {
    val input by remember { emailState }
    Column(modifier = modifier) {
        OutlinedTextField(
            value = input ?: "",
            modifier = modifier.semantics { contentDescription = "email input" },
            onValueChange = onChange,
            label = { Text(text = "Email") },
            isError = errorState.value ?: false
        )

        if (errorState.value == true) {
            Text(
                text = "invalid email",
                color = MaterialTheme.colors.error,
                modifier = Modifier.semantics { contentDescription = "email error" })
        }
    }
}

@Composable
private fun PasswordTextField(
    passwordState: State<String?>,
    errorState: State<Boolean?>,
    onChange: (String) -> Unit,
    modifier: Modifier
) {
    Column(modifier = modifier) {
        OutlinedTextField(
            value = passwordState.value ?: "",
            modifier = modifier.semantics { contentDescription = "password input" },
            onValueChange = onChange,
            label = { Text(text = "Password") },
            isError = errorState.value ?: false
        )
        if (errorState.value == true) {
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
    Button(
        modifier = modifier.semantics { contentDescription = "auth action button" },
        onClick = {
            onClick()
        }
    ) {
        Text(authState.text.authAction)
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
                id = state.text.changeAuthAction
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