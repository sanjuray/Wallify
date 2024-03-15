package com.example.wallify.ui_packages.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.example.wallify.databinding.FragmentCategoryBinding
import com.example.wallify.ui_packages.adapters.RecyclerViewAdapter
import com.example.wallify.ui_packages.model.Data
import com.example.wallify.ui_packages.paging.loadingstate.LoaderStateAdapter
import com.example.wallify.ui_packages.utils.WallInterfaceListener
import com.example.wallify.ui_packages.viewmodels.CategoriesViewModel
import com.example.wallify.ui_packages.viewmodels.CategoryViewModelFactory
import kotlinx.coroutines.launch

class CategoryFragment : Fragment(),WallInterfaceListener {

    private lateinit var viewModel: CategoriesViewModel
    private val args: CategoryFragmentArgs by navArgs()
    private lateinit var binding: FragmentCategoryBinding
    private var recyclerViewAdapter: RecyclerViewAdapter = RecyclerViewAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCategoryBinding.inflate(inflater)
        initViewModel()
        categoryName()
        callBack()
        initRecyclerView()
        return binding.root
    }

    private fun initViewModel(){
        viewModel = ViewModelProvider(
            this,
            CategoryViewModelFactory(args.categoryName)
        )[CategoriesViewModel::class.java]
        viewModel.data.observe(viewLifecycleOwner){
            lifecycleScope.launch {
                recyclerViewAdapter.submitData(it)
            }
        }
    }

     private fun initRecyclerView() {
        val layoutManager = GridLayoutManager(context,3)
        binding.categoryRecyclerview.layoutManager = layoutManager
        binding.categoryRecyclerview.adapter = recyclerViewAdapter.withLoadStateHeaderAndFooter(
            header = LoaderStateAdapter{recyclerViewAdapter.retry()},
            footer = LoaderStateAdapter{recyclerViewAdapter.retry()}
        )

        recyclerViewAdapter.addLoadStateListener { loadState->
            binding.categoryRecyclerview.isVisible = loadState.refresh is LoadState.NotLoading
            binding.categoryProgressBar.isVisible = loadState.refresh is LoadState.Loading
            binding.categoryRetry.isVisible = loadState.refresh is LoadState.Error
            handleError(loadState)
        }

        binding.categoryRetry.setOnClickListener{
            recyclerViewAdapter.retry()
        }
    }
    
    private fun categoryName(){
        binding.categoryNameText.text = args.categoryName
    }

    private fun callBack(){
        binding.categoryNameText.setOnClickListener{
            findNavController().popBackStack()
        }
    }

//    private fun TextView.onLeftDrawableClicked(onClicked: (view:TextView) -> Unit){
//        this.setOnTouchListener{v,event->
//            var hasConsumed = false
//            if(v is TextView){
//                if(event.x >= v.width - v.totalPaddingRight){
//                    if(event.action == MotionEvent.ACTION_UP){
//                        onClicked(this)
//                    }
//                    hasConsumed = true
//                }
//            }
//            hasConsumed
//        }
//    }

    override fun onClickItem(data: Data, view: View) {
        val imageData = arrayOf(data.blurHash,data.fullImageUrl)
        Navigation.findNavController(view)
            .navigate(CategoryFragmentDirections.actionCategoryFragmentToDownloadFragment(imageData))
    }

    private fun handleError(loadStates: CombinedLoadStates){
        val errorState = loadStates.source.append as? LoadState.Error
            ?: loadStates.source.prepend as? LoadState.Error
        errorState?.let{
            Toast.makeText(context,"Try again!!", Toast.LENGTH_SHORT).show()
        }
    }

}