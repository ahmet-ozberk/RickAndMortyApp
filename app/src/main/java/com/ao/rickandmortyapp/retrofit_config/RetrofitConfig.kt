package com.ao.rickandmortyapp.retrofit_config

import com.ao.rickandmortyapp.services.CharacterServices
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitConfig {
    private const val BASE_URL = "https://rickandmortyapi.com"

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val characterService: CharacterServices by lazy {
        retrofit.create(CharacterServices::class.java)
    }
}