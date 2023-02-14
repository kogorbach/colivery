package shpp.myapplication.colivery.data.network

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
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
        password: String
    ) =
        flow<Response<Boolean>> {
            emit(Response.Loading)
            auth.signInWithEmailAndPassword(email, password).await()
            Response.Success(true)
        }.catch {
            emit(Response.Failure(it.message ?: Constants.DEFAULT_NETWORK_ERROR))
        }

    override suspend fun signUp(
        email: String,
        password: String,
        user: UserModel,
    ) = flow {
        emit(Response.Loading)
        auth.createUserWithEmailAndPassword(email, password).await()
        firestore.usersCollection.add(user).await()
        auth.signInWithEmailAndPassword(email, password).await()
        emit(Response.Success(true))
    }.catch {
        emit(Response.Failure(it.message ?: Constants.DEFAULT_NETWORK_ERROR))
    }
}
