package shpp.myapplication.colivery.presentation.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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

    var email = ""
    var password = ""

    Column(Modifier.fillMaxSize()) {
        TextField(value = "email", modifier = Modifier.fillMaxWidth(), onValueChange = {
            email = it
        })
        TextField(value = "password", modifier = Modifier.fillMaxWidth(), onValueChange = {
            password = it
        })
        Spacer(modifier = Modifier.height(8.dp))
        AuthButton(
            email, password, viewModel.state, Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(8.dp))
        ChangeAuthActionText(
            Modifier
                .align(Alignment.End)
                .padding(8.dp), onClick = {
                viewModel.changeState()
            }, viewModel.state)
        Spacer(modifier = Modifier.height(8.dp))
        GoogleAuthButton(Modifier.align(Alignment.CenterHorizontally), onAuthClick = {
            viewModel.signInWithGoogle()
        })
    }
}

@Composable
private fun AuthButton(
    email: String,
    password: String,
    authState: AuthViewModel.AuthState,
    modifier: Modifier
) {
    Button(modifier = modifier, onClick = {
        auth(email, password)
    }) {
        Text(authState.text)
    }
}


@Composable
private fun ChangeAuthActionText(modifier: Modifier, onClick: () -> Unit, state: AuthViewModel.AuthState) {
    ClickableText(modifier = modifier,
        text = AnnotatedString(stringResource(id = if (state == AuthViewModel.AuthState.SIGN_UP) {
            R.string.alreadyHaveAccount
        } else {
            R.string.newToTheApp
        })),
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

private fun auth(email: String, password: String) {

}

@Preview
@Composable
fun PreviewAuth() {
    AuthComposable()
}