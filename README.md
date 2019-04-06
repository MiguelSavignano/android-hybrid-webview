# Simple WebView App

Convert website in android apk.


## Change web url

open file ``app/src/main/java/com.example.webview/MainActivity.kt`` and change the WEB_URL const

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
- 

## Build app

Apk path
```
/app/build/outputs/apk/debug/app-debug.apk
```
