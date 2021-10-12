package ru.s1aks.github_application.ui.users_fragment

import com.github.terrakok.cicerone.Router
import io.reactivex.disposables.CompositeDisposable
import ru.s1aks.github_application.domain.GithubUsersRepoImpl
import ru.s1aks.github_application.domain.entities.GithubUser
import ru.s1aks.github_application.ui.Screens

class UsersPresenter(
    private val usersRepo: GithubUsersRepoImpl,
    private val router: Router,
) : UsersContract.Presenter() {

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    class UsersListPresenter : UserListPresenter {
        val users = mutableListOf<GithubUser>()
        override var itemClickListener: ((UserItemView) -> Unit)? = null

        override fun getCount() = users.size

        override fun bindView(view: UserItemView) {
            view.position?.let {
                val user = users[it]
                view.setLogin(user.login)
            }

        }
    }

    val usersListPresenter = UsersListPresenter()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        loadData()
        usersListPresenter.itemClickListener = { userItemView ->
            userItemView.position?.let { index ->
                compositeDisposable.add(
                    usersRepo.users
                        .map { it[index] }
                        .subscribe(
                            { user ->
                                router.navigateTo(Screens.userDetail(user.login))
                            },
                            { thr ->
                                thr.message?.let { viewState.showError(it) }
                            }
                        )
                )
            }
        }
    }

    override fun loadData() {
        compositeDisposable.add(
            usersRepo.users
                .subscribe(
                    { userList ->
                        usersListPresenter.users.addAll(userList)
                        viewState.updateList()
                    },
                    { thr ->
                        thr.message?.let { viewState.showError(it) }
                    }
                )
        )
    }

    override fun backPressed(): Boolean {
        router.exit()
        return true
    }

}