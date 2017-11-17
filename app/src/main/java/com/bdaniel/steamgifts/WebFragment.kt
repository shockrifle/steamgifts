package com.bdaniel.steamgifts

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import com.bdaniel.steamgifts.databinding.FragmentWebBinding

class WebFragment : Fragment() {

    private var mUrl: String? = null

    private var mListener: OnFragmentInteractionListener? = null

    private var mBinding: FragmentWebBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mUrl = arguments!!.getString(ARG_URL)
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: WebView? = FragmentWebBinding.inflate(layoutInflater, container, false).root as WebView?
        view?.webViewClient = CustomClient()
        view?.settings?.javaScriptEnabled = true
        view?.loadUrl(mUrl)
        return view
    }

    fun onLoggedIn(uri: Uri) {
        if (mListener != null) {
            mListener!!.onFragmentInteraction(uri)
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    companion object {
        private val ARG_URL = "url"

        fun newInstance(url: String): WebFragment {
            val fragment = WebFragment()
            val args = Bundle()
            args.putString(ARG_URL, url)
            fragment.arguments = args
            return fragment
        }
    }
}
