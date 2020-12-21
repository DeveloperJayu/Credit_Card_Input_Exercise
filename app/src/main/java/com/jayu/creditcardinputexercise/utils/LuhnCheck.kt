package com.jayu.creditcardinputexercise.utils

class LuhnCheck {
    fun isValid(number: String): Boolean {
        val (digits, others) = number
                .filterNot(Char::isWhitespace)
                .partition(Char::isDigit)

        if (digits.length <= 1 || others.isNotEmpty()) {
            return false
        }

        val checksum = digits
                .map { n ->
                    n.toInt() - '0'.toInt()
                }
                .reversed()
                .mapIndexed { index, value ->
                    if (index % 2 == 1 && value < 9) value * 2 % 9 else value
                }
                .sum()

        return checksum % 10 == 0
    }
}