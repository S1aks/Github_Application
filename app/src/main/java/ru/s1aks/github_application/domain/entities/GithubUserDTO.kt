package ru.s1aks.github_application.domain.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GithubUserDTO(
    @ColumnInfo(name = "login") val login: String,
    @PrimaryKey
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "avatar_url") val avatarUrl: String,
)