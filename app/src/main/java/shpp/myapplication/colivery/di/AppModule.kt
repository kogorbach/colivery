package shpp.myapplication.colivery.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import shpp.myapplication.colivery.data.network.FirebaseService
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class)
abstract class AppModule {
    @Binds
    @Singleton
    abstract fun bindFirebase(
        firebaseService: FirebaseService
    ): FirebaseService
}