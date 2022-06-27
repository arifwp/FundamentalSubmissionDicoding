package com.awp.githubuser.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.awp.githubuser.api.Client
import com.awp.githubuser.database.UserData
import com.awp.githubuser.response.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    val userData = MutableLiveData<ArrayList<UserData>>()

    fun getSearchUser(): LiveData<ArrayList<UserData>>{
        return userData
    }

    fun setSearchUser(query: String){
        Client.apiInstance
            .getSearchUser(query)
            .enqueue(object : Callback<UserResponse>{
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    if(response.isSuccessful){
                        userData.postValue(response.body()?.items)
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    t.message?.let { Log.d("Fail", it) }
                }

            })
    }

}