package ru.s1aks.github_application.domain.entities

import com.squareup.moshi.Json

data class GithubUserRepo(
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "owner") val owner: GithubUser,
    @field:Json(name = "forks") val forks: Int,
)