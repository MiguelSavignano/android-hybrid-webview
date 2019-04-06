package com.example.webview

import android.net.Uri
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient

const val WEB_URL = "https://google.com"

class MainActivity : Activity() {
  private var myWebView: WebView? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    this.setContentView(R.layout.activity_main)
    this.myWebView = this.findViewById(R.id.webView)
    myWebView!!.settings.javaScriptEnabled = true
    myWebView!!.webViewClient = MyWebViewClient()
    myWebView!!.loadUrl(WEB_URL)
  }

  override fun onBackPressed() {
    // Check if there's history
    if (this.myWebView!!.canGoBack())
      this.myWebView!!.goBack()
    else
      super.onBackPressed()
  }

  private inner class MyWebViewClient() : WebViewClient() {
    override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
      //remain in the web view when click links in the same host
      if (Uri.parse(url).host == Uri.parse(WEB_URL).host) {
        return false
      }
      val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
      startActivity(intent)
      return true
    }
  }
}
