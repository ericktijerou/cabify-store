/*
 * Copyright 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ericktijerou.cabifystore.core

import java.text.NumberFormat
import java.util.*

fun Int?.orZero() = this ?: 0
fun Double?.orZero() = this ?: 0.0

fun randomColorHex(): String {
    val obj = Random()
    val randNum: Int = obj.nextInt(0xffffff + 1)
    return String.format("%06x", randNum)
}

fun Double.toPriceFormatted(currencySymbol: String): String {
    val fmtLocale = Locale.getDefault(Locale.Category.FORMAT)
    val formatter: NumberFormat = NumberFormat.getInstance(fmtLocale)
    formatter.maximumFractionDigits = 2
    return "${formatter.format(this)}$currencySymbol"
}

fun Double.toPriceFull(currencySymbol: String): String {
    val fmtLocale = Locale.getDefault(Locale.Category.FORMAT)
    val formatter: NumberFormat = NumberFormat.getInstance(fmtLocale)
    formatter.minimumFractionDigits = 2
    return "$currencySymbol ${formatter.format(this)}"
}