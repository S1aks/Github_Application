package ru.s1aks.github_application

import android.app.Application
import ru.s1aks.github_application.di.AppComponent
import ru.s1aks.github_application.di.DaggerAppComponent
import ru.s1aks.github_application.di.DbModule

class App : Application() {
    val appComponent: AppComponent by lazy { DaggerAppComponent.builder()
        .dbModule(DbModule(this))
        .build() }

    companion object {
        const val BASE_URL = "https://api.github.com/"
    }
}