package com.example.wallify.ui_packages.utils

import android.view.View
import com.example.wallify.ui_packages.model.Data

interface WallInterfaceListener {
    fun onClickItem(data: Data, view: View)
}