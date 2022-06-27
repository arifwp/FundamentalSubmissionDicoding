package com.awp.githubuser.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.awp.githubuser.database.FavoriteUserDao
import com.awp.githubuser.database.UserDb
import com.awp.githubuser.database.UserFavoriteData

class FavViewModel(application: Application): AndroidViewModel(application) {

    private var userDao: FavoriteUserDao?
    private var userDb: UserDb?

    init {
        userDb = UserDb.getDatabase(application)
        userDao = userDb?.favoriteUserDao()
    }

    fun getFavoriteUser(): LiveData<List<UserFavoriteData>>? {
        return userDao?.getFavoriteUser()
    }
}