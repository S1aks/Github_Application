package ru.s1aks.github_application.ui.main_activity

import com.github.terrakok.cicerone.Router
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ru.s1aks.github_application.impl.LikeBus
import ru.s1aks.github_application.impl.LikeEvent
import ru.s1aks.github_application.impl.StateBus
import ru.s1aks.github_application.ui.Screens

class MainPresenter(
    private val stateBus: StateBus,
    private val likeBus: LikeBus,
    private val router: Router,
) : MainContract.Presenter() {

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()
    private var count = 0

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        router.replaceScreen(Screens.users())
    }

    override fun subscribeStateBus() {
        compositeDisposable.add(
            stateBus.get()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe {
                    viewState.setState(it)
                })
    }

    override fun subscribeLikeBus() {
        compositeDisposable.add(
            likeBus.get()
                .subscribe {
                    when (it!!) {
                        LikeEvent.PLUS_EVENT -> count++
                        LikeEvent.MINUS_EVENT -> count--
                    }
                    viewState.setLikes(count)
                })
    }

    override fun backClicked() {
        router.exit()
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }
}