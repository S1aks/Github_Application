package ru.s1aks.github_application.di

import android.content.Context
import androidx.room.Room
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import dagger.Component
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import ru.s1aks.github_application.App
import ru.s1aks.github_application.domain.*
import ru.s1aks.github_application.impl.RoomGithubRepositoriesCacheImpl
import ru.s1aks.github_application.impl.RoomGithubUsersCacheImpl
import ru.s1aks.github_application.impl.WebGithubRepoImpl
import ru.s1aks.github_application.ui.main_activity.MainActivity
import ru.s1aks.github_application.ui.user_details_fragment.UserDetailsFragment
import ru.s1aks.github_application.ui.users_fragment.UsersFragment
import javax.inject.Singleton

@Module
class WebModule {
    @Provides
    @Singleton
    fun provideClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) })
        .build()

    @Provides
    @Singleton
    fun provideGithubApi(client: OkHttpClient): GithubApi = Retrofit.Builder()
        .baseUrl(App.BASE_URL)
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
        .build().create(GithubApi::class.java)
}

@Module
class DbModule(val context: Context) {
    @Provides
    @Singleton
    fun provideContext(): Context = context

    @Provides
    @Singleton
    fun provideDatabase(context: Context): Database = Room.databaseBuilder(
        context,
        Database::class.java,
        Database.DB_NAME
    ).build()

    @Provides
    @Singleton
    fun provideUserDao(db: Database): UserDao = db.userDao

    @Provides
    @Singleton
    fun provideRepositoryDao(db: Database): UserRepoDao = db.repositoryDao
}

@Module
class AppModule {
    @Provides
    @Singleton
    fun provideCompositeDisposable(): CompositeDisposable = CompositeDisposable()

    @Provides
    @Singleton
    fun provideCicerone(): Cicerone<Router> = Cicerone.create()

    @Provides
    @Singleton
    fun provideRouter(cicerone: Cicerone<Router>): Router = cicerone.router

    @Provides
    @Singleton
    fun provideNavigatorHolder(cicerone: Cicerone<Router>): NavigatorHolder = cicerone.getNavigatorHolder()

    @Provides
    @Singleton
    fun provideWebRepo(githubApi: GithubApi): WebGithubRepo = WebGithubRepoImpl(githubApi)

    @Provides
    @Singleton
    fun provideUsersCache(
        disposable: CompositeDisposable,
        webRepo: WebGithubRepo,
        userDao: UserDao,
    ) = RoomGithubUsersCacheImpl(disposable, webRepo, userDao)

    @Provides
    @Singleton
    fun providesRepoCache(
        disposable: CompositeDisposable,
        webRepo: WebGithubRepo,
        userRepoDao: UserRepoDao,
    ) = RoomGithubRepositoriesCacheImpl(disposable, webRepo, userRepoDao)
}

@Singleton
@Component(modules = [WebModule::class, DbModule::class, AppModule::class])
interface AppComponent{
    fun inject(activity: MainActivity)
    fun inject(usersFragment: UsersFragment)
    fun inject(userDetailsFragment: UserDetailsFragment)
    fun getDisposable(): CompositeDisposable
    fun getRouter(): Router
}