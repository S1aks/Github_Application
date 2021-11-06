package ru.s1aks.github_application.domain

import androidx.room.*
import ru.s1aks.github_application.domain.entities.GithubUserDTO

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(users: List<GithubUserDTO>)

    @Delete
    fun delete(users: List<GithubUserDTO>)

    @Query("DELETE FROM GithubUserDTO")
    fun clear()

    @Query("SELECT * FROM GithubUserDTO")
    fun getAll(): List<GithubUserDTO>

    @Query("SELECT * FROM GithubUserDTO WHERE login = :login LIMIT 1")
    fun findByLogin(login: String): GithubUserDTO?
}
