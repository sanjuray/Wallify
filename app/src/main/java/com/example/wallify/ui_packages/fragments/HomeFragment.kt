package com.example.wallify.ui_packages.fragments

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.example.wallify.databinding.FragmentHomeBinding
import com.example.wallify.ui_packages.adapters.RecyclerViewAdapter
import com.example.wallify.ui_packages.base.BaseFragment
import com.example.wallify.ui_packages.paging.loadingstate.LoaderStateAdapter
import com.example.wallify.ui_packages.utils.WallInterfaceListener
import com.example.wallify.ui_packages.viewmodels.HomeViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment<FragmentHomeBinding>(
    FragmentHomeBinding::inflate
), WallInterfaceListener{

    override var recyclerViewAdapter: RecyclerViewAdapter = RecyclerViewAdapter(this)

    private val viewModel : HomeViewModel by viewModels()

    override fun initViewModel() {
//        Toast.makeText(context,"Checking",Toast.LENGTH_SHORT).show()
        lifecycleScope.launch{
            viewModel.homePage.collectLatest {
                recyclerViewAdapter.submitData(it)
            }
        }
    }

    override fun initRecyclerView() {
        val layoutManager = GridLayoutManager(context,3)
        binding.homeRecyclerView.layoutManager = layoutManager
        binding.homeRecyclerView.adapter = recyclerViewAdapter.withLoadStateHeaderAndFooter(
            header = LoaderStateAdapter{recyclerViewAdapter.retry()},
            footer = LoaderStateAdapter{recyclerViewAdapter.retry()}
        )

        recyclerViewAdapter.addLoadStateListener { loadState->
            binding.homeRecyclerView.isVisible = loadState.refresh is LoadState.NotLoading
            binding.progressBar.isVisible = loadState.refresh is LoadState.Loading
            binding.retry.isVisible = loadState.refresh is LoadState.Error
            handleError(loadState)
        }

        binding.retry.setOnClickListener{
            recyclerViewAdapter.retry()
        }
    }

}