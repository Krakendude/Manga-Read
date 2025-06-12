package com.example.manga_read.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.manga_read.R
import com.example.manga_read.data.MangaItem
import com.example.manga_read.utils.MangaService
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MangaAdapter(private var mangaList: List<MangaItem>) : RecyclerView.Adapter<MangaAdapter.MangaViewHolder>() {

    inner class MangaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageCover: ImageView = itemView.findViewById(R.id.coverImageView)
        val titleText: TextView = itemView.findViewById(R.id.nameTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MangaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_manga, parent, false)
        return MangaViewHolder(view)
    }

    override fun getItemCount(): Int = mangaList.size

    override fun onBindViewHolder(holder: MangaViewHolder, position: Int) {
        val manga = mangaList[position]
        val title = manga.attributes.title["en"] ?: "Sin título"
        holder.titleText.text = title

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val coverResponse = MangaService.getInstance().getCover(manga.id)
                val fileName = coverResponse.data.firstOrNull()?.attributes?.fileName
                if (fileName != null) {
                    val imageUrl = "https://uploads.mangadex.org/covers/${manga.id}/$fileName"
                    withContext(Dispatchers.Main) {
                        Picasso.get().load(imageUrl).into(holder.imageCover)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateData(newList: List<MangaItem>) {
        mangaList = newList
        notifyDataSetChanged()
    }
}