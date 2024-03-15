package com.example.wallify.ui_packages.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wallify.R
import com.example.wallify.databinding.CategoryItemRowBinding
import com.example.wallify.ui_packages.model.Category
import com.example.wallify.ui_packages.utils.CategoryInterfaceListener

class CategoryAdapter(private val categoryList: List<Category>,
        private val listener: CategoryInterfaceListener
    ): RecyclerView.Adapter<CategoryAdapter.MyCategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyCategoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.category_item_row,
                parent,
                false
            )
        return MyCategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyCategoryViewHolder, position: Int) {
        val currentCategory = categoryList[position]
        holder.binding.apply {
            categoryName.text = currentCategory.categoryName

            Glide.with(holder.itemView.context)
                .load(currentCategory.imageUrl)
                .centerCrop()
                .error(R.drawable.baseline_error)
                .into(categoryImage)
        }

        holder.itemView.setOnClickListener {
            listener.onClickCategory(currentCategory,it)
        }
    }

    override fun getItemCount() = categoryList.size

    inner class MyCategoryViewHolder(view: View): RecyclerView.ViewHolder(view){
        val binding = CategoryItemRowBinding.bind(view)
    }
}