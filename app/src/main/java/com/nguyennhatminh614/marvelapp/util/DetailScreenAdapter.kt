package com.nguyennhatminh614.marvelapp.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nguyennhatminh614.marvelapp.data.model.DetailListItem
import com.nguyennhatminh614.marvelapp.data.model.DetailType
import com.nguyennhatminh614.marvelapp.databinding.ItemLayoutTitleBinding
import com.nguyennhatminh614.marvelapp.databinding.LayoutDtoItemBinding

class DetailScreenAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val detailScreenList = mutableListOf<DetailListItem>()

    override fun getItemViewType(position: Int): Int {
        return when (detailScreenList[position].type) {
            DetailType.CONTENT -> ViewType.CONTENT.type
            else -> ViewType.TITLE.type
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when(viewType) {
            ViewType.TITLE.type -> ViewHolderTitle(ItemLayoutTitleBinding.inflate(layoutInflater, parent, false))
            else -> ViewHolderContent(LayoutDtoItemBinding.inflate(layoutInflater, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = detailScreenList[position]
        when(data.type) {
            DetailType.TITLE -> (holder as? ViewHolderTitle)?.bindItem(data)
            DetailType.CONTENT -> (holder as? ViewHolderContent)?.bindItem(data)
        }
    }

    override fun getItemCount(): Int = detailScreenList.size

    fun updateDataItem(detailCategoryList: MutableList<DetailListItem>) {
        this.detailScreenList.clear()
        this.detailScreenList.addAll(detailCategoryList)
        notifyDataSetChanged()
    }

    inner class ViewHolderTitle(val binding: ItemLayoutTitleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(item : DetailListItem) {
            binding.textBigTitle.text = item.content
        }
    }

    inner class ViewHolderContent(val binding: LayoutDtoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(item: DetailListItem) {
            binding.apply {
                textLinkNavigate.text = item.content
            }
        }
    }

    enum class ViewType(val type: Int) {
        TITLE(0),
        CONTENT(1)
    }
}
