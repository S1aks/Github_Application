package ru.s1aks.github_application.domain.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GithubUserRepoDTO(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "owner") val owner: String,
    @ColumnInfo(name = "forks") val forks: Int,
)