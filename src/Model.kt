package model

data class Answer(var pt1: Point = Point(), var pt2: Point = Point(), var dist: Float = Float.MAX_VALUE)

data class Point(val id: Int = -1, val x: Float = 0f, val y: Float = 0f)
