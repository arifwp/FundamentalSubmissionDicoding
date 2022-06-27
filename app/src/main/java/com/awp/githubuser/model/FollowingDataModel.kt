package com.awp.githubuser.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.awp.githubuser.api.Client
import com.awp.githubuser.database.UserData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingDataModel: ViewModel() {
    val listFollowing = MutableLiveData<ArrayList<UserData>>()

    fun getListFollowing(): LiveData<ArrayList<UserData>> {
        return listFollowing
    }

    fun setListFollowing(username: String) {
        Client.apiInstance
            .getFollowing(username)
            .enqueue(object : Callback<ArrayList<UserData>> {
                override fun onResponse(
                    call: Call<ArrayList<UserData>>,
                    response: Response<ArrayList<UserData>>
                ) {
                    if (response.isSuccessful) {
                        listFollowing.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<UserData>>, t: Throwable) {
                    t.message?.let { Log.d("Failure", it) }
                }

            })
    }
}