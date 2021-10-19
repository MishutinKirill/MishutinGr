package com.example.maklecoovkoordright.ui.main.graphics

class Converter(
    val xMin: Double,
    val xMax: Double,
    val yMin: Double,
    val yMax: Double,
    var width: Int,
    var height: Int
) {
    //конвертируем из декартовой системы  в андройд
    fun fromXToScreen(x: Double): Double {
        return width / (xMax - xMin) * (x - xMin)
    }

    fun fromYToScreen(y: Double): Double {
        return height / (yMax - yMin) * (yMax - y)
    }
}