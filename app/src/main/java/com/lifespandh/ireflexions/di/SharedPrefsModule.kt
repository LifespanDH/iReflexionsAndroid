package com.lifespandh.ireflexions.di

import com.lifespandh.ireflexions.utils.sharedPrefs.SharedPrefs
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SharedPrefsModule {

    @Provides
    @Singleton
    fun provideSharedPrefs(): SharedPrefs {
        return SharedPrefs()
    }
}