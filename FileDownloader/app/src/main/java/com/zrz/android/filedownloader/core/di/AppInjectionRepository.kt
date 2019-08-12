package com.zrz.android.filedownloader.core.di

import android.content.Context
import com.zrz.android.filedownloader.core.base.repository.FDRepository
import com.zrz.android.filedownloader.entity.exception.RepositoryTypeNotFoundException
import com.zrz.android.filedownloader.repository.pdf.PDFRepository
import com.zrz.android.filedownloader.repository.pdf.PDFRepositoryImpl

object AppInjectionRepository {

    inline fun <reified RP : FDRepository> getRequiredRepository(appContext: Context): RP =
        when (RP::class.java) {
            PDFRepository::class.java -> PDFRepositoryImpl(
                appContext,
                AppInjectionManager.getRequiredManager(),
                AppInjectionManager.getRequiredManager(),
                AppInjectionManager.getRequiredManager()
            ) as RP
            else -> throw RepositoryTypeNotFoundException(context = appContext, classType = (RP::class.java).toString())
        }
}