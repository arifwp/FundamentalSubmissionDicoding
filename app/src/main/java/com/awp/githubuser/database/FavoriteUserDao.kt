package com.awp.githubuser.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteUserDao {
    @Insert
    fun addToFavorite(userFavoriteData: UserFavoriteData)

    @Query("SELECT * FROM favorite_user")
    fun getFavoriteUser(): LiveData<List<UserFavoriteData>>

    @Query("SELECT count(*) FROM favorite_user WHERE favorite_user.id = :id")
    fun checkUser(id: Int): Int

    @Query("DELETE FROM favorite_user WHERE favorite_user.id = :id")
    fun removeFromFavorite(id: Int): Int
}