package shpp.myapplication.colivery.repository

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.mockito.Mockito.mock
import shpp.myapplication.colivery.data.network.Response
import shpp.myapplication.colivery.data.network.UserModel
import shpp.myapplication.colivery.domain.repo.FirebaseRepository
import shpp.myapplication.colivery.utils.Constants

class FirebaseAuthMockRepository : FirebaseRepository {
    private val auth = mock(FirebaseAuth::class.java)

    var shouldReturnNetworkError = false

    override suspend fun signIn(email: String, password: String): Flow<Response<Boolean>> {

        return flow {
            emit(Response.Loading)
            delay(500L)
            if (shouldReturnNetworkError) {
                emit(Response.Failure(message = Constants.DEFAULT_NETWORK_ERROR))
            } else {
                emit(Response.Success(true))
            }
        }
    }

    override suspend fun signUp(
        email: String,
        password: String,
        user: UserModel
    ): Flow<Response<Boolean>> {

        return flow {
            emit(Response.Loading)
            delay(500L)
            if (shouldReturnNetworkError) {
                emit(Response.Failure(message = Constants.DEFAULT_NETWORK_ERROR))
            } else {
                emit(Response.Success(true))
            }
        }
    }
}