package com.example.wallify.ui_packages.fragments

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.example.wallify.databinding.FragmentPopularBinding
import com.example.wallify.ui_packages.adapters.RecyclerViewAdapter
import com.example.wallify.ui_packages.base.BaseFragment
import com.example.wallify.ui_packages.paging.loadingstate.LoaderStateAdapter
import com.example.wallify.ui_packages.utils.WallInterfaceListener
import com.example.wallify.ui_packages.viewmodels.PopularViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PopularFragment : BaseFragment<FragmentPopularBinding>(
    FragmentPopularBinding::inflate
), WallInterfaceListener {
    override var recyclerViewAdapter: RecyclerViewAdapter = RecyclerViewAdapter(this)

    private val viewModel : PopularViewModel by viewModels()

    override fun initViewModel() {
//        Toast.makeText(context,"Checking", Toast.LENGTH_SHORT).show()
        lifecycleScope.launch{
            viewModel.popularPage.collectLatest {
                recyclerViewAdapter.submitData(it)
            }
        }
    }

    override fun initRecyclerView() {
        val layoutManager = GridLayoutManager(context,3)
        binding.popularRecyclerView.layoutManager = layoutManager
        binding.popularRecyclerView.adapter = recyclerViewAdapter.withLoadStateHeaderAndFooter(
            header = LoaderStateAdapter{recyclerViewAdapter.retry()},
            footer = LoaderStateAdapter{recyclerViewAdapter.retry()}
        )

        recyclerViewAdapter.addLoadStateListener { loadState->
            binding.popularRecyclerView.isVisible = loadState.refresh is LoadState.NotLoading
            binding.popularProgressBar.isVisible = loadState.refresh is LoadState.Loading
            binding.popularRetry.isVisible = loadState.refresh is LoadState.Error
            handleError(loadState)
        }

        binding.popularRetry.setOnClickListener{
            recyclerViewAdapter.retry()
        }
    }

}