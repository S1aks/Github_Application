package ru.s1aks.github_application.ui.user_details_fragment

import moxy.MvpPresenter
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

class UserDetailsContract {

    @AddToEndSingle
    interface View : MvpView {
        fun initView()
        fun updateList()
        fun showToast(message: String)
        fun showError(message: String)
    }

    abstract class Presenter : MvpPresenter<View>() {
        abstract fun loadData(userLogin: String)
        abstract fun dispose()
        abstract fun backPressed(): Boolean
    }
}