package ru.s1aks.github_application.ui.user_details_fragment

import com.github.terrakok.cicerone.Router
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import ru.s1aks.github_application.domain.RoomGithubRepositoriesCache
import ru.s1aks.github_application.domain.entities.GithubUser
import ru.s1aks.github_application.domain.entities.GithubUserRepo

class UserDetailsPresenter(
    private val compositeDisposable: CompositeDisposable?,
    private val repo: RoomGithubRepositoriesCache,
    private val router: Router,
) : UserDetailsContract.Presenter() {

    class UserDetailsListPresenter : DetailsListPresenter {
        val userRepos = mutableListOf<GithubUserRepo>()
        override var itemClickListener: ((DetailsItemView) -> Unit)? = null

        override fun getCount() = userRepos.size

        override fun bindView(view: DetailsItemView) {
            view.position?.let {
                view.setData(userRepos[it])
            }

        }
    }

    val listPresenter = UserDetailsListPresenter()
    var user: GithubUser? = null
    private var listRepos: List<GithubUserRepo>? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.initView()
        user?.let { loadData(it) }
        listPresenter.itemClickListener = { itemView ->
            itemView.position?.let { viewState.showForksNumber(listRepos?.get(it)?.forks ?: 0) }
        }
    }

    override fun loadData(user: GithubUser) {
        compositeDisposable?.add(
            repo.userReposCache
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { list ->
                        listRepos = list.map {
                            GithubUserRepo(
                                it.id,
                                it.name,
                                user,
                                it.forks)
                        }
                        listPresenter.userRepos.clear()
                        listPresenter.userRepos.addAll(listRepos!!)
                        viewState.updateList()
                    },
                    { thr ->
                        thr.message?.let { viewState.showError(it) }
                    }
                )

        )
        Thread { repo.getUserRepoList(user) }.start()
    }

    override fun backPressed(): Boolean {
        router.exit()
        return true
    }

}