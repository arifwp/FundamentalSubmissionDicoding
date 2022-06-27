package com.awp.githubuser.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.awp.githubuser.view.DetailActivity
import com.awp.githubuser.model.FollowersDataModel
import com.awp.githubuser.adapter.ListUserAdapter
import com.awp.githubuser.R
import com.awp.githubuser.databinding.FragmentFollowerBinding

class FollowerFragment : Fragment(R.layout.fragment_follower) {
    private var _binding : FragmentFollowerBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewDataModel: FollowersDataModel
    private lateinit var adapter: ListUserAdapter
    private lateinit var username: String

    private fun progressLoad(state: Boolean){
        if (state){
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        username = args?.getString(DetailActivity.EXTRA_USERNAME).toString()
        _binding = FragmentFollowerBinding.bind(view)

        adapter = ListUserAdapter()
        adapter.notifyDataSetChanged()

        binding.apply {
            rvUser.setHasFixedSize(true)
            rvUser.layoutManager = LinearLayoutManager(activity)
            rvUser.adapter = adapter
        }

        progressLoad(true)
        
        viewDataModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            FollowersDataModel::class.java)
        viewDataModel.setListFollowers(username)
        viewDataModel.getListFollowers().observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.setList(it)
                progressLoad(false)
            }
        }
    }
}