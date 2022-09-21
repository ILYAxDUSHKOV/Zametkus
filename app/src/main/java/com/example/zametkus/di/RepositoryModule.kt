package com.example.zametkus.di

import com.example.zametkus.data.repository.RepositoryImpl
import com.example.zametkus.domain.repository.repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindZamRepository(
        repositoryImpl: RepositoryImpl
    ): repository
}