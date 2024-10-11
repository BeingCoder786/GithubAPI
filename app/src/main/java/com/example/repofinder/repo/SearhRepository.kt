package com.example.githubrepofinder.repo

import androidx.paging.PagingData
import com.example.repofinder.model.Repository
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun searchRepositories(searchQuery: String): Flow<PagingData<Repository>>
}
