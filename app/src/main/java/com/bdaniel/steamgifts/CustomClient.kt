package com.bdaniel.steamgifts

import android.graphics.Bitmap
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient

public class CustomClient : WebViewClient() {
    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        if("https://www.steamgifts.com/".equals(url)){
            Log.e("webview","login");
        }else {
            super.onPageStarted(view, url, favicon)
        }
    }
}