package shpp.myapplication.colivery.domain.repo

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import shpp.myapplication.colivery.data.UserModel

interface FirebaseRepository {
    fun signIn(
        email: String,
        password: String,
        onSuccess: (Task<AuthResult>) -> Unit,
        onFailure: () -> Unit = {}
    )

    fun signUp(
        email: String,
        password: String,
        user: UserModel,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    )
}