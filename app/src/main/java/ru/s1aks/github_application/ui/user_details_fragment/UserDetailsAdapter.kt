package ru.s1aks.github_application.ui.user_details_fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.s1aks.github_application.databinding.FragmentUserDetailsViewItemBinding
import ru.s1aks.github_application.domain.entities.GithubUserRepo

interface ItemView {
    var position: Int?
}

interface DetailsItemView : ItemView {
    fun setData(userRepo: GithubUserRepo?)
}

interface ListPresenter<V : ItemView> {
    var itemClickListener: ((V) -> Unit)?
    fun bindView(view: V)
    fun getCount(): Int
}

interface DetailsListPresenter : ListPresenter<DetailsItemView>

class UserDetailsAdapter(private val presenter: DetailsListPresenter) :
    RecyclerView.Adapter<UserDetailsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(FragmentUserDetailsViewItemBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false)).apply {
            itemView.setOnClickListener { presenter.itemClickListener?.invoke(this) }
        }

    override fun getItemCount() = presenter.getCount()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        presenter.bindView(holder.apply { this.position = position })

    inner class ViewHolder(
        private val binding: FragmentUserDetailsViewItemBinding,
    ) : RecyclerView.ViewHolder(binding.root),
        DetailsItemView {

        override var position: Int? = -1

        override fun setData(userRepo: GithubUserRepo?) {
            binding.textViewItem.text = userRepo?.name
        }
    }
}