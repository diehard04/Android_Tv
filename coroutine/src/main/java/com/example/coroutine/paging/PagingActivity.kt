package com.example.coroutine.paging

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coroutine.R
import com.example.coroutine.paging.adapter.PagingListAdapter
import com.example.coroutine.paging.data.APIService
import kotlinx.android.synthetic.main.activity_paging.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * Created by Dipak Kumar Mehta on 11/29/2021.
 */
class PagingActivity : AppCompatActivity() {

    lateinit var viewModel: PagingViewModel
    lateinit var mainListAdapter: PagingListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paging)
        setupViewModel()
        setupList()
        setupView()
    }

    private fun setupView() {
        lifecycleScope.launch {
            viewModel.listData.collect {
                mainListAdapter.submitData(it)
            }
        }
    }

    private fun setupList() {
        mainListAdapter = PagingListAdapter()
        //recyclerView = findViewById(R.id.recyclerView)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = mainListAdapter
        }
    }

    private fun setupViewModel() {
        viewModel =
            ViewModelProvider(
                this,
                PagingViewModelFactory(APIService.getApiService())
            )[PagingViewModel::class.java]
    }


}