package com.nguyennhatminh614.marvelapp.screen.search

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.chip.Chip
import com.nguyennhatminh614.marvelapp.databinding.ActivitySearchBinding
import com.nguyennhatminh614.marvelapp.util.extensions.processSearchResult

object SearchActivityUtil {

    fun hideKeyboard(activity: SearchActivity, binding: ActivitySearchBinding) {
        binding.apply {
            searchLayout.setOnClickListener {
                (activity.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)
                    ?.hideSoftInputFromWindow(
                        it.windowToken, 0)
            }
            textTempSearchResult.setOnClickListener {
                (activity.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)
                    ?.hideSoftInputFromWindow(
                        it.windowToken, 0)
            }
            recyclerViewSearchResult.setOnClickListener {
                (activity.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)
                    ?.hideSoftInputFromWindow(
                        it.windowToken, 0)
            }
            textSearchResultNotFound.setOnClickListener {
                (activity.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)
                    ?.hideSoftInputFromWindow(
                        it.windowToken, 0)
            }
        }
    }

    fun queryTextString(
        searchPresenter: SearchPresenter,
        binding: ActivitySearchBinding,
        listState: MutableList<Chip>,
        adapter: SearchAdapter,
        updateListData: () -> Unit,
    ) {
        binding.textSearchBox.doAfterTextChanged {
            if (!binding.recyclerViewSearchResult.isVisible) {
                binding.recyclerViewSearchResult.isVisible = true
                binding.textSearchResultNotFound.isVisible = false
            }

            adapter.updateDataItem(mutableListOf())
            updateListData()

            val stringRequest = binding.textSearchBox.text.toString().processSearchResult()

            var isAnyChipChoose = false

            for (state in listState) {
                if (state.isChecked) {
                    when (state) {
                        binding.chipComic -> searchPresenter.queryComicName(stringRequest)
                        binding.chipSeries -> searchPresenter.querySeriesName(stringRequest)
                        binding.chipEvents -> searchPresenter.queryEventName(stringRequest)
                        binding.chipCreator -> searchPresenter.queryCreatorName(
                            stringRequest)
                        binding.chipCharacter -> searchPresenter.queryCharacterName(
                            stringRequest)
                    }
                    isAnyChipChoose = true
                }
            }

            if (isAnyChipChoose.not()) {
                searchPresenter.queryEventName(stringRequest)
                searchPresenter.querySeriesName(stringRequest)
                searchPresenter.queryComicName(stringRequest)
                searchPresenter.queryCreatorName(stringRequest)
                searchPresenter.queryCharacterName(stringRequest)
            }
        }
    }
}

