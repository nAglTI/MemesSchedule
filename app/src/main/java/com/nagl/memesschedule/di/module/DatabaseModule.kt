package com.nagl.memesschedule.di.module

import android.content.Context
import androidx.room.Room
import com.nagl.memesschedule.data.source.db.LocalDatabase
import com.nagl.memesschedule.data.source.db.dao.ScheduleDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(context: Context): LocalDatabase {
        return Room.databaseBuilder(
            context,
            LocalDatabase::class.java, "Memes.db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideWeatherDao(database: LocalDatabase): ScheduleDao {
        return database.scheduleDao
    }
}