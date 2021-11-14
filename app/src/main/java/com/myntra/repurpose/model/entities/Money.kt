
package com.myntra.repurpose.model.entities

import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*

class Money @JvmOverloads internal constructor(
    var amount: BigDecimal,
    val currency: Currency,
    rounding: RoundingMode? = DEFAULT_ROUNDING
) {
    override fun toString(): String {
        return currency.symbol + " " + amount
    }

    fun toString(locale: Locale?): String {
        return currency.getSymbol(locale) + " " + amount
    }

    companion object {
        private val INR = Currency.getInstance(
            Locale(
                "en",
                "in"
            )
        )
        private val DEFAULT_ROUNDING = RoundingMode.HALF_EVEN
        @JvmStatic
        fun rupees(amount: BigDecimal): Money {
            return Money(amount, INR)
        }
    }

    init {
        amount = amount.setScale(
            currency.defaultFractionDigits,
            rounding
        )
    }
}