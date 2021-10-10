package ru.s1aks.github_application.ui.main_activity

import com.github.terrakok.cicerone.Router
import ru.s1aks.github_application.ui.Screens

class MainPresenter(private val router: Router) : MainContract.Presenter() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        router.replaceScreen(Screens.users())
    }

    override fun backClicked() {
        router.exit()
    }
}