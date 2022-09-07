package com.nguyennhatminh614.marvelapp.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nguyennhatminh614.marvelapp.data.model.DtoItem
import com.nguyennhatminh614.marvelapp.databinding.LayoutDtoItemBinding

class DTOItemAdapter<T : DtoItem> :
    RecyclerView.Adapter<DTOItemAdapter<T>.ViewHolder>() {

    private var listDTOItem = mutableListOf<T>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            LayoutDtoItemBinding.inflate(
                LayoutInflater.from(parent.context), parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(listDTOItem[position])
    }

    override fun getItemCount(): Int = listDTOItem.size

    fun updateDataItem(listDtoItem: MutableList<T>){
        this.listDTOItem.clear()
        this.listDTOItem.addAll(listDtoItem)
        notifyDataSetChanged()
    }

    fun updateDTOAdapter(
        listItem: MutableList<T>,
        emptyListEvent: () -> Unit,
    ) {
        if (listItem.isNotEmpty()){
            updateDataItem(listItem)
        } else {
            emptyListEvent()
        }
    }

    inner class ViewHolder(val binding: LayoutDtoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(dtoItem: T) {
            binding.apply {
                textLinkNavigate.text = dtoItem.textDescription
            }
        }
    }
}
