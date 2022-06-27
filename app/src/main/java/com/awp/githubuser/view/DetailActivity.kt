package com.awp.githubuser.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.awp.githubuser.databinding.ActivityDetailBinding
import com.awp.githubuser.adapter.SectionsPagerAdapter
import com.awp.githubuser.model.DetailViewModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_URL = "extra_url"
    }

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindToggle()
        modelDataDetail()

    }

    private fun bindToggle() {

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val avatarUrl = intent.getStringExtra(EXTRA_URL)
        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)

        if (username != null){
            viewModel.setUserDetail(username)
        }

        var _isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkUser(id)
            withContext(Dispatchers.Main){
                if (count != null) {
                    if (count > 0 ){
                        binding.toggleFavorite.isChecked = true
                        _isChecked = true
                    } else {
                        binding.toggleFavorite.isChecked = false
                        _isChecked = false
                    }
                }
            }
        }

        binding.toggleFavorite.setOnClickListener {
            _isChecked = !_isChecked
            if (_isChecked){
                if (username != null) {
                    if (avatarUrl != null) {
                        viewModel.addDataFavorite(username, id, avatarUrl)
                        Toast.makeText(this, "Added to favorite",Toast.LENGTH_SHORT).show()
                    }
                }
            }else {
                viewModel.deleteDataFavorite(id)
                Toast.makeText(this, "Delete from favorite",Toast.LENGTH_SHORT).show()
            }
            binding.toggleFavorite.isChecked = _isChecked
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager, bundle)
        binding.apply{
            view_pager.adapter = sectionsPagerAdapter
            tabs.setupWithViewPager(view_pager)
        }

    }

    private fun modelDataDetail() {
        viewModel.getDataDetail().observe(this) {
            if (it != null) {
                binding.apply {
                    tvName.text = it.name
                    tvUsername.text = it.login
                    tvCompany.text = it.company
                    tvLocation.text= it.location
                    tvRepository.text = "${it.public_repos.toString()} Repository"
                    tvFollower.text = "${it.followers} Followers"
                    tvFollowing.text = "${it.following} Following"
                    Glide.with(this@DetailActivity)
                        .load(it.avatar_url)
                        .centerCrop()
                        .into(imgAvatar)
                }
            }
        }
    }
}

