package com.nguyennhatminh614.marvelapp.screen.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nguyennhatminh614.marvelapp.R
import com.nguyennhatminh614.marvelapp.data.model.SearchObject
import com.nguyennhatminh614.marvelapp.databinding.SearchItemLayoutBinding
import com.nguyennhatminh614.marvelapp.util.extensions.loadGlideImageFromUrl

typealias ClickPositionInterface = (Int) -> Unit

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    private val listSearchResult = mutableListOf<SearchObject>()
    private var clickItemInterfaceByPosition : ClickPositionInterface? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = SearchItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(listSearchResult[position])
    }

    override fun getItemCount(): Int = listSearchResult.size

    fun updateDataItem(searchResultList: MutableList<SearchObject>){
        this.listSearchResult.clear()
        this.listSearchResult.addAll(searchResultList)
        notifyDataSetChanged()
    }

    fun registerClickItemInterface(clickItemInterface: ClickPositionInterface) {
        this.clickItemInterfaceByPosition = clickItemInterface
    }

    fun clear() {
        this.listSearchResult.clear()
    }

    inner class ViewHolder(val binding: SearchItemLayoutBinding) : RecyclerView.ViewHolder(binding.root){
        fun bindItem(item: SearchObject) {
            binding.apply {
                imageThumbnailSearchObject.loadGlideImageFromUrl(
                    root.context,
                    item.thumbnailLink,
                    R.drawable.image_comic_default
                )

                textTitleSearchObject.text = item.title
                textSearchObjectCategory.text = item.category

                layoutSearchItem.setOnClickListener {
                    clickItemInterfaceByPosition?.let { it(adapterPosition) }
                }
            }
        }
    }
}
