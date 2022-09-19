package com.nguyennhatminh614.marvelapp.screen.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nguyennhatminh614.marvelapp.R
import com.nguyennhatminh614.marvelapp.data.model.FavoriteItem
import com.nguyennhatminh614.marvelapp.databinding.ItemFavoriteLayoutBinding
import com.nguyennhatminh614.marvelapp.databinding.ItemLayoutTitleBinding
import com.nguyennhatminh614.marvelapp.util.OnClickItemInterface
import com.nguyennhatminh614.marvelapp.util.OnLongClickItemInterface
import com.nguyennhatminh614.marvelapp.util.extensions.loadGlideImageFromUrl

class FavoriteAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val listFavoriteItem = mutableListOf<Any>()
    private var clickItemInterface: OnClickItemInterface<FavoriteItem>? = null
    private var longClickItemInterface: OnLongClickItemInterface<FavoriteItem>? = null

    override fun getItemViewType(position: Int): Int {
        return when(listFavoriteItem[position]) {
            is String -> ViewType.TITLE.type
            is FavoriteItem -> ViewType.DETAIL.type
            else -> ViewType.NON_VIEW_TYPE.type
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            ViewType.DETAIL.type -> {
                val binding = ItemFavoriteLayoutBinding.inflate(inflater, parent, false)
                ViewHolderFavoriteDetail(binding)
            }
            else -> {
                val binding = ItemLayoutTitleBinding.inflate(inflater, parent, false)
                ViewHolderTitle(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = listFavoriteItem[position]
        when (data) {
            is String -> (holder as? ViewHolderTitle)?.bindItem(data)
            is FavoriteItem -> (holder as? ViewHolderFavoriteDetail)?.bindItem(data)
        }
    }

    override fun getItemCount(): Int = listFavoriteItem.size

    fun updateDataItem(listFavoriteItem: MutableList<Any>) {
        this.listFavoriteItem.clear()
        this.listFavoriteItem.addAll(listFavoriteItem)
        notifyDataSetChanged()
    }

    fun registerClickItemInterface(clickItemInterface: OnClickItemInterface<FavoriteItem>) {
        this.clickItemInterface = clickItemInterface
    }

    fun registerLongClickItemInterface(longClickItemInterface: OnLongClickItemInterface<FavoriteItem>) {
        this.longClickItemInterface = longClickItemInterface
    }

    inner class ViewHolderTitle(val binding: ItemLayoutTitleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(title: String) {
            binding.textBigTitle.text = title
        }
    }

    inner class ViewHolderFavoriteDetail(val binding: ItemFavoriteLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(item: FavoriteItem) {
            binding.apply {
                imageFavoriteItem.loadGlideImageFromUrl(
                    root.context, item.thumbnailLink,
                    R.drawable.character_image
                )
                textFavoriteItem.text = item.title

                layoutFavoriteItem.setOnClickListener {
                    clickItemInterface?.onClickItem(item)
                }

                layoutFavoriteItem.setOnLongClickListener {
                    longClickItemInterface?.onLongClickItem(item)
                    return@setOnLongClickListener true
                }
            }
        }
    }

    enum class ViewType(val type: Int) {
        TITLE(0),
        DETAIL(1),
        NON_VIEW_TYPE(-1)
    }
}
