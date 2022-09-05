package com.nguyennhatminh614.marvelapp.screen.creator

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nguyennhatminh614.marvelapp.R
import com.nguyennhatminh614.marvelapp.data.model.Creator
import com.nguyennhatminh614.marvelapp.databinding.CreatorItemLayoutBinding
import com.nguyennhatminh614.marvelapp.util.OnClickItemInterface
import com.nguyennhatminh614.marvelapp.util.extensions.loadGlideImageFromUrl

class CreatorAdapter : RecyclerView.Adapter<CreatorAdapter.ViewHolder>() {
    private val listCreator = mutableListOf<Creator>()
    private var clickItemInterface: OnClickItemInterface<Creator>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CreatorItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(listCreator[position])
    }

    override fun getItemCount(): Int = listCreator.size

    fun registerClickItemListener(clickItemInterface: OnClickItemInterface<Creator>) {
        this.clickItemInterface = clickItemInterface
    }

    fun updateItemData(listCreator: MutableList<Creator>?) {
        this.listCreator.clear()
        listCreator?.let { this.listCreator.addAll(it) }
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: CreatorItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindItem(creator: Creator) {
            binding.apply {
                imageCreator.loadGlideImageFromUrl(
                    root.context,
                    creator.thumbnailLink,
                    R.drawable.image_creator_default
                )
                textCreatorName.text = creator.name

                creatorItem.setOnClickListener {
                    clickItemInterface?.onClickItem(creator)
                }
            }
        }
    }
}
