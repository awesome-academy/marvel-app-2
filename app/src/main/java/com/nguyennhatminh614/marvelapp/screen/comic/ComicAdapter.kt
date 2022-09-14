package com.nguyennhatminh614.marvelapp.screen.comic

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nguyennhatminh614.marvelapp.R
import com.nguyennhatminh614.marvelapp.data.model.Comic
import com.nguyennhatminh614.marvelapp.databinding.ComicItemLayoutBinding
import com.nguyennhatminh614.marvelapp.util.OnClickFavoriteItemInterface
import com.nguyennhatminh614.marvelapp.util.OnClickItemInterface
import com.nguyennhatminh614.marvelapp.util.extensions.loadGlideImageFromUrl

class ComicAdapter : RecyclerView.Adapter<ComicAdapter.ViewHolder>() {

    private val listComic = mutableListOf<Comic>()
    private val listFavoriteComic = mutableListOf<Comic>()
    private var clickFavoriteItemInterface: OnClickFavoriteItemInterface<Comic>? = null
    private var clickItemInterface: OnClickItemInterface<Comic>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ComicItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(listComic[position])
    }

    override fun getItemCount(): Int = listComic.size

    fun registerClickItemListener(clickItemInterface: OnClickItemInterface<Comic>) {
        this.clickItemInterface = clickItemInterface
    }

    fun registerClickFavoriteItemListener(clickFavoriteItemInterface: OnClickFavoriteItemInterface<Comic>) {
        this.clickFavoriteItemInterface = clickFavoriteItemInterface
    }

    fun updateListComic(listComic: MutableList<Comic>) {
        this.listComic.clear()
        listFavoriteComic.forEach { comic ->
            val elem = listComic.singleOrNull {it.id == comic.id}
            if (elem != null) {
                elem.isFavorite = true
            }
        }
        this.listComic.addAll(listComic)
        notifyDataSetChanged()
    }

    fun updateFavoriteComicList(listFavoriteComic: MutableList<Comic>) {
        this.listFavoriteComic.clear()
        this.listFavoriteComic.addAll(listFavoriteComic)
    }

    inner class ViewHolder(val binding: ComicItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(comic: Comic) {
            binding.apply {
                imageComic.loadGlideImageFromUrl(
                    root.context,
                    comic.thumbnailLink,
                    R.drawable.image_comic_default
                )

                textComicName.text = comic.title
                textComicDescription.text = comic.description

                if (comic.isFavorite) {
                    buttonFavorite.setImageResource(R.drawable.ic_favorite_checked)
                } else {
                    buttonFavorite.setImageResource(R.drawable.ic_favorite)
                }

                buttonFavorite.setOnClickListener {
                    if (comic.isFavorite) {
                        buttonFavorite.setImageResource(R.drawable.ic_favorite)
                        clickFavoriteItemInterface?.onUnfavoriteItem(comic)
                    } else {
                        buttonFavorite.setImageResource(R.drawable.ic_favorite_checked)
                        clickFavoriteItemInterface?.onFavoriteItem(comic)
                    }
                    comic.isFavorite = comic.isFavorite.not()
                }

                layoutItemComic.setOnClickListener {
                    clickItemInterface?.onClickItem(comic)
                }
            }
        }
    }
}
