package com.example.mapd726_group3_newsbox

import android.os.Bundle
import android.os.PersistableBundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity

class ArticleViewActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_article_view)

        val webView = findViewById<WebView>(R.id.web_view)
        val client = WebViewClient()
        webView.webViewClient= client

        val  url = intent.getStringExtra("url")
        url?.let { webView.loadUrl(it) }

    }
}