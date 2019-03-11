package main

import model.Answer
import model.Point
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.random.Random
import kotlin.system.measureTimeMillis

fun main(args: Array<String>){
    val pointList: List<Point> = genPoints(500, 1000f)
    var answer = Answer()
    answer = bruteForceMethod(pointList)
    println(answer)
//    divideAndConquerMethod(pointList)
//    println(answer)
//    val elapsedTime: Long = measureTimeMillis { answer = bruteForceMethod(pointList) }
//    println(elapsedTime)
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
        var answer = Answer()
        var leftPointList: List<Point>
        var rightPointList: List<Point>
        var midPt: Point

        if (pointList.size % 2 == 0){
            // even sized list
            leftPointList = pointList.subList(0, pointList.size / 2 - 1)
            rightPointList = pointList.subList(pointList.size / 2,  pointList.lastIndex)
            midPt = midPoint(leftPointList.last(), rightPointList.first())
        } else{
            // odd sized list
            leftPointList = pointList.subList(0, pointList.size / 2)
            rightPointList = pointList.subList(pointList.size / 2 + 1,  pointList.lastIndex)
            midPt = leftPointList.last()
        }
        answer = min(bruteForce(leftPointList), bruteForce(rightPointList))
    }
    return bruteForce(pointList)
}

fun bruteForceMethod(pointList: List<Point>): Answer{
    return bruteForce(pointList)
}

fun bruteForce(pointList: List<Point>): Answer{
    var answer = Answer()
    var loopCount = 0

    for (i in 0..(pointList.lastIndex - 1)){
        val currentPoint = pointList[i]
        val compareList: List<Point> = pointList.subList(i + 1, pointList.lastIndex)
        compareList.forEach {
            loopCount++
            if (distance(currentPoint, it) < answer.dist){
                answer.id1 = currentPoint.id
                answer.id2 = it.id
                answer.dist = distance(currentPoint, it)
            }
        }
    }
//    println("Looped $loopCount times")
    return answer
}

fun preprocess(pointList: List<Point>): List<Point>{
    return pointList.sortedBy { it.x }
}

fun genPoints(count: Int, bound: Float): List<Point>{
    var pList = ArrayList<Point>()
    for (id in 0..count){
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

fun distance(pt1: Point, pt2: Point): Float{
    return sqrt((pt1.y - pt2.y).pow(2) + (pt1.x - pt2.x).pow(2))
}

fun midPoint(pt1: Point, pt2: Point): Point{
    return Point(-1, (pt1.x + pt2.x) / 2, (pt1.y + pt2.y) / 2)
}