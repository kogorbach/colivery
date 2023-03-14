package shpp.myapplication.colivery.domain.repo

import kotlinx.coroutines.flow.Flow
import shpp.myapplication.colivery.data.network.Response
import shpp.myapplication.colivery.data.network.UserModel

typealias Credentials = Pair<String, String>

interface AuthRepository {
    suspend fun signIn(
        email: String,
        password: String,
    ): Flow<Response<Boolean>>

    suspend fun signUp(
        email: String,
        password: String,
        user: UserModel,
    ): Flow<Response<Boolean>>
}