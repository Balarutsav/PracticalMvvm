package com.example.myapplication.di

import android.content.Context
import com.example.myapplication.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {


    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) =
        AppDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideOrderDao(db: AppDatabase) = db.orderDao()


    @Singleton
    @Provides
    fun provideOrderProductDao(db: AppDatabase) = db.orderProductDao()
}