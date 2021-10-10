package ru.s1aks.github_application.ui

import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.s1aks.github_application.ui.user_detail_fragment.UserDetailFragment
import ru.s1aks.github_application.ui.users_fragment.UsersFragment

object Screens {
    fun users() = FragmentScreen { UsersFragment.newInstance() }
    fun userDetail(userLogin: String) = FragmentScreen { UserDetailFragment.newInstance(userLogin) }
}
