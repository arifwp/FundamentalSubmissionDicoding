package com.awp.githubuser.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.awp.githubuser.adapter.ListUserAdapter
import com.awp.githubuser.database.UserData
import com.awp.githubuser.database.UserFavoriteData
import com.awp.githubuser.databinding.ActivityFavoriteBinding
import com.awp.githubuser.model.FavViewModel

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var viewModel : FavViewModel
    private lateinit var adapter : ListUserAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ListUserAdapter()
        adapter.notifyDataSetChanged()

        viewModel = ViewModelProvider(this).get(FavViewModel::class.java)

        viewModel.getFavoriteUser()?.observe(this) {
            if (it != null) {
                val list = userMap(it)
                adapter.setList(list)
            }
        }

        setItem()

        binding.apply {
            rvUser.setHasFixedSize(true)
            rvUser.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvUser.adapter = adapter
        }
    }

    private fun setItem() {
        adapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: UserData) {
                Intent(this@FavoriteActivity, DetailActivity::class.java).also {
                    it.putExtra(DetailActivity.EXTRA_USERNAME, data.login)
                    it.putExtra(DetailActivity.EXTRA_ID, data.id)
                    it.putExtra(DetailActivity.EXTRA_URL, data.avatar_url)
                    startActivity(it)
                }
            }
        })
    }

    private fun userMap(userFavoriteData: List<UserFavoriteData>): ArrayList<UserData> {
        val listUsers = ArrayList<UserData>()
        for (user in userFavoriteData) {
            val userMapped = UserData (
                user.id,
                user.login,
                user.avatar_url
            )
            listUsers.add(userMapped)
        }
        return listUsers
    }


}