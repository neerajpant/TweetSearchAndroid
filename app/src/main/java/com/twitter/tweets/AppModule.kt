package com.twitter.tweets

import com.twitter.tweets.network.TwitterApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

   /* @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(UnsplashApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()*/
    @Provides
    @Singleton
    fun provideTwitterRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://6f8a2fec-1605-4dc7-a081-a8521fad389a.mock.pstmn.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideTwitterApi(retrofit: Retrofit): TwitterApi =
        retrofit.create(TwitterApi::class.java)

}