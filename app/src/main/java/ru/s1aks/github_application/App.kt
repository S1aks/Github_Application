package ru.s1aks.github_application

import android.app.Application
import com.github.terrakok.cicerone.Cicerone
import ru.s1aks.github_application.impl.LikeBus
import ru.s1aks.github_application.impl.StateBus

class App : Application() {

    private val cicerone = Cicerone.create()
    val router get() = cicerone.router
    val navigatorHolder get() = cicerone.getNavigatorHolder()
    val stateBus = StateBus()
    val likeBus = LikeBus()
}