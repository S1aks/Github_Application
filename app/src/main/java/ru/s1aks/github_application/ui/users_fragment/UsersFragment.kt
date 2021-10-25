package ru.s1aks.github_application.ui.users_fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import ru.s1aks.github_application.databinding.FragmentUsersBinding
import ru.s1aks.github_application.domain.GithubUsersRepo
import ru.s1aks.github_application.impl.util.app
import ru.s1aks.github_application.ui.BackButtonListener

class UsersFragment : MvpAppCompatFragment(), UsersContract.View, BackButtonListener {

    private lateinit var binding: FragmentUsersBinding
    private val presenter: UsersPresenter by moxyPresenter {
        UsersPresenter(GithubUsersRepo(),
            requireActivity().app.router)
    }
    private lateinit var adapter: UsersAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentUsersBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun init() {
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = UsersAdapter(presenter.usersListPresenter)
        binding.recyclerView.adapter = adapter

    }

    @SuppressLint("NotifyDataSetChanged")
    override fun updateList() {
        adapter.notifyDataSetChanged()
    }

    override fun backPressed(): Boolean = presenter.backPressed()

    companion object {
        fun newInstance() = UsersFragment()
    }
}