package ru.s1aks.github_application.ui.user_details_fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.terrakok.cicerone.Router
import com.google.android.material.snackbar.Snackbar
import io.reactivex.disposables.CompositeDisposable
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import ru.s1aks.github_application.R
import ru.s1aks.github_application.databinding.FragmentUserDetailsBinding
import ru.s1aks.github_application.domain.entities.GithubUser
import ru.s1aks.github_application.impl.RoomGithubRepositoriesCacheImpl
import ru.s1aks.github_application.impl.util.app
import ru.s1aks.github_application.ui.BackButtonListener
import javax.inject.Inject

class UserDetailsFragment : MvpAppCompatFragment(), UserDetailsContract.View, BackButtonListener {
    private var binding: FragmentUserDetailsBinding? = null

    @Inject
    lateinit var compositeDisposable: CompositeDisposable

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var repoCacheImpl: RoomGithubRepositoriesCacheImpl
    private val presenter: UserDetailsPresenter by moxyPresenter {
        UserDetailsPresenter(
            compositeDisposable,
            repoCacheImpl,
            router)
    }

    private lateinit var adapter: UserDetailsAdapter

    private var userLogin: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        app.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        arguments?.let {
            userLogin = it.getString(ARG_USER_LOGIN)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentUserDetailsBinding.inflate(layoutInflater, container, false)
        return binding!!.root
    }

    override fun initView() {
        binding?.recyclerView?.layoutManager = LinearLayoutManager(context)
        adapter = UserDetailsAdapter(presenter.listPresenter)
        binding?.recyclerView?.adapter = adapter
        presenter.user = userLogin?.let { GithubUser(it, 0, "") }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun updateList() {
        adapter.notifyDataSetChanged()
    }

    override fun showForksNumber(number: Int) {
        val forksSnackbar = Snackbar.make(requireView(),
            "${getString(R.string.toast_start_text)} $number",
            Snackbar.LENGTH_LONG)
        val params = forksSnackbar.view.layoutParams as FrameLayout.LayoutParams
        params.gravity = Gravity.TOP + Gravity.CENTER_HORIZONTAL
        params.width = ViewGroup.LayoutParams.WRAP_CONTENT
        forksSnackbar.show()
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
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