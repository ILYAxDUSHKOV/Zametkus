package com.example.zametkus.di

import android.app.Application
import android.content.Context
import com.example.zametkus.data.zamDatabase.ZamDao
import com.example.zametkus.data.zamDatabase.ZamDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule /*(val application: Application)*/ { //Здесь был object. Я поменял когда смотрел видео про рум и дагер на класс

    val application: Application = Application()

    @Singleton
    @Provides
    fun getZamDao(zamDatabase: ZamDatabase): ZamDao {
        return zamDatabase.dao()
    }

    @Singleton
    @Provides
    fun getRoomDatabase(@ApplicationContext appContext: Context): ZamDatabase {
        return ZamDatabase.getDatabase(appContext)
    }

    /*@Singleton
    @Provides
    fun provideAppContext(): Context {
        return application.applicationContext
    }*/

}