package com.prateek.mycbseguide.ui.activities

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.prateek.mycbseguide.R
import com.prateek.mycbseguide.constants.RequestStatus
import com.prateek.mycbseguide.databinding.ActivityMainBinding
import com.prateek.mycbseguide.ui.adapters.CategoriesAdapter
import com.prateek.mycbseguide.ui.viewmodels.CategoriesViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint class MainActivity @Inject constructor() : ParentActivity() {

    private val categoriesViewModel by viewModels<CategoriesViewModel>()
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: CategoriesAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        supportActionBar?.hide()

        adapter = CategoriesAdapter()
        binding.mainRecyclerView.layoutManager = GridLayoutManager(this, 3)
        binding.mainRecyclerView.adapter = adapter


        // fetch again if retry button is clicked or refreshed
        binding.retryButton.setOnClickListener{ categoriesViewModel.fetchCategories() }
        binding.listTokensRefreshLayout.setOnRefreshListener{ categoriesViewModel.fetchCategories() }

        // to observe the status of network request being sent to repository, and setting the UI accordingly
        categoriesViewModel.requestStatus.observe(this){
            binding.animation.visibility = View.GONE
            binding.listTokensRefreshLayout.isRefreshing = false
            when(it){
                RequestStatus.INIT -> {}
                RequestStatus.SUCCESS -> setSuccessUi()
                RequestStatus.OFFLINE -> setOfflineUi()
                RequestStatus.LOADING -> setLoadingUi()
                else -> setFailedUi()
            }
        }

        // to observe the live data of list of categories being posted by view model
        categoriesViewModel.categoriesLiveData.observe(this){ adapter.differ.submitList(it) }

        // to fetch all categories at starting the application
        categoriesViewModel.fetchCategories()

    }

    private fun setSuccessUi() {
        binding.animation.visibility = View.GONE
        binding.mainRecyclerView.visibility = View.VISIBLE
        binding.retryButton.visibility = View.GONE
    }
    private fun setFailedUi() {
        binding.animation.setAnimation(R.raw.error)
        binding.animation.visibility = View.VISIBLE
        binding.mainRecyclerView.visibility = View.INVISIBLE
        binding.retryButton.visibility = View.VISIBLE
    }
    private fun setLoadingUi() {
        binding.listTokensRefreshLayout.isRefreshing = true
        binding.mainRecyclerView.visibility = View.INVISIBLE
        binding.retryButton.visibility = View.GONE
    }
    private fun setOfflineUi() {
        binding.animation.setAnimation(R.raw.offline)
        binding.animation.visibility = View.VISIBLE
        binding.mainRecyclerView.visibility = View.INVISIBLE
        binding.retryButton.visibility = View.VISIBLE
    }

}