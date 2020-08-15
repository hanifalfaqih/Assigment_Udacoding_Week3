package com.allana.assigmentudacodingweek3

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.appbar.CollapsingToolbarLayout
import kotlinx.android.synthetic.main.activity_detail_news.*


class DetailNewsActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_SOURCE_NAME = "extra_source_name"
        const val EXTRA_AUTHOR = "extra_author"
        const val EXTRA_TITLE = "extra_title"
        const val EXTRA_DESCRIPTION = "extra_description"
        const val EXTRA_URL_TO_IMAGE = "extra_url_to_image"
        const val EXTRA_PUBLISHED_AT = "exra_published_at"
        const val EXTRA_URL = "extra_url"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_news)

        val collapsingToolbar: CollapsingToolbarLayout = toolbar_layout
        val toolbar = toolbar

        val tvNewsSource: TextView? = null
        val tvNewsAuthor: TextView? = tv_news_author
        val tvNewsTitle: TextView? = tv_news_title
        val tvNewsDescription: TextView? = tv_news_description
        val imgNews: ImageView = img_news

        val sourceName = intent.getStringExtra(EXTRA_SOURCE_NAME)
        val title = intent.getStringExtra(EXTRA_TITLE)
        val author = intent.getStringExtra(EXTRA_AUTHOR)
        val description = intent.getStringExtra(EXTRA_DESCRIPTION)
        val img = intent.getStringExtra(EXTRA_URL_TO_IMAGE)
        val url = intent.getStringExtra(EXTRA_URL)


        tvNewsSource?.text = sourceName
        tvNewsAuthor?.text = author
        tvNewsTitle?.text = title
        tvNewsDescription?.text = description
        Glide.with(this)
            .load(img)
            .into(imgNews)

        collapsingToolbar.title = sourceName
        setSupportActionBar(toolbar)

        if (url != null) {
            initWebView(url)
        }

    }

    private fun initWebView(url: String) {
        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient()
        webView.webChromeClient = WebChromeClient()
        webView.loadUrl(url)

        webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                progressBar.visibility = View.VISIBLE
                super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                progressBar.visibility = View.GONE
                super.onPageFinished(view, url)
            }
        }
    }
}

