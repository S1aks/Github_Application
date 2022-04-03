package ru.s1aks.github_application.impl

import io.reactivex.Single
import ru.s1aks.github_application.App
import ru.s1aks.github_application.domain.GithubApi
import ru.s1aks.github_application.domain.WebGithubRepo
import ru.s1aks.github_application.domain.entities.GithubUser
import ru.s1aks.github_application.domain.entities.GithubUserRepo

class WebGithubRepoImpl(
    private val webApi: GithubApi
) : WebGithubRepo {
    override fun getUsers(): Single<List<GithubUser>> = webApi.usersList()

    override fun getUserRepoList(user: String): Single<List<GithubUserRepo>> =
        webApi.userRepoList(user)
}