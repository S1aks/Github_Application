package ru.s1aks.github_application.ui.users_fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.terrakok.cicerone.Router
import com.google.android.material.snackbar.Snackbar
import io.reactivex.disposables.CompositeDisposable
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import ru.s1aks.github_application.databinding.FragmentUsersBinding
import ru.s1aks.github_application.impl.RoomGithubUsersCacheImpl
import ru.s1aks.github_application.impl.util.app
import ru.s1aks.github_application.ui.BackButtonListener
import javax.inject.Inject

class UsersFragment : MvpAppCompatFragment(), UsersContract.View, BackButtonListener {

    private var binding: FragmentUsersBinding? = null
    @Inject
    lateinit var compositeDisposable: CompositeDisposable
    @Inject
    lateinit var router: Router
    @Inject
    lateinit var usersCacheImpl: RoomGithubUsersCacheImpl
    private val presenter: UsersPresenter by moxyPresenter {
        UsersPresenter(
            compositeDisposable,
            usersCacheImpl,
            router)
    }
    private lateinit var adapter: UsersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        app.appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentUsersBinding.inflate(layoutInflater, container, false)
        return binding!!.root
    }

    override fun initView() {
        binding?.recyclerView?.layoutManager = LinearLayoutManager(context)
        adapter = UsersAdapter(presenter.listPresenter)
        binding?.recyclerView?.adapter = adapter
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun updateList() {
        adapter.notifyDataSetChanged()
    }

    override fun showError(message: String) {
        view?.let { Snackbar.make(it, message, Snackbar.LENGTH_SHORT) }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    override fun backPressed(): Boolean = presenter.backPressed()

    companion object {
        fun newInstance() = UsersFragment()
    }
}