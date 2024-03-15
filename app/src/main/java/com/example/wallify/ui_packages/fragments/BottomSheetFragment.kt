package com.example.wallify.ui_packages.fragments

import android.app.DownloadManager
import android.app.WallpaperManager
import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.wallify.R
import com.example.wallify.databinding.BottomDialogSheetBinding
import com.example.wallify.ui_packages.utils.Constants
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.snackbar.Snackbar
import java.io.File

class BottomSheetFragment(private val wallUrl: String): BottomSheetDialogFragment() {

    private lateinit var binding: BottomDialogSheetBinding
    private lateinit var snackBar: Snackbar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomDialogSheetBinding.inflate(inflater)
        initButtons()
        return binding.root
    }

    private fun initButtons(){
        binding.setHomeScreen.setOnClickListener{setAsBackground(Constants.BackgroundSetting.HOME_SCREEN)}
        binding.setLockScreen.setOnClickListener{setAsBackground(Constants.BackgroundSetting.LOCK_SCREEN)}
        binding.setBoth.setOnClickListener{setAsBackground(Constants.BackgroundSetting.BOTH)}
        binding.download.setOnClickListener{downloadImage(wallUrl)}
    }

    private fun setAsBackground(lockOrBackground: Int){
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            try {
                val wallpaperManager = WallpaperManager.getInstance(context)
                val image = activity?.findViewById<ShapeableImageView>(R.id.main_image)
                if(image?.drawable == null){
                    Toast.makeText(context,"in a wait...",Toast.LENGTH_SHORT).show()
                }else{
                    val bitmap = (image.drawable as BitmapDrawable).bitmap
                    wallpaperManager.setBitmap(bitmap,null,true,lockOrBackground)
                    Snackbar.make(binding.root,"Wallpaper is Set",Snackbar.LENGTH_LONG).show()
                }
            }catch (e: Exception){
                Toast.makeText(context,e.message+"",Toast.LENGTH_SHORT).show()
            }
//        }
    }


    private fun downloadImage(url: String){
        try{
            val downloadManger = context?.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

            val imageUrl = Uri.parse(url)
            val request = DownloadManager.Request(imageUrl).apply {
                setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE or DownloadManager.Request.NETWORK_WIFI)
                    .setMimeType("image/*")
                    .setAllowedOverRoaming(false)
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    .setTitle("wool")
                    .setDestinationInExternalPublicDir(
                        Environment.DIRECTORY_PICTURES,
                        File.separator + "wool"+".jpg"
                    )
            }
            downloadManger.enqueue(request)
            Toast.makeText(context,"Downloading...",Toast.LENGTH_SHORT).show()
            snackBar = Snackbar.make(binding.root,"Downloaded",Snackbar.LENGTH_LONG)
                .setAction("Undo") {
                    Toast.makeText(context, "Under progress", Toast.LENGTH_SHORT).show()
                }
            snackBar.show()
        }catch (e: Exception){
            Toast.makeText(context,"Image Download Failed ${e.message}",Toast.LENGTH_LONG).show()
        }
    }

}