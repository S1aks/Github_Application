package ru.s1aks.github_application.domain

import io.reactivex.subjects.BehaviorSubject
import ru.s1aks.github_application.domain.entities.GithubUserDTO

interface RoomGithubUsersCache {
    val usersCache: BehaviorSubject<List<GithubUserDTO>>
    fun getUsers()
}