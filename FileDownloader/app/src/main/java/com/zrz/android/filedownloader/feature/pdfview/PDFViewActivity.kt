package com.zrz.android.filedownloader.feature.pdfview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zrz.android.filedownloader.BuildConfig.KEY_FILE_PATH
import com.zrz.android.filedownloader.R
import com.zrz.android.filedownloader.util.extention.asBundleKey
import com.zrz.android.filedownloader.util.extention.asIntentKey
import kotlinx.android.synthetic.main.activity_pdfview.*
import java.io.File

class PDFViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdfview)
        showPDF()
    }

    private fun showPDF() {
        intent = intent
        val bundle = intent.getBundleExtra(KEY_FILE_PATH.asIntentKey())
        if (bundle != null) {
            val filePath = bundle.getString(KEY_FILE_PATH.asBundleKey())!!
            val file = File(filePath)
            pdfView.fromFile(file).load()
        }
    }
}
