package com.ao.rickandmortyapp.repository

import android.util.Log
import com.ao.rickandmortyapp.model.CharacterModel
import com.ao.rickandmortyapp.model.Result
import com.ao.rickandmortyapp.model.UiState
import com.ao.rickandmortyapp.retrofit_config.RetrofitConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CharacterRepository {
    fun fetchCharacters(page:Int = 1): Flow<UiState<Result>> {
        return flow {
            if(page == 1) emit(UiState.loading())
            try {
                val characters = RetrofitConfig.characterService.getCharacters(page = page)
                Log.e("CHARACTERS", characters.results.map { it.id }.toString())
                emit(UiState.success(characters))
            } catch (e: Exception) {
                emit(UiState.error(e.message ?: "An error occurred"))
            }
        }.flowOn(Dispatchers.IO).catch {
            emit(UiState.error("An error occurred while fetching characters data ${it.message}"))
        }
    }
}