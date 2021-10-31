package ru.s1aks.github_application.domain

import androidx.room.RoomDatabase
import ru.s1aks.github_application.domain.entities.GithubUserDTO
import ru.s1aks.github_application.domain.entities.GithubUserRepoDTO

@androidx.room.Database(
    entities = [GithubUserDTO::class, GithubUserRepoDTO::class],
    version = 1
)
abstract class Database : RoomDatabase() {
    abstract val userDao: UserDao
    abstract val repositoryDao: UserRepoDao

    companion object {
        const val DB_NAME = "database.db"
    }
}