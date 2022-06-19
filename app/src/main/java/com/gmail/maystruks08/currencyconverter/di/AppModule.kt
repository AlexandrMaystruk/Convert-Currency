package com.gmail.maystruks08.currencyconverter.di

import android.content.Context
import androidx.room.Room
import com.gmail.maystruks08.currencyconverter.data.memory.AppDatabase
import com.gmail.maystruks08.currencyconverter.data.memory.dao.CurrencyDao
import com.gmail.maystruks08.currencyconverter.data.memory.dao.FavoritesDao
import com.gmail.maystruks08.currencyconverter.data.network.CurrencyApiService
import com.gmail.maystruks08.currencyconverter.data.network.HttpRoutes
import com.gmail.maystruks08.currencyconverter.data.network.NetworkUtil
import com.gmail.maystruks08.currencyconverter.data.repositories.AppCoroutineDispatchersImpl
import com.gmail.maystruks08.currencyconverter.domain.AppDispatchers
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    companion object {

        private const val TIME_OUT = 10000L

        @Provides
        @Singleton
        fun provideWeatherApi(retrofit: Retrofit): CurrencyApiService {
            return retrofit.create(CurrencyApiService::class.java)
        }

        @Provides
        @Singleton
        fun provideRetrofitInterface(client: OkHttpClient): Retrofit {
            val contentType = "application/json".toMediaType()
            return Retrofit.Builder()
                .baseUrl(HttpRoutes.BASE_URL)
                .client(client)
                .addConverterFactory(Json.asConverterFactory(contentType))
                .build()
        }

        @Provides
        @Singleton
        fun provideOkHttpClient(): OkHttpClient {
            return OkHttpClient.Builder()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS).build()
        }

        @Provides
        @Singleton
        fun provideNetworkUtil(@ApplicationContext appContext: Context): NetworkUtil {
            return NetworkUtil(appContext)
        }

        @Provides
        @Singleton
        fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
            Room.databaseBuilder(context, AppDatabase::class.java, "currency_converter_db_v1")
                .build()

        @Provides
        @Singleton
        fun provideCurrencyDao(appDatabase: AppDatabase): CurrencyDao = appDatabase.currencyDao()

        @Provides
        @Singleton
        fun provideFavoritesDao(appDatabase: AppDatabase): FavoritesDao = appDatabase.favoritesDao()

    }

    @Binds
    @Singleton
    abstract fun bindDispatchers(impl: AppCoroutineDispatchersImpl): AppDispatchers

}