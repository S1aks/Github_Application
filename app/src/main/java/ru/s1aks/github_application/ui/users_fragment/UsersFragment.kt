package ru.s1aks.github_application.ui.users_fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import io.reactivex.disposables.CompositeDisposable
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import ru.s1aks.github_application.App
import ru.s1aks.github_application.databinding.FragmentUsersBinding
import ru.s1aks.github_application.impl.RoomGithubUsersCacheImpl
import ru.s1aks.github_application.impl.WebGithubRepoImpl
import ru.s1aks.github_application.ui.BackButtonListener

class UsersFragment : MvpAppCompatFragment(), UsersContract.View, BackButtonListener {

    private var binding: FragmentUsersBinding? = null
    private var compositeDisposable: CompositeDisposable? = CompositeDisposable()
    private val presenter: UsersPresenter by moxyPresenter {
        UsersPresenter(
            compositeDisposable,
            RoomGithubUsersCacheImpl(
                compositeDisposable,
                WebGithubRepoImpl(),
                App.instance.db.userDao),
            App.instance.router)
    }
    private lateinit var adapter: UsersAdapter


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

    override fun onDestroy() {
        compositeDisposable = null
        super.onDestroy()
    }

    override fun backPressed(): Boolean = presenter.backPressed()

    companion object {
        fun newInstance() = UsersFragment()
    }
}