package com.intelbras.urna.model

import com.intelbras.urna.R

data class Vote(
    val number1: String,
    val number2: String,
    val name: String,
    var qtdVote: Int,
    val photo: Int,
)
data class Zone(val number: String)

val list = listOf(
    Vote("2", number2 = "0", "PAULO OLIVEIRA", 0, R.drawable.paulo),
    Vote("2", number2 = "1", "STEVE JOBS", 0, R.drawable.esteve1),
    Vote("2", number2 = "2", "MARK ZUCKERBERG", 0, R.drawable.mark),
    Vote("2", number2 = "3", "BILL GATE", 0, R.drawable.bill),
    Vote("2", number2 = "4", "STEVE WOZNIAK", 0, R.drawable.wos),
)

val listFail = mutableListOf(
    Vote("2", number2 = "0", "PAULO OLIVEIRA", 13, R.drawable.paulo),
    Vote("2", number2 = "1", "STEVE JOBS", 4, R.drawable.esteve1),
    Vote("2", number2 = "2", "MARK ZUCKERBERG", 3, R.drawable.mark),
    Vote("2", number2 = "3", "BILL GATE", 2, R.drawable.bill),
    Vote("2", number2 = "4", "STEVE WOZNIAK", 3, R.drawable.wos),
)