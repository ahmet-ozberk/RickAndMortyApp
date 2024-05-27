package com.ao.rickandmortyapp.services

import com.ao.rickandmortyapp.model.CharacterModel
import com.ao.rickandmortyapp.model.Result
import retrofit2.http.GET
import retrofit2.http.Query

interface CharacterServices {
    @GET("/api/character")
    suspend fun getCharacters(@Query("page") page: Int = 1): Result
}