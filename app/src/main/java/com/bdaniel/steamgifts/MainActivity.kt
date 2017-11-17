package com.bdaniel.steamgifts

import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.webkit.CookieManager

class MainActivity : AppCompatActivity(), OnFragmentInteractionListener {

    override fun onFragmentInteraction(uri: Uri) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val cookieManager = CookieManager.getInstance()
        val hasLogin: Boolean;
        val cookies = cookieManager.getCookie("steamcommunity.com")?.split("; ")?.associate { it.split('=')[0] to it.split('=')[1] }
        hasLogin = cookies != null && cookies.containsKey("steamLogin")

        val newInstance: Fragment
        newInstance = if (hasLogin) {
            GiveAwayListFragment.newInstance(1)
        } else {
            WebFragment.newInstance("https://www.steamgifts.com/")
        }
        supportFragmentManager.beginTransaction().add(R.id.container,
                newInstance).commit()
    }
}
