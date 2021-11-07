package ru.s1aks.github_application

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import ru.s1aks.github_application.di.appModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(appModule)
        }
    }

    companion object {
        const val BASE_URL = "https://api.github.com/"
    }
}