package com.zrz.android.filedownloader.feature.main

import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IntRange
import com.zrz.android.filedownloader.BuildConfig.NO_IMAGE
import com.zrz.android.filedownloader.R
import com.zrz.android.filedownloader.core.base.adapter.ResizeAdapter
import com.zrz.android.filedownloader.entity.PDFResource
import com.zrz.android.filedownloader.util.extention.inflateView
import com.zrz.android.filedownloader.util.extention.showImage
import kotlinx.android.synthetic.main.item_main_pdf.view.*

class MainAdapter(
    val dataSet: ArrayList<PDFResource> = ArrayList(),
    val progress: ArrayList<Int> = ArrayList(),
    val isItemsClickable: ArrayList<Boolean> = ArrayList(),
    displayMetrics: DisplayMetrics
) : ResizeAdapter<PDFResource, MainAdapter.MainViewHolder>(dataSet, progress, isItemsClickable, displayMetrics) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MainViewHolder(parent.inflateView(R.layout.item_main_pdf))

    override fun getItemWidthInPercents(): Int = ITEM_WIDTH_IN_PERCENTS

    override fun getItemHeightInPercents(): Int = ITEM_HEIGHT_IN_PERCENTS

    override fun getPosition(res: PDFResource) = dataSet.indexOfFirst { it.urlAddress == res.urlAddress }

    class MainViewHolder(view: View) : BaseViewHolder<PDFResource>(view) {

        override fun onBind(resource: PDFResource, progressStatus: Int) {
            if (resource.imageName == NO_IMAGE) {
                itemView.ivFirstPage.showImage(R.mipmap.error)
            } else {
                itemView.ivFirstPage.showImage("${resource.imageDirPath}${resource.imageName}", R.mipmap.done)
            }
            showProgress(progressStatus)
        }

        private fun showProgress(@IntRange(from = 0, to = 100) percents: Int) {
            if (percents in 1..99) {
                itemView.tvShowProgress.visibility = View.VISIBLE
                val percentsText = "$percents%"
                itemView.tvShowProgress.text = percentsText
                itemView.pbShowProgress.visibility = View.VISIBLE
                itemView.pbShowProgress.progress = percents
            } else {
                itemView.tvShowProgress.visibility = View.GONE
                itemView.pbShowProgress.visibility = View.GONE
            }
        }
    }

    companion object {
        private const val ITEM_WIDTH_IN_PERCENTS = 80
        private const val ITEM_HEIGHT_IN_PERCENTS = 62
    }
}