package com.zrz.android.filedownloader.core.base.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<M, VH : BaseAdapter.BaseViewHolder<M>>(
    private val dataSet: ArrayList<M>,
    private val progress: ArrayList<Int>,
    private val isItemsClickable: ArrayList<Boolean> = ArrayList(),
    var itemClickAction: ((Int, View) -> Unit)? = null
) : RecyclerView.Adapter<VH>() {

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.itemView.isClickable = isItemsClickable[position]
        holder.onBind(dataSet[position], progress[position])
        holder.itemView.setOnClickListener { view ->
            if (isInPositionsRange(holder.adapterPosition)) {
                if (isItemsClickable[position]) {
                    onClickAction(position)
                    itemClickAction?.invoke(position, view)
                }
            }
        }
    }

    override fun getItemCount() = dataSet.size

    fun updateItem(res: M) {
        val position = getPosition(res)
        if (isInPositionsRange(position)) {
            dataSet[position] = res
            isItemsClickable[position] = true
            notifyItemChanged(position)
        }
    }

    fun updateProgress(res: M, progress: Int) {
        val position = getPosition(res)
        if (isInPositionsRange(position)) {
            this.progress[position] = progress
            notifyItemChanged(position)
        }
    }

    private fun onClickAction(position: Int) {
        isItemsClickable[position] = false
    }

    private fun isInPositionsRange(position: Int) =
        position != RecyclerView.NO_POSITION && position < itemCount

    abstract fun getPosition(res: M): Int

    abstract class BaseViewHolder<M>(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun onBind(resource: M, progressStatus: Int)
    }
}