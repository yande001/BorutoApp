package com.example.darren.borutoapp.presentation.screens.search

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.darren.borutoapp.domain.model.Goat
import com.example.darren.borutoapp.domain.model.Hero
import com.example.darren.borutoapp.domain.use_cases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val useCases: UseCases
): ViewModel() {

    private val _searchQuery = mutableStateOf("")
    val searchQuery = _searchQuery

    private val _searchedHeroes = MutableStateFlow<PagingData<Hero>>(PagingData.empty())
    val searchedHeroes = _searchedHeroes

    private val _searchedGoats = MutableStateFlow<PagingData<Goat>>(PagingData.empty())
    val searchGoats = _searchedGoats

    fun updateSearchQuery(query: String){
        _searchQuery.value = query
    }

    fun searchHeroes(query: String){
        viewModelScope.launch(Dispatchers.IO) {
            useCases.searchHeroesUseCase(query = query).cachedIn(viewModelScope).collect{
                _searchedHeroes.value = it
            }
        }
    }

    fun searchGoats(query: String){
        viewModelScope.launch(Dispatchers.IO) {
            useCases.searchGoatsUseCase(query = query).cachedIn(viewModelScope).collect{
                _searchedGoats.value = it
            }
        }
    }
}