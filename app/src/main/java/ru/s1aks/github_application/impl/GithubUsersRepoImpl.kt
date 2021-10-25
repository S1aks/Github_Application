package ru.s1aks.github_application.impl

import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import ru.s1aks.github_application.domain.GithubUsersRepo
import ru.s1aks.github_application.domain.entities.GithubUser

class GithubUsersRepoImpl : GithubUsersRepo {
    private val userList = listOf(
        GithubUser("login1"),
        GithubUser("login2"),
        GithubUser("login3"),
        GithubUser("login4"),
        GithubUser("login5")
    )

    private val behaviorSubject = BehaviorSubject.createDefault(userList)

    override val users: Observable<List<GithubUser>>
        get() = behaviorSubject
}