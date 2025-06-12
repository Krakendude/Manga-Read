package com.example.manga_read.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.manga_read.R
import com.example.manga_read.data.MangaItem
import com.example.manga_read.databinding.ActivityMainBinding
import com.example.manga_read.utils.mangaService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun searchManga(query: String, onResult: (List<MangaItem>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = mangaService.getInstance().searchManga(query)
                withContext(Dispatchers.Main) {
                    onResult(response.data)
                }
            } catch (e: Exception) {
                Log.e("SearchError", e.message ?: "Error")
            }
        }
    }

}