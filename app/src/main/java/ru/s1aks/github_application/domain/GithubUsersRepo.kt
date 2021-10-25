package ru.s1aks.github_application.domain

import io.reactivex.Observable
import ru.s1aks.github_application.domain.entities.GithubUser

interface GithubUsersRepo {
    val users: Observable<List<GithubUser>>
}