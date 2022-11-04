package com.example.androidtv.ui.user

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidtv.data.api.ApiHelper
import com.example.androidtv.data.api.RetrofitBuilder
import com.example.androidtv.data.model.User
import com.example.androidtv.databinding.ActivityUserBinding
import com.example.androidtv.ui.base.ViewModelFactory
import com.example.androidtv.utils.Status
import com.example.androidtv.viewmodel.UserViewModel

/**
 * Created by Dipak Kumar Mehta on 10/21/2021.
 */
class UserActivity : FragmentActivity() {

    private lateinit var viewModel: UserViewModel
    private lateinit var adapter: UserAdapter
    private var binding: ActivityUserBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        val view = binding?.root
        setContentView(view)
        setupUI()
        setupViewModel()
        setupObservers()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, ViewModelFactory(ApiHelper(RetrofitBuilder.apiService)))
            .get(UserViewModel::class.java)
    }

    private fun setupUI() {
        binding?.recyclerView?.layoutManager = LinearLayoutManager(this)
        adapter = UserAdapter(arrayListOf())
        binding?.recyclerView?.addItemDecoration(
            DividerItemDecoration(binding?.recyclerView!!.context, (binding?.recyclerView!!.layoutManager as LinearLayoutManager).orientation)
        )
        binding?.recyclerView?.adapter = adapter
    }


    private fun setupObservers() {
        viewModel.getUser().observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        binding?.recyclerView?.visibility = View.VISIBLE
                        binding?.progressBar?.visibility = View.GONE
                        resource.data?.let { users -> retrieveList(users) }
                    }
                    Status.ERROR -> {
                        binding?.recyclerView?.visibility = View.VISIBLE
                        binding?.progressBar?.visibility = View.GONE
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        binding?.progressBar?.visibility = View.VISIBLE
                        binding?.recyclerView?.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun retrieveList(users: List<User>) {
        adapter.apply {
            addUsers(users)
            notifyDataSetChanged()
        }
    }
}