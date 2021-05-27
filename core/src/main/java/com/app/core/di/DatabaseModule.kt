package com.app.core.di

import android.content.Context
import androidx.room.Room
import com.app.core.data.source.local.room.MovieDao
import com.app.core.data.source.local.room.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DatabaseModule {

    private const val PASSPHRASE_DATABASE = "ilham"

    @Provides
    @Named(PASSPHRASE_DATABASE)
    fun providePassPhraseDatabase(): String = PASSPHRASE_DATABASE

    @Singleton
    @Provides
    fun provideSupportFactory(
        @Named(PASSPHRASE_DATABASE) _passphrase: String
    ): SupportFactory {
        val passphrase: ByteArray = SQLiteDatabase.getBytes(_passphrase.toCharArray())
        return SupportFactory(passphrase)
    }

    @Singleton
    @Provides
    fun provideMovieDatabase(
        @ApplicationContext context: Context,
        supportFactory: SupportFactory
    ): MovieDatabase =
        Room.databaseBuilder(
            context,
            MovieDatabase::class.java,
            MovieDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .openHelperFactory(supportFactory)
            .build()

    @Singleton
    @Provides
    fun provideMovieDao(movieDatabase: MovieDatabase): MovieDao =
        movieDatabase.movieDao()

}