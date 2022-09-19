package com.nguyennhatminh614.marvelapp.screen.homepage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nguyennhatminh614.marvelapp.R
import com.nguyennhatminh614.marvelapp.data.model.Character
import com.nguyennhatminh614.marvelapp.data.model.Comic
import com.nguyennhatminh614.marvelapp.data.model.Creator
import com.nguyennhatminh614.marvelapp.databinding.CharacterItemLayoutBinding
import com.nguyennhatminh614.marvelapp.databinding.CreatorItemLayoutBinding
import com.nguyennhatminh614.marvelapp.databinding.ItemLayoutTitleBinding
import com.nguyennhatminh614.marvelapp.util.OnClickFavoriteItemInterface
import com.nguyennhatminh614.marvelapp.util.OnClickItemInterface
import com.nguyennhatminh614.marvelapp.util.constant.Constant
import com.nguyennhatminh614.marvelapp.util.extensions.loadGlideImageFromUrl

const val TITLE_VIEW_TYPE = 0
const val COMIC_VIEW_TYPE = 1
const val CREATOR_VIEW_TYPE = 2
const val CHARACTER_VIEW_TYPE = 3

class HomePageAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val listHomePageContent = mutableListOf<Any>()

    private val listFavoriteCharacter = mutableListOf<Character>()
    private val listFavoriteComic = mutableListOf<Comic>()

    private var clickFavoriteCharacterItemInterface: OnClickFavoriteItemInterface<Character>? = null
    private var clickItemCharacterInterface: OnClickItemInterface<Character>? = null
    private var clickItemCreatorInterface: OnClickItemInterface<Creator>? = null

    override fun getItemViewType(position: Int): Int {
        return when (listHomePageContent[position]) {
            is Character -> ViewType.CHARACTER.type
            is Creator -> ViewType.CREATOR.type
            else -> ViewType.TITLE.type
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            ViewType.CHARACTER.type -> {
                CharacterViewHolder(CharacterItemLayoutBinding.inflate(layoutInflater,
                    parent,
                    false))
            }
            ViewType.CREATOR.type -> {
                CreatorViewHolder(CreatorItemLayoutBinding.inflate(layoutInflater, parent, false))
            }
            else -> {
                ViewHolderTitle(ItemLayoutTitleBinding.inflate(layoutInflater, parent, false))
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val data = listHomePageContent[position]) {
            is Character -> (holder as? HomePageAdapter.CharacterViewHolder)?.bindItem(data)
            is Creator -> (holder as? HomePageAdapter.CreatorViewHolder)?.bindItem(data)
            is String -> (holder as? HomePageAdapter.ViewHolderTitle)?.bindItem(data)
        }
    }

    override fun getItemCount(): Int = listHomePageContent.size

    fun registerClickFavoriteCharacterItemInterface(clickFavoriteItemInterface:
                                                    OnClickFavoriteItemInterface<Character>) {
        this.clickFavoriteCharacterItemInterface = clickFavoriteItemInterface
    }

    fun registerClickItemCharacterInterface(clickItemInterface: OnClickItemInterface<Character>) {
        this.clickItemCharacterInterface = clickItemInterface
    }

    fun registerClickItemCreatorListener(clickItemInterface: OnClickItemInterface<Creator>) {
        this.clickItemCreatorInterface = clickItemInterface
    }

    fun updateDataItem(listItem: MutableList<Any>) {
        this.listHomePageContent.clear()

        val listCharacter = mutableListOf<Character>()
        listItem.filter { return@filter it is Character }.forEach { listCharacter.add(it as Character) }

        val listComic = mutableListOf<Comic>()
        listItem.filter { return@filter it is Comic }.forEach{ (listComic.add(it as Comic)) }

        for (character in listFavoriteCharacter) {
            listCharacter.filter { return@filter it.id == character.id}.also {
                if(it.isNotEmpty()) {
                    it[Constant.FIRST_POSITION].isFavorite = true
                }
            }
        }

        for (comic in listFavoriteComic) {
            listComic.filter { return@filter it.id == comic.id}.also {
                if(it.isNotEmpty()) {
                    it[Constant.FIRST_POSITION].isFavorite = true
                }
            }
        }

        this.listHomePageContent.addAll(listItem)
        notifyDataSetChanged()
    }

    fun updateDataListCharacterFavorite(listItem: MutableList<Character>) {
        this.listFavoriteCharacter.clear()
        this.listFavoriteCharacter.addAll(listItem)
    }

    fun updateDataListComicFavorite(listItem: MutableList<Comic>) {
        this.listFavoriteComic.clear()
        this.listFavoriteComic.addAll(listItem)
    }

    inner class CharacterViewHolder(val binding: CharacterItemLayoutBinding) :
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
                    if (character.isFavorite) {
                        buttonFavorite.setImageResource(R.drawable.ic_favorite)
                        clickFavoriteCharacterItemInterface?.onUnfavoriteItem(character)
                    } else {
                        buttonFavorite.setImageResource(R.drawable.ic_favorite_checked)
                        clickFavoriteCharacterItemInterface?.onFavoriteItem(character)
                    }

                    character.isFavorite = character.isFavorite.not()
                }

                characterItem.setOnClickListener {
                    clickItemCharacterInterface?.onClickItem(character)
                }
            }
        }
    }

    inner class CreatorViewHolder(val binding: CreatorItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(creator: Creator) {
            binding.apply {
                imageCreator.loadGlideImageFromUrl(
                    root.context,
                    creator.thumbnailLink,
                    R.drawable.image_creator_default
                )
                textCreatorName.text = creator.name

                creatorItem.setOnClickListener {
                    clickItemCreatorInterface?.onClickItem(creator)
                }
            }
        }
    }

    inner class ViewHolderTitle(val binding: ItemLayoutTitleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(title: String) {
            binding.textBigTitle.text = title
        }
    }

    enum class ViewType(val type: Int) {
        TITLE(TITLE_VIEW_TYPE),
        CHARACTER(CHARACTER_VIEW_TYPE),
        COMIC(COMIC_VIEW_TYPE),
        CREATOR(CREATOR_VIEW_TYPE)
    }
}
