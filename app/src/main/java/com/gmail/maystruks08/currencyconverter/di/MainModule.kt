package com.gmail.maystruks08.currencyconverter.di

import com.gmail.maystruks08.currencyconverter.data.repositories.RepositoryImpl
import com.gmail.maystruks08.currencyconverter.domain.repositories.Repository
import com.gmail.maystruks08.currencyconverter.domain.usecases.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class MainModule {

    @Binds
    @ActivityRetainedScoped
    abstract fun bindRepository(impl: RepositoryImpl): Repository

    @Binds
    @ActivityRetainedScoped
    abstract fun bindAddCurrencyToFavorite(impl: AddCurrencyToFavoriteImpl): AddCurrencyToFavorite

    @Binds
    @ActivityRetainedScoped
    abstract fun bindRemoveCurrencyFromFavorite(impl: RemoveCurrencyFromFavoriteImpl): RemoveCurrencyFromFavorite

    @Binds
    @ActivityRetainedScoped
    abstract fun bindFetchAllCurrencies(impl: FetchAllCurrenciesUseCaseImpl): FetchCurrenciesUseCase

    @Binds
    @ActivityRetainedScoped
    abstract fun bindConvertCurrencyUseCase(impl: ConvertCurrencyUseCaseImpl): ConvertCurrencyUseCase


}