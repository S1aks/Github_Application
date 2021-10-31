package ru.s1aks.github_application.impl

import io.reactivex.Single
import ru.s1aks.github_application.App
import ru.s1aks.github_application.domain.WebGithubRepo
import ru.s1aks.github_application.domain.entities.GithubUser
import ru.s1aks.github_application.domain.entities.GithubUserRepo

class WebGithubRepoImpl : WebGithubRepo {
    override fun getUsers(): Single<List<GithubUser>> = App.instance.githubApi.usersList()

    override fun getUserRepoList(user: String): Single<List<GithubUserRepo>> =
        App.instance.githubApi.userRepoList(user)
}