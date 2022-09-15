package com.nguyennhatminh614.marvelapp.screen.stories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nguyennhatminh614.marvelapp.R
import com.nguyennhatminh614.marvelapp.data.model.Stories
import com.nguyennhatminh614.marvelapp.databinding.StoriesItemLayoutBinding
import com.nguyennhatminh614.marvelapp.util.OnClickFavoriteItemInterface
import com.nguyennhatminh614.marvelapp.util.OnClickItemInterface
import com.nguyennhatminh614.marvelapp.util.extensions.loadGlideImageFromUrl

class StoriesAdapter : RecyclerView.Adapter<StoriesAdapter.ViewHolder>() {

    private val listStories = mutableListOf<Stories>()
    private var clickItemInterface: OnClickItemInterface<Stories>? = null
    private var clickFavoriteItemInterface: OnClickFavoriteItemInterface<Stories>? = null

    override fun getItemCount(): Int = listStories.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            StoriesItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(listStories[position])
    }

    fun registerOnClickItemInterface(clickItemInterface: OnClickItemInterface<Stories>) {
        this.clickItemInterface = clickItemInterface
    }

    fun registerOnClickFavoriteItemInterface(clickFavoriteItemInterface: OnClickFavoriteItemInterface<Stories>) {
        this.clickFavoriteItemInterface = clickFavoriteItemInterface
    }

    fun updateDataItem(listStories: MutableList<Stories>?) {
        listStories?.let {
            this.listStories.clear()
            this.listStories.addAll(it)
            notifyDataSetChanged()
        }
    }

    fun updateFavoriteItem(listFavoriteStories: MutableList<Stories>?) {
        if (listFavoriteStories != null) {
            for (character in listFavoriteStories) {
                listStories.filter { return@filter it.id == character.id }.also {
                    if (it.isNotEmpty()) {
                        it[0].isFavorite = true

                    }
                }
            }
            notifyDataSetChanged()
        }
    }

    inner class ViewHolder(val binding: StoriesItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(stories: Stories) {
            binding.apply {
                imageStories.loadGlideImageFromUrl(
                    root.context,
                    stories.thumbnailLink,
                    R.drawable.image_comic_default
                )
                textStoriesName.text = stories.title
                textStoriesDescription.text = stories.description

                if (stories.isFavorite) {
                    buttonFavorite.setImageResource(R.drawable.ic_favorite_checked)
                } else {
                    buttonFavorite.setImageResource(R.drawable.ic_favorite)
                }

                buttonFavorite.setOnClickListener {
                    if (stories.isFavorite) {
                        buttonFavorite.setImageResource(R.drawable.ic_favorite)
                        clickFavoriteItemInterface?.onUnfavoriteItem(stories)
                    } else {
                        buttonFavorite.setImageResource(R.drawable.ic_favorite_checked)
                        clickFavoriteItemInterface?.onFavoriteItem(stories)
                    }
                    stories.isFavorite = stories.isFavorite.not()
                }

                layoutStoriesItem.setOnClickListener {
                    clickItemInterface?.onClickItem(stories)
                }
            }
        }
    }
}
