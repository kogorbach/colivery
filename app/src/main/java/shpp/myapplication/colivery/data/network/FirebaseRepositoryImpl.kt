package shpp.myapplication.colivery.data.network

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import shpp.myapplication.colivery.domain.repo.FirebaseRepository
import shpp.myapplication.colivery.utils.Constants
import shpp.myapplication.colivery.utils.ext.usersCollection
import javax.inject.Inject

class FirebaseRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : FirebaseRepository {


    override suspend fun signIn(
        email: String,
        password: String,
        onSuccess: (Task<AuthResult>) -> Unit,
        onFailure: (String) -> Unit
    ) =
        try {
            auth.signInWithEmailAndPassword(email, password).await()
            Response.Success(true)
        } catch (exception: Exception) {
            Response.Failure(exception)
        }

    override suspend fun signUp(
        email: String,
        password: String,
        user: UserModel,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
            firestore.usersCollection().add(user).addOnSuccessListener {
                onSuccess()
            }.addOnFailureListener {
                onFailure(it.message ?: Constants.DEFAULT_NETWORK_ERROR)
            }
        }
    }
}