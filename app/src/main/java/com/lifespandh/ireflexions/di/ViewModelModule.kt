package com.lifespandh.ireflexions.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lifespandh.ireflexions.auth.AuthViewModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap

@Module
@InstallIn(SingletonComponent::class)
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    /**
     * Below is an example of how you can add a new instance of
     * viewmodel that you have created recently and still is not
     * present in the dependency injection hashmap
     */
    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    abstract fun authViewModel(authViewModel: AuthViewModel): ViewModel
}