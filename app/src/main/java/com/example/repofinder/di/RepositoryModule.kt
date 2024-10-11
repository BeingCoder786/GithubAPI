package com.example.repofinder.di

import android.content.Context

import com.example.githubrepofinder.repo.SearchRepository
import com.example.githubrepofinder.repo.SearchRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindSearchRepositoryImpl(searchRepositoryImpl: SearchRepositoryImpl): SearchRepository

}