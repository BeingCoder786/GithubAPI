package com.example.repofinder.api

import com.example.repofinder.model.SearchRepositoryResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface GithubApi {

    @GET("search/repositories")
    suspend fun searchRepositories(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") size: Int = 10,
    ): SearchRepositoryResponse
}
