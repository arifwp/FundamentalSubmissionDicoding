package com.awp.githubuser.api
import com.awp.githubuser.database.UserData
import com.awp.githubuser.response.DetailUserResponse
import com.awp.githubuser.response.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface Service {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getSearchUser(
        @Query("q") query: String
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getUserDetail(
        @Path("username") username : String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowers(
        @Path("username") username: String
    ): Call<ArrayList<UserData>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(
        @Path("username") username: String
    ): Call<ArrayList<UserData>>
}