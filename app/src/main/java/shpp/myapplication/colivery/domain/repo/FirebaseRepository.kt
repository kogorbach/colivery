package shpp.myapplication.colivery.domain.repo

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import shpp.myapplication.colivery.data.network.Response
import shpp.myapplication.colivery.data.network.UserModel

typealias Credentials = Pair<String, String>

interface FirebaseRepository {
    suspend fun signIn(
        email: String,
        password: String,
        onSuccess: (Task<AuthResult>) -> Unit,
        onFailure: (String) -> Unit
    ): Response<Boolean>

    suspend fun signUp(
        email: String,
        password: String,
        user: UserModel,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    )
}