package com.example.kushal_demo.di

import android.content.Context
import androidx.room.Room
import com.example.kushal_demo.data.api.PortfolioAPIService
import com.example.kushal_demo.data.localDB.HoldingDao
import com.example.kushal_demo.data.localDB.PortfolioDatabase
import com.example.kushal_demo.data.repo.PortfolioRepositoryImpl
import com.example.kushal_demo.domain.repo.PortfolioRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideBaseUrl() = "https://35dee773a9ec441e9f38d5fc249406ce.api.mockbin.io/"

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: String): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): PortfolioAPIService = retrofit.create(PortfolioAPIService::class.java)

    @Provides
    @Singleton
    fun provideRoom(@ApplicationContext context: Context): PortfolioDatabase =
        Room.databaseBuilder(context, PortfolioDatabase::class.java, "portfolio_db").build()

    @Provides
    fun provideDao(db: PortfolioDatabase): HoldingDao = db.holdingDao()

    @Provides
    fun provideRepository(api: PortfolioAPIService, dao: HoldingDao): PortfolioRepository =
        PortfolioRepositoryImpl(api, dao)
}
