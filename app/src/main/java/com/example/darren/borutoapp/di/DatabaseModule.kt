package com.example.darren.borutoapp.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.darren.borutoapp.data.local.BorutoDatabase
import com.example.darren.borutoapp.data.local.GoatDatabase
import com.example.darren.borutoapp.data.repository.LocalDataSourceImpl
import com.example.darren.borutoapp.domain.repository.LocalDataSource
import com.example.darren.borutoapp.util.Constants.BORUTO_DATABASE
import com.example.darren.borutoapp.util.Constants.GOAT_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): BorutoDatabase{
       return Room.databaseBuilder(
            context,
            BorutoDatabase::class.java,
            BORUTO_DATABASE
        ).build()
    }

    @Provides
    @Singleton
    fun provideLocalDataSource(
        borutoDatabase: BorutoDatabase,
        goatDatabase: GoatDatabase
    ): LocalDataSource {
        return LocalDataSourceImpl(
            borutoDatabase = borutoDatabase,
            goatDatabase = goatDatabase
        )
    }

    @Provides
    @Singleton
    fun provideGoatDatabase(
        @ApplicationContext context: Context
    ): GoatDatabase {
        return Room.databaseBuilder(
            context,
            GoatDatabase::class.java,
            GOAT_DATABASE
        ).build()
    }

}