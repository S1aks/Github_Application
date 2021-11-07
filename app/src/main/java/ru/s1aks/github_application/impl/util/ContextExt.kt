package ru.s1aks.github_application.impl.util

import android.content.Context
import androidx.fragment.app.Fragment
import ru.s1aks.github_application.App

val Context.app: App
    get() = applicationContext as App

val Fragment.app: App
    get() = requireActivity().app