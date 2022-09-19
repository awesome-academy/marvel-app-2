package com.nguyennhatminh614.marvelapp.screen.webscreen

import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.view.isVisible
import com.nguyennhatminh614.marvelapp.databinding.FragmentWebLayoutBinding
import com.nguyennhatminh614.marvelapp.util.base.BaseFragment
import com.nguyennhatminh614.marvelapp.util.constant.Constant

class WebScreenFragment :
    BaseFragment<FragmentWebLayoutBinding>(FragmentWebLayoutBinding::inflate) {

    private var urlString: String = ""

    override fun initData() {
        viewBinding.webView.apply {
            settings.javaScriptEnabled = true
            settings.loadWithOverviewMode = true
            settings.useWideViewPort = true
            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?,
                ): Boolean {
                    return super.shouldOverrideUrlLoading(view, request)
                }
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    activity?.runOnUiThread {
                        viewBinding.progressBarLoading.isVisible = false
                    }
                }
            }
            loadUrl(urlString)
        }
    }

    override fun initialize() {
        urlString = arguments?.getString(Constant.DETAIL_ITEM).toString()
    }

    override fun callData() {
        // Not support
    }

    override fun initEvent() {
        // Not support
    }
}
