package ru.s1aks.github_application.ui.users_fragment

import com.github.terrakok.cicerone.Router
import ru.s1aks.github_application.domain.GithubUsersRepo
import ru.s1aks.github_application.domain.entities.GithubUser
import ru.s1aks.github_application.ui.Screens

class UsersPresenter(
    private val usersRepo: GithubUsersRepo,
    private val router: Router,
) : UsersContract.Presenter() {

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
            userItemView.position?.let { router.navigateTo(Screens.userDetail(loadUser(it).login)) }
        }
    }

    override fun loadData() {
        val users = usersRepo.getUsers()
        usersListPresenter.users.addAll(users)
        viewState.updateList()
    }

    override fun loadUser(position: Int): GithubUser {
        return usersRepo.getUsers()[position]
    }

    override fun backPressed(): Boolean {
        router.exit()
        return true
    }

}