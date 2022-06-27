package com.awp.githubuser.model

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.awp.githubuser.api.Client
import com.awp.githubuser.database.FavoriteUserDao
import com.awp.githubuser.database.UserDb
import com.awp.githubuser.database.UserFavoriteData
import com.awp.githubuser.response.DetailUserResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : AndroidViewModel(application) {

    val userDetail = MutableLiveData<DetailUserResponse>()

    private var userDb: UserDb?
    private var userDao: FavoriteUserDao?

    init {
        userDb = UserDb.getDatabase(application)
        userDao = userDb?.favoriteUserDao()
    }

    fun addDataFavorite(username: String, id: Int, avatarUrl: String) {
        CoroutineScope(Dispatchers.IO).launch {
            var user = UserFavoriteData( username, id, avatarUrl )
            userDao?.addToFavorite(user)
        }
    }

    fun checkUser(id: Int) = userDao?.checkUser(id)

    fun setUserDetail(username: String) {

        Client.apiInstance
            .getUserDetail(username)
            .enqueue(object : Callback<DetailUserResponse> {
                override fun onResponse(
                    call: Call<DetailUserResponse>,
                    response: Response<DetailUserResponse>
                ) {
                    if (response.isSuccessful) {
                        userDetail.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                    Log.e("Failure", t.message.toString())
                }

            })
    }

    fun deleteDataFavorite(id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.removeFromFavorite(id)
        }
    }

    fun getDataDetail(): LiveData<DetailUserResponse> {
        return userDetail
    }
}