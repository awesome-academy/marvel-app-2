package com.nguyennhatminh614.marvelapp.util.base

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

abstract class BaseAdapter<T, VH : BaseViewHolder<T>>(
    diffUtil: DiffUtil.ItemCallback<T>
) : ListAdapter<T, VH>(diffUtil), SubmitData<T> {

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bindView(getItem(position))
    }

    override fun submitData(data: List<T>?) {
        submitList(data)
    }
}
