package model

import kotlin.math.pow
import kotlin.math.sqrt

data class Answer(var id1: Int = -1, var id2: Int = -1, var dist: Float = Float.MAX_VALUE)

data class Point(val id: Int, val x: Float, val y: Float)
