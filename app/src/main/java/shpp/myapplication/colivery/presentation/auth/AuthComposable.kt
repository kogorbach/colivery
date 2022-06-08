package shpp.myapplication.colivery.presentation.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import shpp.myapplication.colivery.R

@Composable
fun AuthComposable() {
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
        Button(modifier = Modifier.align(Alignment.CenterHorizontally), onClick = {
            auth(email, password)
        }) {
            Text("Sign up")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            modifier = Modifier
                .align(Alignment.End)
                .padding(end = 8.dp),
            text = stringResource(R.string.alreadyHaveAccount),
            color = colorResource(
                id =
                R.color.purple_500
            )
        )
    }
}

fun auth(email: String, password: String) {

}

@Preview
@Composable
fun PreviewAuth() {
    AuthComposable()
}