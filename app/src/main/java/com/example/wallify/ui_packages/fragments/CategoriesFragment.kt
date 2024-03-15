package com.example.wallify.ui_packages.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.example.wallify.databinding.FragmentCategoriesBinding
import com.example.wallify.ui_packages.adapters.CategoryAdapter
import com.example.wallify.ui_packages.model.Category
import com.example.wallify.ui_packages.utils.ApiListCategory
import com.example.wallify.ui_packages.utils.CategoryInterfaceListener

class CategoriesFragment : Fragment(),CategoryInterfaceListener {

    private lateinit var binding: FragmentCategoriesBinding
    private lateinit var recyclerViewAdapter: CategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        // Inflate the layout for this fragment
        binding = FragmentCategoriesBinding.inflate(inflater,container,false)
        initRecyclerViewAdapter()
        return binding.root
    }

    private fun initRecyclerViewAdapter(){
        val layoutManager  = GridLayoutManager(context,2)
        recyclerViewAdapter = CategoryAdapter(ApiListCategory.list,this)
        binding.categoriesRecyclerView.layoutManager = layoutManager
        binding.categoriesRecyclerView.adapter = recyclerViewAdapter
    }

    override fun onClickCategory(category: Category, view: View) {
        val action = MainFragmentDirections.actionMainFragmentToCategoryFragment(category.categoryName)
        Navigation.findNavController(view).navigate(action)
    }

}