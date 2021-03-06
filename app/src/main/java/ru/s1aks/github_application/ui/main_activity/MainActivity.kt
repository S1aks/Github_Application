package ru.s1aks.github_application.ui.main_activity

import android.os.Bundle
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import org.koin.android.ext.android.inject
import ru.s1aks.github_application.databinding.ActivityMainBinding
import ru.s1aks.github_application.ui.BackButtonListener

class MainActivity : MvpAppCompatActivity(), MainContract.View {

    private lateinit var binding: ActivityMainBinding
    private val router: Router by inject()
    private val navigatorHolder: NavigatorHolder by inject()
    private val presenter by moxyPresenter { MainPresenter(router) }
    private val navigator by lazy { AppNavigator(this, binding.root.id) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }

    override fun onBackPressed() {
        supportFragmentManager.fragments.forEach {
            if (it is BackButtonListener && it.backPressed()) {
                return
            }
        }
        presenter.backClicked()
    }

}