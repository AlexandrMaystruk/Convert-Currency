package com.gmail.maystruks08.currencyconverter.presentation.ui.utils

import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject

class DecimalInputProcessor @Inject constructor() {

    private var integerBuffer = StringBuilder()
    private var floatBuffer = StringBuilder()

    private val floatSize = 2
    private var hasFloatingPoint = false

    val currentValue: BigDecimal get() = getResult()

    fun nextValue(c: String): BigDecimal {
        if (c == KEY_POINT) hasFloatingPoint = true
        if (c == COMMAND_CLEAR) {
            integerBuffer.setLength(0)
            floatBuffer.setLength(0)
            hasFloatingPoint = false
        }
        if (checkIsValidSymbol(c)) {
            if (hasFloatingPoint) {
                if (!hasFloatLimit()) {
                    floatBuffer.append(c)
                }
            } else {
                integerBuffer.append(c)
            }
        }
        return getResult()
    }

    fun clearBuffer() {
        hasFloatingPoint = false
        integerBuffer.setLength(0)
        floatBuffer.setLength(0)
    }

    private fun getResult(): BigDecimal {
        val value =
            integerBuffer.toString() + if (floatBuffer.isNotEmpty()) ".${floatBuffer}" else ""
        return value.toDecimal()
    }

    private fun checkIsValidSymbol(c: String): Boolean {
        return when (c) {
            KEY_POINT, COMMAND_CLEAR, COMMAND_OK -> false
            else -> true
        }
    }

    private fun hasFloatLimit(): Boolean {
        return floatBuffer.length >= floatSize
    }

    private fun String.toDecimal(): BigDecimal {
        return try {
            this.toBigDecimal().setScale(2, RoundingMode.HALF_UP)
        } catch (e: NumberFormatException) {
            BigDecimal.ZERO
        }
    }

    companion object {
        const val KEY_0 = "0"
        const val KEY_DOUBLE_ZERO = "00"
        const val KEY_POINT = "."
        const val COMMAND_CLEAR = "<"
        const val COMMAND_OK = "OK"
    }

}