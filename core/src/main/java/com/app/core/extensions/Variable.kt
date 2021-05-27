package com.app.core.extensions

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun Float.calculateWithMaxRating(maxRating: Int): Float {
    require(maxRating > 5) { "Max rating should be higher than 5" }

    val defaultMaxRating = 5F
    val divisionResult = maxRating.toFloat() / defaultMaxRating
    return this / divisionResult
}

fun String.formatDatePattern(
    previousPattern: String = "yyyy-MM-dd",
    newPattern: String = "d MMM yyyy"
): String? {
    try {
        val parser = SimpleDateFormat(previousPattern, Locale.getDefault())
        val formatter = SimpleDateFormat(newPattern, Locale.getDefault())
        val parserResult = parser.parse(this)
        parserResult?.let { result ->
            return formatter.format(result)
        } ?: return "-"
    } catch (e: ParseException) {
        e.printStackTrace()
        return "-"
    }
}