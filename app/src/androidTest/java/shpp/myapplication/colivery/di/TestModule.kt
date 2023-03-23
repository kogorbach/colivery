package shpp.myapplication.colivery.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import shpp.myapplication.colivery.domain.repo.AuthRepository
import shpp.myapplication.colivery.repository.MockAuthRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestModule {

    @Singleton
    @Provides
    fun provideRepository(): AuthRepository {
        return MockAuthRepository()
    }
}