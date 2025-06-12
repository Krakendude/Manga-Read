package com.example.manga_read.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.manga_read.R
import com.example.manga_read.adapters.MangaAdapter
import com.example.manga_read.data.MangaItem
import com.example.manga_read.databinding.ActivityMainBinding
import com.example.manga_read.utils.MangaService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var adapter: MangaAdapter

    var mangaList: List<MangaItem> = emptyList()

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

        adapter = MangaAdapter(mangaList)

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)

        searchManga("jojo") { result ->
            mangaList = result
            adapter.updateData(mangaList)
        }


    }

    fun searchManga(query: String, onResult: (List<MangaItem>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = MangaService.getInstance().searchManga(query)
                withContext(Dispatchers.Main) {
                    onResult(response.data)
                }
            } catch (e: Exception) {
                Log.e("SearchError", e.message ?: "Error")
            }
        }
    }

}