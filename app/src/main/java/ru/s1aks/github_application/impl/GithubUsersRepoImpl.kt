package ru.s1aks.github_application.impl

import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import ru.s1aks.github_application.domain.GithubUsersRepo
import ru.s1aks.github_application.domain.entities.GithubUser
import java.util.concurrent.TimeUnit

class GithubUsersRepoImpl : GithubUsersRepo {
    private val userList = listOf(
        GithubUser("login1"),
        GithubUser("login2"),
        GithubUser("login3"),
        GithubUser("login4"),
        GithubUser("login5")
    )

    private val behaviorSubject = BehaviorSubject.createDefault(userList).delay(2, TimeUnit.SECONDS)

    override val users: Observable<List<GithubUser>>
        get() = behaviorSubject
}