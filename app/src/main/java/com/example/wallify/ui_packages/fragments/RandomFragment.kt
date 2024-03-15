package com.example.wallify.ui_packages.fragments

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.example.wallify.databinding.FragmentRandomBinding
import com.example.wallify.ui_packages.adapters.RecyclerViewAdapter
import com.example.wallify.ui_packages.base.BaseFragment
import com.example.wallify.ui_packages.paging.loadingstate.LoaderStateAdapter
import com.example.wallify.ui_packages.utils.WallInterfaceListener
import com.example.wallify.ui_packages.viewmodels.RandomViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class RandomFragment : BaseFragment<FragmentRandomBinding>(
    FragmentRandomBinding::inflate
),WallInterfaceListener {
    override var recyclerViewAdapter: RecyclerViewAdapter = RecyclerViewAdapter(this)

    private val viewModel : RandomViewModel by viewModels()

    override fun initViewModel() {
//        Toast.makeText(context,"Checking", Toast.LENGTH_SHORT).show()
        lifecycleScope.launch{
            viewModel.randomPage.collectLatest {
                recyclerViewAdapter.submitData(it)
            }
        }
    }

    override fun initRecyclerView() {
        val layoutManager = GridLayoutManager(context,3)
        binding.randomRecyclerView.layoutManager = layoutManager
        binding.randomRecyclerView.adapter = recyclerViewAdapter.withLoadStateHeaderAndFooter(
            header = LoaderStateAdapter{recyclerViewAdapter.retry()},
            footer = LoaderStateAdapter{recyclerViewAdapter.retry()}
        )

        recyclerViewAdapter.addLoadStateListener { loadState->
            binding.randomRecyclerView.isVisible = loadState.refresh is LoadState.NotLoading
            binding.randomProgressBar.isVisible = loadState.refresh is LoadState.Loading
            binding.randomRetry.isVisible = loadState.refresh is LoadState.Error
            handleError(loadState)
        }

        binding.randomRetry.setOnClickListener{
            recyclerViewAdapter.retry()
        }
    }
}