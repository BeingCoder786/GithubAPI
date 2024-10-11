package com.example.repofinder.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class SearchRepositoryResponse(
    @SerializedName("total_count")
    val totalCount: Long,
    @SerializedName("items")
    val repositories: List<Repository>,
)

@Entity(tableName = "repositories")
data class Repository(
    @PrimaryKey(autoGenerate = true)
    val id: Long  = 0,
    val name: String?,
    @Embedded
    val owner: RepositoryOwner?,
    @SerializedName("html_url")
    @ColumnInfo(name = "repo_url")
    val url: String,
    val description: String?,
    @SerializedName("stargazers_count")
    val numberOfStars: Long?,
    @SerializedName("language")
    val programmingLanguage: String?,
)


data class RepositoryOwner(
    @ColumnInfo(name = "espot_id")
    val id: Long?,
    @SerializedName("login")
    val username: String?,
    @SerializedName("avatar_url")
    val url: String?,
    @SerializedName("html_url")
    val profileUrl: String?,
)
