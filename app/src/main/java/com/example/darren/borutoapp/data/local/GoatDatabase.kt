package com.example.darren.borutoapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.darren.borutoapp.data.local.dao.GoatDao
import com.example.darren.borutoapp.data.local.dao.GoatRemoteKeysDao
import com.example.darren.borutoapp.data.local.dao.HeroDao
import com.example.darren.borutoapp.data.local.dao.HeroRemoteKeysDao
import com.example.darren.borutoapp.domain.model.Goat
import com.example.darren.borutoapp.domain.model.GoatRemoteKeys
import com.example.darren.borutoapp.domain.model.Hero
import com.example.darren.borutoapp.domain.model.HeroRemoteKeys


@Database(entities = [Goat::class, GoatRemoteKeys::class], version = 1)
@TypeConverters(DatabaseConverter::class)
abstract class GoatDatabase : RoomDatabase() {

    companion object{
        fun create(context: Context, useInMemory: Boolean): GoatDatabase{
            val databaseBuilder = if (useInMemory){
                Room.inMemoryDatabaseBuilder(context, GoatDatabase::class.java)
            } else{
                Room.databaseBuilder(context, GoatDatabase::class.java, "test_goat_database.db")
            }
            return databaseBuilder
                .fallbackToDestructiveMigration()
                .build()
        }
    }

    abstract fun goatDao(): GoatDao
    abstract fun goatRemoteKeysDao(): GoatRemoteKeysDao
}