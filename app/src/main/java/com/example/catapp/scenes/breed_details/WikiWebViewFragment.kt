package com.example.catapp.scenes.breed_details

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.example.catapp.R
import com.example.catapp.bases.BaseFragment
import com.example.catapp.databinding.FragmentWikiBinding
import com.example.catapp.utils.ToolbarFragment

class WikiWebViewFragment : BaseFragment<FragmentWikiBinding>() {
    override fun builder(): ToolbarFragment {
        return ToolbarFragment.Builder()
            .with(binding.toolbar)
            .shouldDisplayBack(requireActivity())
            .build()
    }

    override val layoutRes: Int
        get() = R.layout.fragment_wiki

    private val args by navArgs<WikiWebViewFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                binding.webPageLoadingIndicator.visibility = View.VISIBLE
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                binding.webPageLoadingIndicator.visibility = View.GONE
            }
        }

        binding.webView.loadUrl(args.url)
    }
}