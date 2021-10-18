package ru.s1aks.github_application.ui.main_activity

import moxy.MvpPresenter
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import ru.s1aks.github_application.impl.StateEvent

class MainContract {

    @AddToEndSingle
    interface View : MvpView {
        fun setState(state: StateEvent)
        fun setLikes(numLikes: Int)
    }

    abstract class  Presenter : MvpPresenter<View>() {
        abstract fun subscribeStateBus()
        abstract fun subscribeLikeBus()
        abstract fun backClicked()
    }
}