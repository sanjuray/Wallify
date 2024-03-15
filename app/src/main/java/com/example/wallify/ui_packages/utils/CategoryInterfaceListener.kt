package com.example.wallify.ui_packages.utils

import android.view.View
import com.example.wallify.ui_packages.model.Category

interface CategoryInterfaceListener {
    fun onClickCategory(category: Category, view:View)
}