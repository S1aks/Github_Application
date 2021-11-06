package ru.s1aks.github_application.ui.user_details_fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import ru.s1aks.github_application.App
import ru.s1aks.github_application.databinding.FragmentUserDetailsBinding
import ru.s1aks.github_application.impl.WebGithubRepoImpl
import ru.s1aks.github_application.ui.BackButtonListener

class UserDetailsFragment : MvpAppCompatFragment(), UserDetailsContract.View, BackButtonListener {
    private var binding: FragmentUserDetailsBinding? = null
    private val presenter: UserDetailsPresenter by moxyPresenter {
        UserDetailsPresenter(WebGithubRepoImpl(), App.instance.router)
    }

    private lateinit var adapter: UserDetailsAdapter

    private var userLogin: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            userLogin = it.getString(ARG_USER_LOGIN)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentUserDetailsBinding.inflate(layoutInflater,container,false)
        return binding!!.root
    }

    override fun initView() {
        binding?.recyclerView?.layoutManager = LinearLayoutManager(context)
        adapter = UserDetailsAdapter(presenter.listPresenter)
        binding?.recyclerView?.adapter = adapter
        presenter.userLogin = userLogin
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun updateList() {
        adapter.notifyDataSetChanged()
    }

    override fun showToast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    override fun onDestroy() {
        presenter.dispose()
        super.onDestroy()
    }

    override fun showError(message: String) {
        view?.let { Snackbar.make(it, message, Snackbar.LENGTH_SHORT) }
    }

    override fun backPressed(): Boolean = presenter.backPressed()

    companion object {
        private const val ARG_USER_LOGIN = "user_login"

        fun newInstance(userLogin: String) =
            UserDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_USER_LOGIN, userLogin)
                }
            }
    }
}