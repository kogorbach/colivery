package shpp.myapplication.colivery.repository

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import org.mockito.Mockito.mock
import shpp.myapplication.colivery.data.network.Response
import shpp.myapplication.colivery.data.network.UserModel
import shpp.myapplication.colivery.domain.repo.FirebaseRepository

//class FirebaseAuthMockRepository: FirebaseRepository {
//    private val auth = mock(FirebaseAuth::class.java)
//
//    override suspend fun signIn(email: String, password: String): Flow<Response<Boolean>> {
//        whenever(auth.signInWithEmailAndPassword(email, password)).thenReturn(mock())
//
//        // Return a fake user for testing purposes
//        return Response.Success(true)
//    }
//
//    override suspend fun signUp(email: String, password: String, user: UserModel): Flow<Response<Boolean>> {
//        whenever(auth.createUserWithEmailAndPassword(email, password)).thenReturn(mock())
//
//        // Return a fake user for testing purposes
//        return Response.Success(true)
//    }
//}