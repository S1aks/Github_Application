package ru.s1aks.github_application.ui.users_fragment

import moxy.MvpPresenter
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

class UsersContract {

    @AddToEndSingle
    interface View : MvpView {
        fun init()
        fun updateList()
        fun showError(message: String)
    }

    abstract class Presenter : MvpPresenter<View>() {
        abstract fun loadData()
        abstract fun backPressed(): Boolean
    }
}