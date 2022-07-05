package com.mrt.android.teamcall.util

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerAdapter<T : RecyclerView.ViewHolder, D>(private var mDataSet: MutableList<D>) :
    RecyclerView.Adapter<T>() {

    init {
        this.setHasStableIds(true)
    }

    abstract fun createView(view: ViewGroup, viewType: Int): T

    abstract fun bindView(view: T, position: Int)


    override fun getItemId(position: Int): Long {
        return mDataSet[position].hashCode().toLong()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): T {
        return createView(parent, viewType)
    }

    override fun onBindViewHolder(holder: T, position: Int) {
        bindView(holder, position)
    }

    override fun getItemCount(): Int {
        return mDataSet.size
    }

    fun swapDataSet(newData: MutableList<D>) {
        mDataSet = newData
        notifyDataSetChanged()
    }

    fun getItem(position: Int): D {
        return mDataSet[position]
    }

    fun getPosition(item: D): Int {
        return mDataSet.indexOf(item)
    }

    fun removeItem(position: Int) {
        mDataSet.removeAt(position)
        notifyDataSetChanged()
    }

    fun replaceItem(item: D, position: Int) {
        mDataSet[position] = item
        notifyDataSetChanged()
    }

    fun addItem(item: D) {
        mDataSet.add(item)
        notifyDataSetChanged()
    }
}
