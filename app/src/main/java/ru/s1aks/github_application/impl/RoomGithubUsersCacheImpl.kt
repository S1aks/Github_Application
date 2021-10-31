package ru.s1aks.github_application.impl

import android.annotation.SuppressLint
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import ru.s1aks.github_application.domain.RoomGithubUsersCache
import ru.s1aks.github_application.domain.UserDao
import ru.s1aks.github_application.domain.WebGithubRepo
import ru.s1aks.github_application.domain.entities.GithubUserDTO

class RoomGithubUsersCacheImpl(
    private val compositeDisposable: CompositeDisposable?,
    private val webRepo: WebGithubRepo,
    private val userDao: UserDao,
) : RoomGithubUsersCache {
    override val usersCache = BehaviorSubject.create<List<GithubUserDTO>>()

    @SuppressLint("CheckResult")
    override fun getUsers() {
        compositeDisposable?.add(
            webRepo.getUsers()
                .observeOn(Schedulers.io())
                .subscribe(
                    { list ->
                        userDao.clear()
                        userDao.insert(list.map {
                            GithubUserDTO(
                                it.login,
                                it.id,
                                it.avatarUrl
                            )
                        })
                        usersCache.onNext(userDao.getAll())
                    },
                    { thr -> thr.message }
                )
        )
        usersCache.onNext(userDao.getAll())
    }
}