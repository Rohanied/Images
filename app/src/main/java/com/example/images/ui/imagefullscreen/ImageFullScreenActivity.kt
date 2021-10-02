package com.example.images.ui.imagefullscreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import com.bumptech.glide.Glide
import com.example.images.databinding.ActivityImageFullScreenBinding

class ImageFullScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityImageFullScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)

        binding = ActivityImageFullScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)


        supportActionBar?.hide()

        val imageUrl = intent.extras!!["imageUrl"] as String

        Glide.with(this).load(imageUrl).into(binding.fullScreenImage)
    }
}