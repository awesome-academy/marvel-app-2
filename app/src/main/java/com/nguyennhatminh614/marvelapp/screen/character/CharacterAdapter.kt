package com.nguyennhatminh614.marvelapp.screen.character

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nguyennhatminh614.marvelapp.R
import com.nguyennhatminh614.marvelapp.data.model.Character
import com.nguyennhatminh614.marvelapp.databinding.CharacterItemLayoutBinding
import com.nguyennhatminh614.marvelapp.util.OnClickFavoriteItemInterface
import com.nguyennhatminh614.marvelapp.util.OnClickItemInterface
import com.nguyennhatminh614.marvelapp.util.extensions.loadGlideImageFromUrl

class CharacterAdapter : RecyclerView.Adapter<CharacterAdapter.ViewHolder>() {

    private val listCharacter = mutableListOf<Character>()
    private var binding: CharacterItemLayoutBinding? = null
    private var clickFavoriteItemInterface: OnClickFavoriteItemInterface<Character>? = null
    private var clickItemInterface: OnClickItemInterface<Character>? = null

    override fun getItemCount(): Int = listCharacter.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = CharacterItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding!!)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(listCharacter[position])
    }

    fun registerClickFavoriteItemInterface(clickFavoriteItemInterface: OnClickFavoriteItemInterface<Character>) {
        this.clickFavoriteItemInterface = clickFavoriteItemInterface
    }

    fun registerClickItemInterface(clickItemInterface: OnClickItemInterface<Character>) {
        this.clickItemInterface = clickItemInterface
    }

    fun updateFavoriteItem(listFavoriteCharacter: MutableList<Character>) {
        for (character in listFavoriteCharacter) {
            listCharacter.filter { return@filter it.id == character.id }.also {
                if (it.isNotEmpty()) {
                    it[0].isFavorite = true
                }
            }
        }
    }

    fun updateItemList(listCharacter: MutableList<Character>) {
        this.listCharacter.clear()
        this.listCharacter.addAll(listCharacter)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: CharacterItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(character: Character) {
            binding.apply {
                imageCharacter.loadGlideImageFromUrl(
                    root.context,
                    character.thumbnailLink,
                    R.drawable.character_image
                )
                textCharacterName.text = character.name
                textDescription.text = character.description
                if (character.isFavorite) {
                    buttonFavorite.setImageResource(R.drawable.ic_favorite_checked)
                } else {
                    buttonFavorite.setImageResource(R.drawable.ic_favorite)
                }
                buttonFavorite.setOnClickListener {
                    character.isFavorite = !character.isFavorite

                    if (character.isFavorite) {
                        buttonFavorite.setImageResource(R.drawable.ic_favorite_checked)
                        clickFavoriteItemInterface?.onFavoriteItem(character)
                    } else {
                        buttonFavorite.setImageResource(R.drawable.ic_favorite)
                        clickFavoriteItemInterface?.onUnfavoriteItem(character)
                    }
                }

                characterItem.setOnClickListener {
                    clickItemInterface?.onClickItem(character)
                }
            }
        }
    }
}
