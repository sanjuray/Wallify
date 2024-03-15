package com.example.wallify.ui_packages.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.viewbinding.ViewBinding
import com.example.wallify.ui_packages.adapters.RecyclerViewAdapter
import com.example.wallify.ui_packages.fragments.MainFragmentDirections
import com.example.wallify.ui_packages.model.Data
import com.example.wallify.ui_packages.utils.WallInterfaceListener

abstract class BaseFragment<VB: ViewBinding>(
    private val layoutInflater: (inflater : LayoutInflater)-> VB
    ): Fragment(),WallInterfaceListener {


    private var _binding: VB ?= null
    val binding: VB
        get() = _binding as VB

    abstract var recyclerViewAdapter: RecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = layoutInflater.invoke(inflater)
        initViewModel()
        initRecyclerView()

        if(_binding == null){
            throw IllegalArgumentException("binding cannot be null")
        }

        return binding.root
    }

    abstract fun initViewModel()
    abstract fun initRecyclerView()

    override fun onClickItem(data: Data, view: View) {
        val imageData = arrayOf(data.blurHash,data.fullImageUrl)
        Navigation.findNavController(view)
            .navigate(MainFragmentDirections.actionMainFragmentToDownloadFragment(imageData))
    }

    fun handleError(loadStates: CombinedLoadStates){
        val errorState = loadStates.source.append as? LoadState.Error
            ?: loadStates.source.prepend as? LoadState.Error
        errorState?.let{
            Toast.makeText(context,"Try again!!",Toast.LENGTH_SHORT).show()
        }
    }
}