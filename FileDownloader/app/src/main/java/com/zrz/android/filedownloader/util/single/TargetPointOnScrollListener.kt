package com.zrz.android.filedownloader.util.single

import androidx.recyclerview.widget.RecyclerView

object TargetPointOnScrollListener : RecyclerView.OnScrollListener() {

    private var positionX = 0
    private var positionY = 0

    var reachTargetPointAction: ((Int) -> Unit)? = null

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val currentView = recyclerView.findChildViewUnder(positionX.toFloat(), positionY.toFloat())
        val position = currentView?.let { recyclerView.getChildAdapterPosition(it) }
        if (position != null) reachTargetPointAction?.invoke(position)
    }

    fun setTargetPoint(coordinates: IntArray) {
        positionX = coordinates[0]
        positionY = coordinates[1]
    }


}