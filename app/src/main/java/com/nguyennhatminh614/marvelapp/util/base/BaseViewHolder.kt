package com.nguyennhatminh614.marvelapp.util.base

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseViewHolder<T>(
    binding: ViewBinding,
    clickItem: (T) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private var item: T? = null

    init {
        itemView.setOnClickListener { item?.let { it1 -> clickItem(it1) } }
    }

    open fun bindView(item: T) {
        this.item = item
    }
}
