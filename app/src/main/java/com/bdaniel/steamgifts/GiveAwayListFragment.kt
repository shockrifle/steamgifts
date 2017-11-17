package com.bdaniel.steamgifts

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import com.bdaniel.steamgifts.databinding.FragmentGiveawayListBinding
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jsoup.Connection
import org.jsoup.Jsoup
import org.jsoup.select.Elements

class GiveAwayListFragment : Fragment() {

    private var mColumnCount = 1
    private var mListener: OnFragmentInteractionListener? = null
    private var mBinding: FragmentGiveawayListBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null) {
            mColumnCount = arguments.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = FragmentGiveawayListBinding.inflate(inflater, container, false)
        val view = mBinding?.root

        if (view is RecyclerView) {
            val context = view.getContext()
            if (mColumnCount <= 1) {
                view.layoutManager = LinearLayoutManager(context)
            } else {
                view.layoutManager = GridLayoutManager(context, mColumnCount)
            }
            val instance = CookieManager.getInstance()
            val cookies: MutableMap<String, String> = mutableMapOf()
            instance.getCookie("steamcommunity.com")?.split("; ")?.associate { it.split('=')[0] to it.split('=')[1] }?.let { cookies.putAll(it) }
            instance.getCookie("steamgifts.com")?.split("; ")?.associate { it.split('=')[0] to it.split('=')[1] }?.let { cookies.putAll(it) }


            Observable.create(ObservableOnSubscribe<Connection.Response> { e ->
                e.onNext(Jsoup.connect("https://steamgifts.com")
                        .cookies(cookies)
                        .timeout(2000)
                        .execute())
            }).subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread()).subscribe {

                val imageUrls: MutableList<String> = mutableListOf()
                it.parse().select("a.giveaway_image_thumbnail").forEach {
                    val style = it.attr("style")
                    imageUrls.add(style.substring(style.indexOf('(') + 1, style.lastIndexOf(')')))
                }

                view.adapter = MyGiveAwayRecyclerViewAdapter(imageUrls, mListener)
            }
        }
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    companion object {
        private val ARG_COLUMN_COUNT = "column-count"

        fun newInstance(columnCount: Int): GiveAwayListFragment {
            val fragment = GiveAwayListFragment()
            val args = Bundle()
            args.putInt(ARG_COLUMN_COUNT, columnCount)
            fragment.arguments = args
            return fragment
        }
    }
}
