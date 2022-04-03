package ru.s1aks.github_application.di

import androidx.room.Room
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import ru.s1aks.github_application.App
import ru.s1aks.github_application.domain.Database
import ru.s1aks.github_application.domain.GithubApi
import ru.s1aks.github_application.impl.RoomGithubRepositoriesCacheImpl
import ru.s1aks.github_application.impl.RoomGithubUsersCacheImpl
import ru.s1aks.github_application.impl.WebGithubRepoImpl

val appModule = module {
    single { CompositeDisposable() }
    single { Cicerone.create() }
    single { get<Cicerone<Router>>().router }
    single { get<Cicerone<Router>>().getNavigatorHolder() }
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) })
            .build()
    }
    single {
        Retrofit.Builder()
            .baseUrl(App.BASE_URL)
            .client(get())
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build().create(GithubApi::class.java)
    }
    single {
        Room.databaseBuilder(
            get(),
            Database::class.java,
            Database.DB_NAME
        ).build()
    }
    single { get<Database>().userDao }
    single { get<Database>().repositoryDao }

    single { WebGithubRepoImpl(get()) }
    single { RoomGithubUsersCacheImpl(get(), get<WebGithubRepoImpl>(), get()) }
    single { RoomGithubRepositoriesCacheImpl(get(), get<WebGithubRepoImpl>(), get()) }
}