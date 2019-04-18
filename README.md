# Simple Hybrid WebView App

Convert website in android apk.

## Change web url

open file `app/src/main/java/com.example.webview/MainActivity.kt` and change the WEB_URL const

```
const val WEB_URL = "https://my-responsive-website"
```

## Create icon

Using Android studio
ImportFile > New > Image Asset
or copy the new icon in all mipmap folders

```
cp ic_launcher.png ./app/src/main/res/mipmap*
```

## Build app

Apk path

```
/app/build/outputs/apk/debug/app-debug.apk
```

### Override webview

In hybrid webview you nedd to simulate a native application you can override some content of the webpage injenting javascript.
onPageFinished run the script src/main/res/assets/initJavascript.js to override header, footer and change eventlisteners.

EJ:

```javascript
$("header") && $("header").remove();
$("footer") && $("footer").remove();
```

### Native events

Listener javascript events in native code.
The global Object "Android" help to comunicate webview with native code.

Example:

- Native

```java
class WebAppInterface(private val mContext: Context) {

  @JavascriptInterface
  fun showToast(toast: String) {
    Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show()
  }
}
```

- Webview

```javascript
Android.showToast("Hello world!!!");
```
