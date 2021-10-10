package ru.s1aks.github_application.ui.users_fragment

import moxy.MvpPresenter
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import ru.s1aks.github_application.domain.entities.GithubUser
import ru.s1aks.github_application.ui.BackButtonListener

class UsersContract {

    @AddToEndSingle
    interface View : MvpView {
        fun init()
        fun updateList()
    }

    abstract class Presenter : MvpPresenter<View>() {
        abstract fun loadData()
        abstract fun loadUser(position: Int): GithubUser
        abstract fun backPressed(): Boolean
    }
}