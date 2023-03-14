package shpp.myapplication.colivery.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import shpp.myapplication.colivery.data.network.AuthRepositoryImpl
import shpp.myapplication.colivery.domain.repo.AuthRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {

    @Binds
    abstract fun bindRepository(
        authRepository: AuthRepositoryImpl
    ): AuthRepository
}