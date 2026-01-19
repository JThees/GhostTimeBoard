// SPDX-License-Identifier: GPL-3.0-only
package helium314.keyboard.latin.utils

import android.content.Context
import helium314.keyboard.latin.settings.Defaults
import helium314.keyboard.latin.settings.Settings
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.TimeZone

fun getTimestamp(context: Context): String = getTimestampFormatter(context).format(Calendar.getInstance().time)

fun getTimestampFormatter(context: Context): SimpleDateFormat {
    val format = context.getSharedPreferences("heliboard_preferences", 0).getString(Settings.PREF_TIMESTAMP_FORMAT, Defaults.PREF_TIMESTAMP_FORMAT)
    return runCatching<SimpleDateFormat> { SimpleDateFormat(format, Settings.getValues().mLocale) }.getOrNull()
        ?: SimpleDateFormat(Defaults.PREF_TIMESTAMP_FORMAT, Settings.getValues().mLocale)
}

fun checkTimestampFormat(format: String) = runCatching { SimpleDateFormat(format, Settings.getValues().mLocale) }.isSuccess

fun getEnhancedTimestamp(context: Context): String {
    val now = Date()
    val utcFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    utcFormat.timeZone = TimeZone.getTimeZone("UTC")
    val utcTimestamp = utcFormat.format(now)
    
    val localFormat = SimpleDateFormat("h:mm a z")
    localFormat.timeZone = TimeZone.getDefault()
    val localTimestamp = localFormat.format(now)
    
    return "[$utcTimestamp] ($localTimestamp)"
}
