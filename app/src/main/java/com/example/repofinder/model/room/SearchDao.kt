package com.example.repofinder.model.room

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.repofinder.model.Repository

@Dao
interface SearchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepo(repositories: List<Repository>)

    @Query("SELECT * FROM repositories WHERE name LIKE :searchQuery LIMIT 15")
    fun searchRepositoriesFromDb(searchQuery: String): PagingSource<Int, Repository>

}