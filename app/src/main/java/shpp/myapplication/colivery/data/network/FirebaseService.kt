package shpp.myapplication.colivery.data.network

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import shpp.myapplication.colivery.data.UserModel
import shpp.myapplication.colivery.utils.ext.usersCollection
import javax.inject.Inject

class FirebaseService @Inject constructor() {

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    fun signIn(email: String, password: String, onSuccess: (Task<AuthResult>) -> Unit) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            onSuccess(it)
        }
    }

    fun signUp(email: String, password: String, user: UserModel, onSuccess: () -> Unit) {
        auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
            firestore.usersCollection().add(user).addOnSuccessListener {

            }
        }
    }
}