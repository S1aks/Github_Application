package ru.s1aks.github_application.domain

import io.reactivex.subjects.BehaviorSubject
import ru.s1aks.github_application.domain.entities.GithubUser
import ru.s1aks.github_application.domain.entities.GithubUserRepoDTO

interface RoomGithubRepositoriesCache {
    val userReposCache: BehaviorSubject<List<GithubUserRepoDTO>>
    fun getUserRepoList(user: GithubUser)
}