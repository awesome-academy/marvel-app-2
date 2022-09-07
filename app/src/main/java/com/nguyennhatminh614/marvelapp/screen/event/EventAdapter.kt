package com.nguyennhatminh614.marvelapp.screen.event

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nguyennhatminh614.marvelapp.R
import com.nguyennhatminh614.marvelapp.data.model.Event
import com.nguyennhatminh614.marvelapp.databinding.EventItemLayoutBinding
import com.nguyennhatminh614.marvelapp.util.OnClickItemInterface
import com.nguyennhatminh614.marvelapp.util.extensions.loadGlideImageFromUrl

class EventAdapter : RecyclerView.Adapter<EventAdapter.ViewHolder>() {

    private var listEvent = mutableListOf<Event>()
    private var clickItemInterface: OnClickItemInterface<Event>? = null

    override fun getItemCount(): Int = listEvent.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = EventItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(listEvent[position])
    }

    fun registerClickItemListener(clickItemInterface: OnClickItemInterface<Event>) {
        this.clickItemInterface = clickItemInterface
    }

    fun updateItemdata(list: MutableList<Event>?) {
        listEvent.clear()
        list?.let { listEvent.addAll(it) }
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding : EventItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindItem(event: Event) {
            binding.apply {
                imageEvent.loadGlideImageFromUrl(
                    root.context,
                    event.thumbnailLink,
                    R.drawable.character_image,
                )
                textEventName.text = event.title
                textStartDate.text = event.startDate
                textEndDate.text = event.endDate

                layoutEventItem.setOnClickListener {
                    clickItemInterface?.onClickItem(event)
                }
            }
        }
    }
}
