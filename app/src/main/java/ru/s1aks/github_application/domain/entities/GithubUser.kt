package ru.s1aks.github_application.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GithubUser(
    val login: String,
    var liked: Boolean = false
) : Parcelable