package ru.s1aks.github_application.ui.main_activity

import android.os.Bundle
import androidx.core.view.isVisible
import com.github.terrakok.cicerone.androidx.AppNavigator
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import ru.s1aks.github_application.databinding.ActivityMainBinding
import ru.s1aks.github_application.impl.StateEvent
import ru.s1aks.github_application.impl.util.app
import ru.s1aks.github_application.ui.BackButtonListener

class MainActivity : MvpAppCompatActivity(), MainContract.View {

    private lateinit var binding: ActivityMainBinding
    private val presenter by moxyPresenter { MainPresenter(app.stateBus, app.likeBus, app.router) }
    private val navigator by lazy { AppNavigator(this, binding.container.id) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        presenter.subscribeStateBus()
        presenter.subscribeLikeBus()
        hideContent()
    }

    private fun hideContent() {
        binding.mainPageProgressBar.isVisible = false
        binding.mainPageContent.isVisible = false
    }

    override fun setState(state: StateEvent) {
        hideContent()
        when (state) {
            StateEvent.LOADING_STATE -> binding.mainPageProgressBar.isVisible = true
            StateEvent.SUCCESS_STATE -> binding.mainPageContent.isVisible = true
            StateEvent.ERROR_STATE -> binding.mainPageContent.isVisible = true
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        app.navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        app.navigatorHolder.removeNavigator()
    }

    override fun onBackPressed() {
        supportFragmentManager.fragments.forEach {
            if (it is BackButtonListener && it.backPressed()) {
                return
            }
        }
        presenter.backClicked()
    }

    override fun setLikes(numLikes: Int) {
        binding.likesCounter.text = numLikes.toString()
    }

}