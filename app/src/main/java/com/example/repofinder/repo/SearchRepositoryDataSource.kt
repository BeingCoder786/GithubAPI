package com.example.githubrepofinder.repo

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.repofinder.api.GithubApi
import com.example.repofinder.model.Repository
import com.example.repofinder.model.room.SearchDao
import com.google.gson.annotations.SerializedName
import javax.inject.Inject

const val FIRST_PAGE = 1

class SearchRepositoryDataSource @Inject constructor(
    private val searchApi: GithubApi,
    private val searchQuery: String = "language:kotlin",
    private val repositoryDao: SearchDao
) : PagingSource<Int, Repository>() {

    override fun getRefreshKey(state: PagingState<Int, Repository>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repository> =
        try {
            val page = params.key ?: FIRST_PAGE

            val response =
                searchApi.searchRepositories(
                    query = searchQuery,
                    page = page,
                    size = params.loadSize,
                )

            // Cache the API response into Room
            val repositoryEntities = response.repositories.map { repo ->
                Repository(
                    id = repo.id,
                    name = repo.name,
                    owner = repo.owner,
                    description = repo.description,
                     url = repo.url,
                 numberOfStars = repo.numberOfStars,
                 programmingLanguage= repo.programmingLanguage

                )
            }

            // Insert into Room DB
            repositoryDao.insertRepo(repositoryEntities)

            LoadResult.Page(
                data = response.repositories,
                prevKey = if (page == FIRST_PAGE) null else page - 1,
                nextKey = if (response.repositories.isEmpty()) null else page + 1,
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
}
