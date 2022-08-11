package com.example.currencyconverter.di

import com.example.currencyconverter.data.api.ApiService
import com.example.currencyconverter.data.repository.CurrencyRepositoryImpl
import com.example.currencyconverter.domain.repository.CurrencyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideDiscoverRepository(
        apiService: ApiService
    ): CurrencyRepository {
        return CurrencyRepositoryImpl(apiService)
    }
}
