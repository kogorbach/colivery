package shpp.myapplication.colivery.data

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import shpp.myapplication.colivery.domain.repo.FirebaseRepository
import shpp.myapplication.colivery.utils.ext.usersCollection
import javax.inject.Inject

class FirebaseRepositoryImpl @Inject constructor(): FirebaseRepository {

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    override fun signIn(email: String, password: String, onSuccess: (Task<AuthResult>) -> Unit, onFailure: () -> Unit) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            onSuccess(it)
        }
    }

    override fun signUp(email: String, password: String, user: UserModel, onSuccess: () -> Unit, onFailure: () -> Unit) {
        auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
            firestore.usersCollection().add(user).addOnSuccessListener {

            }
        }
    }
}