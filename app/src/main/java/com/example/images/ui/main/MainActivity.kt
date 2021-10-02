package com.example.images.ui.main

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
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

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        getImages("")

        binding.searchIcon.setOnClickListener {
            hideKeyboard()
            binding.progressBar.visibility = View.VISIBLE
            if(binding.searchEditText.text.isNullOrEmpty()){
                Toast.makeText(this,"Search Text can't be empty", Toast.LENGTH_LONG).show()
            }else{
               getImages(binding.searchEditText.text.toString())
            }
        }


    }

    override fun onClickImage(imageUrl: String) {
        val intent = Intent(this,ImageFullScreenActivity::class.java)
        intent.putExtra("imageUrl", imageUrl)
        startActivity(intent)
    }


    private fun getImages(q: String){
        val apiInterface = ApiInterface.create().getImages("23650555-72bd02d03d216cdcc1ea6df1a", "photo", "all", q)

        apiInterface.enqueue(object : Callback<Response>{
            override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
                binding.progressBar.visibility = View.GONE
                val images = response.body()?.hits
                if(images?.isNotEmpty()!!)
                binding.imageRecyclerView.apply {
                    layoutManager = LinearLayoutManager(this@MainActivity)
                    adapter = ImageAdapter(this@MainActivity,images, this@MainActivity)
                }
                else{
                    binding.imageRecyclerView.apply {
                        layoutManager = LinearLayoutManager(this@MainActivity)
                        adapter = ImageAdapter(this@MainActivity,images, this@MainActivity)
                    }
                    Toast.makeText(this@MainActivity,"No Results found", Toast.LENGTH_LONG).show()

                }
            }

            override fun onFailure(call: Call<Response>, t: Throwable) {
                binding.progressBar.visibility = View.GONE
                Toast.makeText(this@MainActivity,"Error : $t. Try Again", Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}