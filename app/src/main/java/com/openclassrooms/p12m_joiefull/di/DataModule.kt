package com.openclassrooms.p12m_joiefull.di

import com.openclassrooms.p12m_joiefull.data.network.ItemsClient
import com.openclassrooms.p12m_joiefull.data.repository.ItemsRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val dataModule: Module = module {

    /**
     * Provides an [OkHttpClient] instance with logging capabilities for network requests.
     */
    single {
        OkHttpClient.Builder().apply {
            addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        }.build()
    }

    /**
     * Provides a [Moshi] instance for JSON serialization/deserialization.
     */
    single {
        Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    }

    /**
     * Provides a [Retrofit] instance configured with the base URL, [Moshi] converter, and [OkHttpClient].
     */
    single {
        Retrofit.Builder()
            .baseUrl("https://raw.githubusercontent.com/OpenClassrooms-Student-Center/D-velopper-une-interface-accessible-en-Jetpack-Compose/main/api/")
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .client(get())
            .build()
    }

    /**
     * Provides an [ItemsClient] instance created from [Retrofit].
     */
    single {
        get<Retrofit>().create(ItemsClient::class.java)
    }

    /**
     * Provides an [ItemsRepository] instance with dependency on [ItemsClient].
     */
    single { ItemsRepository(get()) }
}