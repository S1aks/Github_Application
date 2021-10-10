package ru.s1aks.github_application.ui.users_fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.s1aks.github_application.databinding.FragmentUsersViewItemBinding

interface ItemView {
    var position: Int?
}

interface UserItemView: ItemView {
    fun setLogin(userLogin: String)
}

interface ListPresenter<V : ItemView> {
    var itemClickListener: ((V) -> Unit)?
    fun bindView(view: V)
    fun getCount(): Int
}

interface UserListPresenter : ListPresenter<UserItemView>

class UsersAdapter(private val presenter: UserListPresenter) :
    RecyclerView.Adapter<UsersAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(FragmentUsersViewItemBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false)).apply {
            itemView.setOnClickListener { presenter.itemClickListener?.invoke(this) }
        }

    override fun getItemCount() = presenter.getCount()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        presenter.bindView(holder.apply { this.position = position })

    inner class ViewHolder(
        private val binding: FragmentUsersViewItemBinding,
    ) : RecyclerView.ViewHolder(binding.root),
        UserItemView {

        override var position: Int? = -1

        override fun setLogin(userLogin: String) {
            binding.tvLogin.text = userLogin
        }
    }
}