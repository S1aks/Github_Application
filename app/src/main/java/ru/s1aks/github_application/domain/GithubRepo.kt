package ru.s1aks.github_application.domain

import io.reactivex.Single
import ru.s1aks.github_application.domain.entities.GithubUser
import ru.s1aks.github_application.domain.entities.GithubUserRepo

interface GithubRepo {
    fun getUsers(): Single<List<GithubUser>>
    fun getUserRepoList(user: String): Single<List<GithubUserRepo>>
}