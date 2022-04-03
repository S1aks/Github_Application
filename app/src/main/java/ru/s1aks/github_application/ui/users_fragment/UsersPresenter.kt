package ru.s1aks.github_application.ui.users_fragment

import com.github.terrakok.cicerone.Router
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import ru.s1aks.github_application.domain.RoomGithubUsersCache
import ru.s1aks.github_application.domain.entities.GithubUser
import ru.s1aks.github_application.ui.Screens

class UsersPresenter(
    private val compositeDisposable: CompositeDisposable?,
    private val repo: RoomGithubUsersCache,
    private val router: Router,
) : UsersContract.Presenter() {

    class UsersListPresenter : UserListPresenter {
        val users = mutableListOf<GithubUser>()
        override var itemClickListener: ((UserItemView) -> Unit)? = null

        override fun getCount() = users.size

        override fun bindView(view: UserItemView) {
            view.position?.let {
                view.setData(users[it])
            }

        }
    }

    val listPresenter = UsersListPresenter()

    private var userList: List<GithubUser>? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.initView()
        loadData()
        listPresenter.itemClickListener = { itemView ->
            itemView.position?.let { index ->
                userList?.get(index)?.login?.let { router.navigateTo(Screens.userDetail(it)) }
            }
        }
    }

    override fun loadData() {
        compositeDisposable?.add(
            repo.usersCache
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { list ->
                        userList = list.map {
                            GithubUser(
                                it.login,
                                it.id,
                                it.avatarUrl
                            )
                        }
                        listPresenter.users.clear()
                        listPresenter.users.addAll(userList!!)
                        viewState.updateList()
                    },
                    { thr ->
                        thr.message?.let { viewState.showError(it) }
                    }
                )
        )
        Thread { repo.getUsers() }.start()
    }

    override fun backPressed(): Boolean {
        router.exit()
        return true
    }

}