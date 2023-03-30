package com.prateek.mycbseguide.di

import com.prateek.mycbseguide.api.MyCbseGuideApi
import com.prateek.mycbseguide.constants.ApiConstants.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    //to provide retrofit builder
    @Provides
    @Singleton
    fun getRetrofitBuilder(): Retrofit.Builder = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())

    //to provide API Interface
    @Provides
    @Singleton
    fun provideApi(builder: Retrofit.Builder): MyCbseGuideApi {
        return builder
            .build()
            .create(MyCbseGuideApi::class.java)
    }


}