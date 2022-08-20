package com.nguyennhatminh614.marvelapp.util.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VBinding : ViewBinding>(private val bindingLayoutInflater: (LayoutInflater) -> VBinding) :
    Fragment() {

    private var binding: VBinding? = null
    protected val viewBinding: VBinding
        get() = binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        restoreInstanceState(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = bindingLayoutInflater(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        saveInstanceState(outState)
    }

    abstract fun initData()

    abstract fun restoreInstanceState(savedInstanceState: Bundle?)

    abstract fun saveInstanceState(outState: Bundle)
}
