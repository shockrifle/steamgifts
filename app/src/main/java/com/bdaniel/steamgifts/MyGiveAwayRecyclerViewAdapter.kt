package com.bdaniel.steamgifts

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import com.bdaniel.steamgifts.databinding.ItemGiveawayBinding
import com.squareup.picasso.Picasso

class MyGiveAwayRecyclerViewAdapter(private val mValues: List<String>, private val mListener: OnFragmentInteractionListener?) : RecyclerView.Adapter<MyGiveAwayRecyclerViewAdapter.ViewHolder>() {

    override fun getItemCount() = mValues.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemGiveawayBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Picasso.with(holder.mBinding.root.context).load(mValues[position]).into(holder.mBinding.root as ImageView)
    }

    inner class ViewHolder(val mBinding: ItemGiveawayBinding) : RecyclerView.ViewHolder(mBinding.root)
}
