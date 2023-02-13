package shpp.myapplication.colivery.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import shpp.myapplication.colivery.data.FirebaseRepositoryImpl
import shpp.myapplication.colivery.domain.repo.FirebaseRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class RepoModule {

    @Binds
    abstract fun bindRepository(
        firebaseRepository: FirebaseRepositoryImpl
    ): FirebaseRepository
}