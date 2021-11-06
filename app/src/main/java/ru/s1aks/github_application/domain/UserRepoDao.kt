package ru.s1aks.github_application.domain

import androidx.room.*
import ru.s1aks.github_application.domain.entities.GithubUserRepoDTO

@Dao
interface UserRepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(repos: List<GithubUserRepoDTO>)

    @Delete
    fun delete(repos: List<GithubUserRepoDTO>)

    @Query("DELETE FROM GithubUserRepoDTO WHERE owner = :user")
    fun clearFromUser(user: String)

    @Query("DELETE FROM GithubUserRepoDTO")
    fun clearAll()

    @Query("SELECT * FROM GithubUserRepoDTO")
    fun getAll(): List<GithubUserRepoDTO>

    @Query("SELECT * FROM GithubUserRepoDTO WHERE owner = :user")
    fun findForUser(user: String): List<GithubUserRepoDTO>
}