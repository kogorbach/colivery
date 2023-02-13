package shpp.myapplication.colivery.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import shpp.myapplication.colivery.data.network.FirebaseRepositoryImpl
import shpp.myapplication.colivery.domain.repo.FirebaseRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {

    @Binds
    abstract fun bindRepository(
        firebaseRepository: FirebaseRepositoryImpl
    ): FirebaseRepository

    @Provides
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()

    @Provides
    fun provideFirestore() = FirebaseFirestore.getInstance()
}