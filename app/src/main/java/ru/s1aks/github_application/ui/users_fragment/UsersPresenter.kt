package ru.s1aks.github_application.ui.users_fragment

import android.content.Context
import com.github.terrakok.cicerone.Router
import io.reactivex.disposables.CompositeDisposable
import ru.s1aks.github_application.domain.entities.GithubUser
import ru.s1aks.github_application.impl.GithubUsersRepoImpl
import ru.s1aks.github_application.impl.LikeEvent
import ru.s1aks.github_application.impl.StateEvent
import ru.s1aks.github_application.impl.util.app
import ru.s1aks.github_application.ui.Screens

class UsersPresenter(
    private val appContext: Context,
    private val usersRepo: GithubUsersRepoImpl,
    private val router: Router,
) : UsersContract.Presenter() {

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    interface ItemView {
        var position: Int?
        var liked: Boolean
        fun setItem(user: GithubUser)
        fun toggleLike()
    }

    interface ListPresenter<V : ItemView> {
        var itemClickListener: ((V) -> Unit)?
        var likeButtonClickListener: ((V) -> Unit)?
        fun bindView(view: V)
        fun getCount(): Int
    }

    class UsersListPresenter : ListPresenter<ItemView> {
        val users = mutableListOf<GithubUser>()
        override var itemClickListener: ((ItemView) -> Unit)? = null
        override var likeButtonClickListener: ((ItemView) -> Unit)? = null

        override fun getCount() = users.size

        override fun bindView(view: ItemView) {
            view.position?.let {
                val user = users[it]
                view.setItem(user)
            }
        }
    }

    val usersListPresenter = UsersListPresenter()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        appContext.app.stateBus.post(StateEvent.LOADING_STATE)
        loadData()
        usersListPresenter.itemClickListener = { userItemView ->
            userItemView.position?.let { index ->
                compositeDisposable.add(
                    usersRepo.users
                        .map { it[index] }
                        .subscribe(
                            { user -> router.navigateTo(Screens.userDetail(user.login)) },
                            { thr -> thr.message?.let { viewState.showError(it) } }
                        )
                )
            }
        }
        usersListPresenter.likeButtonClickListener = {
            it.toggleLike()
            appContext.app.likeBus.post(
                when (it.liked) {
                    true -> LikeEvent.PLUS_EVENT
                    false -> LikeEvent.MINUS_EVENT
                }
            )
            usersListPresenter.users[it.position!!].liked = it.liked
        }
    }

    override fun loadData() {
        compositeDisposable.add(
            usersRepo.users
                .subscribe(
                    { userList ->
                        usersListPresenter.users.addAll(userList)
                        viewState.updateList()
                        appContext.app.stateBus.post(StateEvent.SUCCESS_STATE)
                    },
                    { thr -> thr.message?.let { viewState.showError(it) } }
                )
        )
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }

    override fun backPressed(): Boolean {
        router.exit()
        return true
    }

}