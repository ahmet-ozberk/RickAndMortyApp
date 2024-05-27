package com.ao.rickandmortyapp.views.home

import androidx.compose.runtime.MutableState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ao.rickandmortyapp.model.CharacterModel
import com.ao.rickandmortyapp.model.Result
import com.ao.rickandmortyapp.model.UiState
import com.ao.rickandmortyapp.repository.CharacterRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    init {
        getCharacter()
    }

    private val _characterData = MutableLiveData<UiState<Result>>()
    val characterData: LiveData<UiState<Result>> get() = _characterData
    var currentPage = 1


    private fun getCharacter() {
        val characterRepository = CharacterRepository()
        viewModelScope.launch {
            characterRepository.fetchCharacters().collect {
                _characterData.postValue(it)
            }
        }
    }

    fun getPageCharacters() {
        currentPage++
        val characterRepository = CharacterRepository()
        viewModelScope.launch {
            characterRepository.fetchCharacters(currentPage).collect { result ->
                if (result is UiState.Success) {
                    val newList = result.data.results
                    val oldList = (_characterData.value as UiState.Success).data.results
                    val updatedList = oldList + newList
                    delay(2000)
                    _characterData.postValue(UiState.Success(Result(updatedList)))
                } else {
                    _characterData.postValue(result)
                }
            }
        }
    }
}