package com.zrz.android.filedownloader.entity.exception

import android.content.Context
import com.zrz.android.filedownloader.R

class RepositoryTypeNotFoundException(
    context: Context,
    classType: String
) : Exception("${context.getString(R.string.the_type)} $classType ${context.getString(R.string.was_not_found)}")
