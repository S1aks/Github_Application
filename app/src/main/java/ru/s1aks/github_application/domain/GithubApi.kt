package ru.s1aks.github_application.domain

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import ru.s1aks.github_application.domain.entities.GithubUser
import ru.s1aks.github_application.domain.entities.GithubUserRepo

interface GithubApi {
    @GET("users")
    fun usersList(): Single<List<GithubUser>>

    @GET("users/{user}/repos")
    fun userRepoList(@Path("user") user: String): Single<List<GithubUserRepo>>
}