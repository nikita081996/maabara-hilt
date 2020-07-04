package com.covidkanzidata.utility

import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object Validation {

    /**
     * format date
     */
    @JvmStatic
    fun getDate(date: String?): String {
        return try {
            if (date != null) {
                val formatter: DateFormat = SimpleDateFormat(DateConstants.dateFormat, Locale.US)
                val validityFormatter: DateFormat =
                    SimpleDateFormat(DateConstants.validityFormatter, Locale.US)

                val currDate = formatter.parse(date) as Date
                ("" + validityFormatter.format(currDate))
            } else {
                ""
            }

        } catch (pe: ParseException) {
            ""
        }

    }

    /**
     * calculate validity date when valid date not available
     */
    @JvmStatic
    fun getValidity(date: String?): String {
        return try {
            if (date != null && date != "") {
                val formatter: DateFormat = SimpleDateFormat(DateConstants.dateFormat, Locale.US)
                val cal: Calendar = Calendar.getInstance()
                val currDate = formatter.parse(date) as Date
                cal.time = currDate
                cal.add(Calendar.DATE, 14)
                ("Valid until " + cal.get(Calendar.DATE) + "-" + (cal.getDisplayName(
                    Calendar.MONTH,
                    Calendar.LONG,
                    Locale.getDefault()
                )) + "-" + cal.get(
                    Calendar.YEAR
                ))
            } else {
                ""
            }

        } catch (pe: ParseException) {
            ""
        }
    }

    /**
     * format date
     */
    @JvmStatic
    fun getValidity2(date: String?): String {
        return try {
            if (date != null) {
                val formatter: DateFormat = SimpleDateFormat(DateConstants.dateFormat, Locale.US)
                val validityFormatter: DateFormat =
                    SimpleDateFormat(DateConstants.validityFormatter, Locale.US)

                val currDate = formatter.parse(date) as Date
                ("Valid until " + validityFormatter.format(currDate))
            } else {
                ""
            }

        } catch (pe: ParseException) {
            ""
        }
    }
}