package com.example.scalablecapital.application

import com.example.scalablecapital.BuildConfig
import com.example.scalablecapital.data.network.GitHubApiService
import com.example.scalablecapital.data.network.GitHubApiServiceRx
import com.example.scalablecapital.data.network.HeaderInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        val okHttpClient = if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            OkHttpClient.Builder()
                .addInterceptor(HeaderInterceptor())
                .addInterceptor(loggingInterceptor)
                .build()
        } else {
            OkHttpClient
                .Builder()
                .build()
        }
        return Retrofit.Builder()
            .baseUrl("https:///api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): GitHubApiService = retrofit.create(GitHubApiService::class.java)

    @Provides
    @Singleton
    fun provideRxApiService(retrofit: Retrofit): GitHubApiServiceRx = retrofit.create(GitHubApiServiceRx::class.java)
}