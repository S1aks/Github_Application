package ru.s1aks.github_application.ui.users_fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.s1aks.github_application.databinding.FragmentUsersViewItemBinding
import ru.s1aks.github_application.domain.entities.GithubUser

class UsersAdapter(private val presenter: UsersPresenter.UsersListPresenter) :
    RecyclerView.Adapter<UsersAdapter.ViewHolder>() {
    private var binding: FragmentUsersViewItemBinding? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = FragmentUsersViewItemBinding.inflate(LayoutInflater.from(parent.context),
            parent, false)
        return ViewHolder(binding!!).apply {
            itemView.setOnClickListener {
                presenter.itemClickListener?.invoke(this)
            }
            binding!!.likeButton.setOnClickListener {
                presenter.likeButtonClickListener?.invoke(this)
            }
        }
    }

    override fun getItemCount() = presenter.getCount()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        presenter.bindView(holder.apply { this.position = position })

    inner class ViewHolder(
        private val binding: FragmentUsersViewItemBinding,
    ) : RecyclerView.ViewHolder(binding.root), UsersPresenter.ItemView {
        override var position: Int? = -1
        override var liked: Boolean = false

        override fun setItem(user: GithubUser) {
            binding.userLogin.text = user.login
            liked = user.liked
            binding.likeButton.isChecked = liked
        }

        override fun toggleLike() {
            liked = !liked
        }
    }
}