package ru.s1aks.github_application.ui

import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.s1aks.github_application.ui.user_details_fragment.UserDetailsFragment
import ru.s1aks.github_application.ui.users_fragment.UsersFragment

object Screens {
    fun users() = FragmentScreen { UsersFragment.newInstance() }
    fun userDetail(userLogin: String) = FragmentScreen { UserDetailsFragment.newInstance(userLogin) }
}
