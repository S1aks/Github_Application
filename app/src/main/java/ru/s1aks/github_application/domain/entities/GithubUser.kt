package ru.s1aks.github_application.domain.entities

import com.squareup.moshi.Json

data class GithubUser(
    @field:Json(name = "login") var login: String,
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "avatar_url") val avatarUrl: String
)