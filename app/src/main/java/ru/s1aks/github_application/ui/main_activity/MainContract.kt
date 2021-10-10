package ru.s1aks.github_application.ui.main_activity

import moxy.MvpPresenter
import moxy.MvpView

class MainContract {
    interface View : MvpView

    abstract class  Presenter : MvpPresenter<View>() {
        abstract fun backClicked()
    }
}