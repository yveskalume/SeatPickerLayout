package com.yveskalume.customlayoutcasestudy

data class Seat(val id: Int, val number: Int, val isAvailable: Boolean)

fun getDataList(): List<Seat> {
    return listOf(
        Seat(id = 1, number = 3, isAvailable = true),
        Seat(id = 2, number = 8, isAvailable = true),
        Seat(id = 3, number = 5, isAvailable = true),
        Seat(id = 4, number = 7, isAvailable = true),
        Seat(id = 5, number = 19, isAvailable = true),
    )
}