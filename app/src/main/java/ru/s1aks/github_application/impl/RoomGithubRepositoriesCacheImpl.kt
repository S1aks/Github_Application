package ru.s1aks.github_application.impl

import android.annotation.SuppressLint
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import ru.s1aks.github_application.domain.RoomGithubRepositoriesCache
import ru.s1aks.github_application.domain.UserRepoDao
import ru.s1aks.github_application.domain.WebGithubRepo
import ru.s1aks.github_application.domain.entities.GithubUser
import ru.s1aks.github_application.domain.entities.GithubUserRepoDTO

class RoomGithubRepositoriesCacheImpl(
    private val compositeDisposable: CompositeDisposable?,
    private val webRepo: WebGithubRepo,
    private val userRepoDao: UserRepoDao,
) : RoomGithubRepositoriesCache {

    override val userReposCache = BehaviorSubject.create<List<GithubUserRepoDTO>>()

    @SuppressLint("CheckResult")
    override fun getUserRepoList(user: GithubUser) {
        compositeDisposable?.add(
            webRepo.getUserRepoList(user.login).subscribe(
                { list ->
                    userRepoDao.clearFromUser(user.login)
                    userRepoDao.insert(list.map {
                        GithubUserRepoDTO(
                            it.id,
                            it.name,
                            it.owner.login,
                            it.forks
                        )
                    })
                    userReposCache.onNext(userRepoDao.findForUser(user.login))
                },
                { thr -> thr.message }
            )
        )
        userReposCache.onNext(userRepoDao.findForUser(user.login))
    }
}