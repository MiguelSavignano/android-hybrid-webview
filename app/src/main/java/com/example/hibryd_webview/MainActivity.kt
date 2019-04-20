package com.example.hibryd_webview

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import android.webkit.WebView
import android.webkit.WebViewClient
import android.webkit.JavascriptInterface
import android.widget.Toast
import kotlinx.android.synthetic.main.content_main.*

const val WEB_URL = "https://github.com/MiguelSavignano/hybrid-webview"


fun showToast (mContext: Context, message: String) {
  Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
}

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    setSupportActionBar(toolbar)

    val toggle = ActionBarDrawerToggle(
      this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
    )
    drawer_layout.addDrawerListener(toggle)
    toggle.syncState()

    nav_view.setNavigationItemSelectedListener(this)
    showToast(applicationContext, "Hello world")
    createWebview()
  }

  fun createWebview(){
    webview_content.settings.javaScriptEnabled = true
    webview_content.webViewClient = AppWebViewClient()
    webview_content.addJavascriptInterface(WebAppInterface(this), "Android")
    webview_content.loadUrl(WEB_URL)
  }

  override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
    super.onCreate(savedInstanceState, persistentState)
  }

  override fun onBackPressed() {
    if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
      drawer_layout.closeDrawer(GravityCompat.START)
    } else {
      super.onBackPressed()
    }
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    // Inflate the menu; this adds items to the action bar if it is present.
    menuInflater.inflate(R.menu.main, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    when (item.itemId) {
      R.id.action_settings -> return true
      else -> return super.onOptionsItemSelected(item)
    }
  }

  override fun onNavigationItemSelected(item: MenuItem): Boolean {
    // Handle navigation view item clicks here.
    when (item.itemId) {
      R.id.nav_camera -> {
        // Handle the camera action
      }
      R.id.nav_gallery -> {

      }
      R.id.nav_slideshow -> {

      }
      R.id.nav_manage -> {

      }
      R.id.nav_share -> {

      }
      R.id.nav_send -> {

      }
    }

    drawer_layout.closeDrawer(GravityCompat.START)
    return true
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
  fun showToast(message: String) {
    Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
  }
}