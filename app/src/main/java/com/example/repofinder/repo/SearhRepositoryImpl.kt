package com.example.githubrepofinder.repo

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.repofinder.api.GithubApi
import com.example.repofinder.di.NetworkUtils
import com.example.repofinder.model.Repository
import com.example.repofinder.model.room.SearchDao
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchRepositoryImpl @Inject constructor(
    private val searchApi: GithubApi,
    private val repositoryDao: SearchDao,
    @ApplicationContext private val context: Context
) : SearchRepository {
    override fun searchRepositories(searchQuery: String): Flow<PagingData<Repository>> {
        return if (NetworkUtils.isNetworkAvailable(context)) {
            // Network is available, fetch from API
            Pager(config = PagingConfig(pageSize = 10)) {
                SearchRepositoryDataSource(
                    searchApi = searchApi,
                    searchQuery = searchQuery,
                    repositoryDao = repositoryDao
                )
            }.flow
        } else {
            // Network is offline, fetch from Room (limit to 15 results)
            Pager(config = PagingConfig(pageSize = 15)) {
                repositoryDao.searchRepositoriesFromDb("%$searchQuery%")
            }.flow
        }
    }
}