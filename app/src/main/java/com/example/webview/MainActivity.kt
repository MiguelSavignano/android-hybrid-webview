package com.example.webview

import android.net.Uri
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.webkit.HttpAuthHandler
import android.webkit.JavascriptInterface
import android.widget.Toast
import java.io.File
import java.io.BufferedReader
import java.io.InputStreamReader


const val WEB_URL = "https://github.com/MiguelSavignano/hybrid-webview"

class MainActivity : Activity() {
  private var webView: WebView? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    this.setContentView(R.layout.activity_main)
    webView = this.findViewById(R.id.webView)
    webView!!.settings.javaScriptEnabled = true
    webView!!.webViewClient = AppWebViewClient()
    webView!!.addJavascriptInterface(WebAppInterface(this), "Android")
    webView!!.loadUrl(WEB_URL)
  }

  override fun onBackPressed() {
    // device back button go back in the web view
    if (webView!!.canGoBack())
      webView!!.goBack()
    else
      super.onBackPressed()
  }

  private inner class AppWebViewClient : WebViewClient() {
    override fun onPageFinished(web: WebView, url: String) {
      val script = readAssetFileText("initJavascript.js")
      web.evaluateJavascript(script, {value -> })
    }

    override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
      // remain in the web view when click links in the same host
      if (Uri.parse(url).host == Uri.parse(WEB_URL).host) {
        return false
      }
      val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
      startActivity(intent)
      return true
    }

    fun readAssetFileText(fileName: String): String {
      return assets.open(fileName).bufferedReader().use { it.readText() }
    }

    // basic auth
    // override fun onReceivedHttpAuthRequest(view: WebView, handler: HttpAuthHandler, host: String, realm: String) {
    //   handler.proceed("user", "password")
    // }
  }
}

class WebAppInterface(private val mContext: Context) {

  @JavascriptInterface
  fun showToast(toast: String) {
    Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show()
  }
}
