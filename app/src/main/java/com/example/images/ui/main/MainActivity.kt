package com.example.images.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.images.beans.Response
import com.example.images.data.remote.ApiInterface
import com.example.images.databinding.ActivityMainBinding
import com.example.images.ui.imagefullscreen.ImageFullScreenActivity
import retrofit2.Call
import retrofit2.Callback

class MainActivity : AppCompatActivity(), ImageAdapter.OnClickItem {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val apiInterface = ApiInterface.create().getImages("23650555-72bd02d03d216cdcc1ea6df1a", "photo", "all")

        apiInterface.enqueue(object : Callback<Response>{
            override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
                binding.progressBar.visibility = View.GONE
                val images = response.body()?.hits
                binding.imageRecyclerView.apply {
                    layoutManager = LinearLayoutManager(this@MainActivity)
                    adapter = ImageAdapter(this@MainActivity,images!!, this@MainActivity)
                }
            }

            override fun onFailure(call: Call<Response>, t: Throwable) {
                binding.progressBar.visibility = View.GONE
               Toast.makeText(this@MainActivity,"Error : $t. Try Again", Toast.LENGTH_LONG).show()
            }

        })
    }

    override fun onClickImage(imageUrl: String) {
        val intent = Intent(this,ImageFullScreenActivity::class.java)
        intent.putExtra("imageUrl", imageUrl)
        startActivity(intent)
    }
}