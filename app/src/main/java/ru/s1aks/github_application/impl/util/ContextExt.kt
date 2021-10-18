package ru.s1aks.github_application.impl.util

import android.content.Context
import ru.s1aks.github_application.App

val Context.app: App
    get() = applicationContext as App
