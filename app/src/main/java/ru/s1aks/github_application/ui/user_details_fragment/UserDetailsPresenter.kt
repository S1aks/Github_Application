package ru.s1aks.github_application.ui.user_details_fragment

import com.github.terrakok.cicerone.Router
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import ru.s1aks.github_application.domain.GithubRepo
import ru.s1aks.github_application.domain.entities.GithubUserRepo

class UserDetailsPresenter(
    private val repo: GithubRepo,
    private val router: Router,
) : UserDetailsContract.Presenter() {

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

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
    var userLogin: String? = null
    private var listRepos: List<GithubUserRepo>? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.initView()
        userLogin?.let { loadData(it) }
        listPresenter.itemClickListener = { itemView ->
            itemView.position?.let { viewState.showToast(listRepos?.get(it)?.forks.toString()) }
        }
    }

    override fun loadData(userLogin: String) {
        compositeDisposable.add(
            repo.getUserRepoList(userLogin)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        listRepos = it
                        listPresenter.userRepos.clear()
                        listPresenter.userRepos.addAll(it)
                        viewState.updateList()
                    },
                    { thr ->
                        thr.message?.let { viewState.showError(it) }
                    }
                )
        )
    }

    override fun dispose() {
        compositeDisposable.dispose()
    }

    override fun backPressed(): Boolean {
        router.exit()
        return true
    }

}