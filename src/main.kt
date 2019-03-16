package main

import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.random.Random
import kotlin.system.measureTimeMillis

data class Answer(var pt1: Point = Point(), var pt2: Point = Point(), var dist: Float = Float.MAX_VALUE)
data class Point(val id: Int = -1, val x: Float = 0f, val y: Float = 0f)

fun main(args: Array<String>){
    val pointList: List<Point> = genPoints(50000, 10000f)
    var answer = Answer()
    var elapsedTime: Long
    elapsedTime = measureTimeMillis { answer = bruteForceMethod(pointList) }
    println(answer)
    println("${elapsedTime}ms")
    elapsedTime = measureTimeMillis { answer = divideAndConquerMethod(pointList) }
    println(answer)
    println("${elapsedTime}ms")
}

fun divideAndConquerMethod(pointList: List<Point>): Answer{
    val pList: List<Point> = preprocess(pointList)
    return divideAndConquer(pList)
}

fun divideAndConquer(pointList: List<Point>): Answer{
    if (pointList.size < 4){
        // size lt 4 -> conquer
        return bruteForce(pointList)
    } else{
        // size ge 4 -> divide
        var answer: Answer
        var leftPtList: List<Point>
        var rightPtList: List<Point>
        var midPt: Point
        //  split list into half
        if (pointList.size % 2 == 0){
            // even sized list
            leftPtList = pointList.subList(0, pointList.size / 2)
            rightPtList = pointList.subList(pointList.size / 2,  pointList.size)
            midPt = midPoint(leftPtList.last(), rightPtList.first())
        } else{
            // odd sized list
            leftPtList = pointList.subList(0, pointList.size / 2 + 1)
            rightPtList = pointList.subList(pointList.size / 2 + 1,  pointList.size)
            midPt = leftPtList.last()
        }
        //  recursive divide and conquer
        answer = min(divideAndConquer(leftPtList), divideAndConquer(rightPtList))
        val stripLower: Float = midPt.x - answer.dist
        val stripUpper: Float = midPt.x + answer.dist
        val leftStripPtList = leftPtList.filter { it.x in stripLower..stripUpper }
        val rightStripPtList = rightPtList.filter { it.x in stripLower..stripUpper }
        //  min of left and right region and middle strip
        answer = min(bruteForce(leftStripPtList, rightStripPtList), answer)
        return answer
    }
}

fun bruteForceMethod(pointList: List<Point>): Answer{
    return bruteForce(pointList)
}

fun bruteForce(pointList1: List<Point>, pointList2: List<Point>): Answer{
    var answer = Answer()
    pointList1.forEach {
        pt1 -> pointList2.forEach {
            pt2 -> answer = min(distance(pt1, pt2), answer)
        }
    }
    return answer
}

fun bruteForce(pointList: List<Point>): Answer{
    var answer = Answer()
    for (i in 0..(pointList.lastIndex)){
        val currentPoint = pointList[i]
        val compareList: List<Point> = pointList.subList(i + 1, pointList.size)
        compareList.forEach {
            answer = min(distance(it, currentPoint), answer)
        }
    }
    return answer
}

fun preprocess(pointList: List<Point>): List<Point>{
    return pointList.sortedBy { it.x }
}

fun genPoints(count: Int, bound: Float): List<Point>{
    var pList = ArrayList<Point>()
    for (id in 0..(count-1)){
        pList.add(Point(id, Random.nextFloat() * bound * 2 - bound,
            Random.nextFloat() * bound * 2 - bound))
    }
    return pList
}

fun min(ans1: Answer, ans2: Answer): Answer{
    return when (ans1.dist < ans2.dist){
        true -> ans1
        else -> ans2
    }
}

fun distance(pt1: Point, pt2: Point): Answer{
    return Answer(pt1, pt2, sqrt((pt1.y - pt2.y).pow(2) + (pt1.x - pt2.x).pow(2)))
}

fun midPoint(pt1: Point, pt2: Point): Point{
    return Point(-1, (pt1.x + pt2.x) / 2, (pt1.y + pt2.y) / 2)
}