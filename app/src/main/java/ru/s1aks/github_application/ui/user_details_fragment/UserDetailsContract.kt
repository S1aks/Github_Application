package ru.s1aks.github_application.ui.user_details_fragment

import moxy.MvpPresenter
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import ru.s1aks.github_application.domain.entities.GithubUser

class UserDetailsContract {

    @AddToEndSingle
    interface View : MvpView {
        fun initView()
        fun updateList()
        fun showForksNumber(number: Int)
        fun showError(message: String)
    }

    abstract class Presenter : MvpPresenter<View>() {
        abstract fun loadData(user: GithubUser)
        abstract fun backPressed(): Boolean
    }
}