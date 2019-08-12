package com.zrz.android.filedownloader.core.base.adapter

import android.util.DisplayMetrics
import androidx.annotation.IntRange

abstract class ResizeAdapter<M, VH : BaseAdapter.BaseViewHolder<M>>(
    dataSet: ArrayList<M>,
    progress: ArrayList<Int>,
    isItemsClickable: ArrayList<Boolean>,
    private val displayMetrics: DisplayMetrics
) : BaseAdapter<M, VH>(dataSet, progress, isItemsClickable) {

    @IntRange(from = 1, to = 100)
    abstract fun getItemHeightInPercents(): Int

    @IntRange(from = 1, to = 100)
    abstract fun getItemWidthInPercents(): Int

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.itemView.layoutParams.height = setItemHeight()
        holder.itemView.layoutParams.width = setItemWidth()
        super.onBindViewHolder(holder, position)
    }

    private fun setItemHeight(): Int =
        displayMetrics.heightPixels / 100 * getItemHeightInPercents()

    private fun setItemWidth(): Int =
        displayMetrics.widthPixels / 100 * getItemWidthInPercents()
}