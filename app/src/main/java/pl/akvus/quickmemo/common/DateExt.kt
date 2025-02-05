package pl.akvus.quickmemo.common

import java.util.Date

fun Long.toDate(): Date = Date(this)
fun Date.toLong(): Long = this.time
