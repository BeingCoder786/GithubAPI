package com.example.githubrepofinder.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.githubrepofinder.repo.SearchRepository
import com.example.repofinder.model.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
        private val searchRepository: SearchRepository,
    ) : ViewModel() {
        private val _searchQuery = MutableStateFlow("")

        @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
        val repositories: Flow<PagingData<Repository>> =
            _searchQuery
                .debounce(500)
                .distinctUntilChanged()
                .filter { it.isNotBlank() }
                .flatMapLatest { query ->
                    searchRepository
                        .searchRepositories(searchQuery = query)
                        .cachedIn(viewModelScope)
                }



        fun searchRepositories(searchQuery: String = "cache") {
            _searchQuery.value = searchQuery
        }
    }
