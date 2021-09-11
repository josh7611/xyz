package com.xyz.app.ui.common

fun String.firstLetterAsUpperCase(): String {
    require(isNotEmpty()) { "Cannot get upper case from empty string" }

    return substring(0, 1).uppercase()
}