package com.nguyennhatminh614.marvelapp.screen.mainscreen

import android.graphics.Color
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nguyennhatminh614.marvelapp.data.model.MenuDrawerItem
import com.nguyennhatminh614.marvelapp.databinding.MenuItemLayoutBinding

class MenuItemAdapter(
    private val listItem: List<MenuDrawerItem>,
    private val currentPosition: Int,
) : RecyclerView.Adapter<MenuItemAdapter.ViewHolder>() {

    private lateinit var binding: MenuItemLayoutBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = MenuItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val menuItem = listItem[position]
        binding.apply {
            imageMenuItem.setImageResource(menuItem.iconResourceID)
            textMenuItem.text = menuItem.title

            if (position == currentPosition) {
                imageMenuItem.setColorFilter(DEFAULT_COLOR, PorterDuff.Mode.SRC_ATOP)
                textMenuItem.setTextColor(DEFAULT_COLOR)
            }
        }
    }

    override fun getItemCount(): Int = listItem.size

    inner class ViewHolder(binding: MenuItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    companion object {
        private const val DEFAULT_COLOR = Color.MAGENTA
    }
}
