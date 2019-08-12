package com.zrz.android.filedownloader.feature.main

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.zrz.android.filedownloader.BuildConfig.KEY_FILE_PATH
import com.zrz.android.filedownloader.BuildConfig.PROGRESS_START_INDEX_VALUE
import com.zrz.android.filedownloader.R
import com.zrz.android.filedownloader.core.base.activity.BaseActivity
import com.zrz.android.filedownloader.entity.PDFResource
import com.zrz.android.filedownloader.feature.pdfview.PDFViewActivity
import com.zrz.android.filedownloader.util.single.TargetPointOnScrollListener
import com.zrz.android.filedownloader.util.extention.startNewActivity
import com.zrz.android.filedownloader.util.helper.StartSnapHelper
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<View, Presenter>(), View {

    private lateinit var mainAdapter: MainAdapter
    private lateinit var targetPointScrollListener: TargetPointOnScrollListener

    override fun createPresenter() = MainPresenter(applicationContext)

    override fun obtainLayoutResID(): Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        btnMainShowPDF.setOnClickListener {
            requestShowPDF()
        }
        targetPointScrollListener = TargetPointOnScrollListener
        recyclerViewInitializing()
        requestShowList()
    }

    override fun onStart() {
        targetPointScrollListener.setTargetPoint(getPointCoordinatesForScrollListening())
        targetPointScrollListener.reachTargetPointAction = { position ->
            showTitle(mainAdapter.dataSet[position].fileName)
        }
        rvMain.addOnScrollListener(targetPointScrollListener)
        super.onStart()
    }

    override fun onStop() {
        rvMain.removeOnScrollListener(targetPointScrollListener)
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detach()
    }

    override fun showList(resList: ArrayList<PDFResource>) {
        mainAdapter.dataSet.addAll(resList)
        resList.forEach { _ ->
            mainAdapter.progress.add(PROGRESS_START_INDEX_VALUE)
            mainAdapter.isItemsClickable.add(true)
        }
        mainAdapter.notifyDataSetChanged()
    }

    override fun updateItem(res: PDFResource) {
        mainAdapter.updateItem(res)
    }

    override fun updateItemProgress(resource: PDFResource, progress: Int) {
        mainAdapter.updateProgress(resource, progress)
    }

    override fun showPDF(filePath: String) {
        startNewActivity<PDFViewActivity>(KEY_FILE_PATH to filePath)
    }

    private fun requestShowList() {
        presenter.requestPDFListData()
    }

    private fun showTitle(title: String) {
        tvMainTitle.text = title
    }

    private fun onItemClick(res: PDFResource) {
        presenter.requestUpdateOneItem(res)
    }

    private fun requestShowPDF() {
        presenter.startShowPDF(tvMainTitle.text.toString())
    }

    private fun recyclerViewInitializing() {
        val rvLayoutManager = LinearLayoutManager(
            applicationContext,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        mainAdapter = MainAdapter(displayMetrics = getDisplayMetrics())
        mainAdapter.itemClickAction = { position, _ ->
            onItemClick(mainAdapter.dataSet[position])
        }
        rvMain.apply {
            layoutManager = rvLayoutManager
            adapter = mainAdapter
        }
        val snapHelper: LinearSnapHelper = StartSnapHelper()
        snapHelper.attachToRecyclerView(rvMain)
    }

    private fun getPointCoordinatesForScrollListening(): IntArray {
        val coordinateX = getDisplayMetrics().widthPixels / 2
        val coordinateY = getDisplayMetrics().heightPixels / 2
        return intArrayOf(coordinateX, coordinateY)
    }
}
