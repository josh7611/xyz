package com.xyz.app.data.ApplicationException

import com.xyz.app.R

open class ApplicationException(
    val reason: String,
    val defaultDisplayedStringResId: Int = 0
) : Exception(reason)

object SortBeforeFetchException : ApplicationException("Sort before fetch!!", R.string.main_error_sort_before_fetch)