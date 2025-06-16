package cd.zgeniuscoders.floapp.di

import android.app.Application
import android.content.Context
import cd.zgeniuscoders.floapp.remote.AuthenticationService
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApplicationContext(application: Application): Context? {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideFirebaseFirestoreService(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideCategoryRepository(db: FirebaseFirestore): AuthenticationService {
        return AuthenticationService()
    }


}