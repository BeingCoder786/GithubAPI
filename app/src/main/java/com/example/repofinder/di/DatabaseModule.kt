package com.example.repofinder.di

import android.content.Context
import com.example.repofinder.model.room.SearchDao
import com.example.repofinder.model.room.SearchDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideSearchDao(@ApplicationContext context: Context): SearchDao =
        SearchDatabase.getInstance(context).searchDao()
}