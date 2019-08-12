package com.zrz.android.filedownloader.core.base.activity

import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.zrz.android.filedownloader.BuildConfig.ACTIVITY_UUID
import com.zrz.android.filedownloader.core.di.AppInjectionManager
import com.zrz.android.filedownloader.core.mvp.MVPPresenter
import com.zrz.android.filedownloader.core.mvp.MVPView
import com.zrz.android.filedownloader.model.presenter.PresenterFactory
import com.zrz.android.filedownloader.model.presenter.PresenterManager
import java.util.*

abstract class BaseActivity<V : MVPView, P : MVPPresenter<V>> : AppCompatActivity(), MVPView, PresenterFactory<V, P> {

    private lateinit var presenterManager: PresenterManager
    private lateinit var activityUUID: String
    private lateinit var view: V

    lateinit var presenter: P

    @LayoutRes
    abstract fun obtainLayoutResID(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(obtainLayoutResID())
        activityUUID = getActualActivityUUID(savedInstanceState)
        presenterManager = AppInjectionManager.getRequiredManager()
        presenter = presenterManager.addPresenter(activityUUID, this)
        @Suppress("UNCHECKED_CAST")
        view = this as V
        presenter.attach(view)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(ACTIVITY_UUID, activityUUID)
    }

    override fun handleError(e: Throwable) {
        showToast(e.toString())
        if (e.message != null) {
            showToast(e.message!!)
        }
    }

    override fun showToast(text: String, gravity: Int) {
        val toast = Toast.makeText(this, text, Toast.LENGTH_SHORT)
        toast.setGravity(gravity, 0, 0)
        toast.show()
    }

    fun getDisplayMetrics(): DisplayMetrics {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isFinishing) presenterManager.removePresenter(activityUUID)
    }

    private fun getActualActivityUUID(savedInstanceState: Bundle?) =
        if (savedInstanceState != null && savedInstanceState.containsKey(ACTIVITY_UUID)) {
            savedInstanceState.getString(ACTIVITY_UUID)!!
        } else {
            UUID.randomUUID().toString()
        }
}
