package me.bemind.glitch

import android.graphics.Point

/**
 * Created by angelomoroni on 21/07/17.
 */


class GRect{
    var w :Int = 0
    var h :Int = 0
    val center :Point = Point(0,0)
    var angle : Int = 0
    var vertices : List<Point> = arrayListOf()

    init {
        vertices = generateVertices()
    }

    constructor(w: Int,h: Int, center :Point = Point(0,0)){
        this.w = w
        this.h = h
        this.center.copy(center)

        vertices = generateVertices()
    }

    constructor(w: Int,h: Int,imageW:Float,imageH:Float):this(w,h, Point((imageW/2).toInt(),(imageH/2).toInt()))

    fun move(deltaX:Int,deltaY:Int) : List<Point>{
        center.x += deltaX
        center.y += deltaY

        vertices = moveVertices(vertices,deltaX,deltaY)

        return vertices
    }

    fun rotate(angle:Int) : List<Point>{
        this.angle = angle
        vertices = rotateVertices(vertices)
        return vertices
    }



    private fun rotateVertices(vl :List<Point>) : List<Point> {
        val vertices = vl.copy()
        for(p in vertices){
            val rotatedP = rotate(p)
            p.copy(rotatedP)
        }

        return vertices
    }

    private fun moveVertices(vl :List<Point>,deltaX:Int,deltaY:Int) : List<Point>{
        val vertices = vl.copy()
        for(p in vertices){
            p.x += deltaX
            p.y += deltaY
        }

        return vertices
    }

    private fun rotate(p: Point) : Point{
        val x:Int = p.x
        val y:Int = p.y
        val radians = (Math.PI/180) * angle
        val cos = Math.cos(radians)
        val sin = Math.sin(radians)
        val nx = (cos * (x - center.x)) + (sin * (y - center.y)) + center.x
        val ny = (cos * (y - center.y)) - (sin * (x - center.x)) + center.y

        return Point(nx.toInt(),ny.toInt())
    }

    private fun generateVertices() : List<Point>{
        val vv : MutableList<Point> = arrayListOf()
        val topLeft = Point(center.x - w/2,center.y - h/2)
        vv.add(topLeft)
        vv.add(Point(topLeft.x + w,topLeft.y))
        vv.add(Point(topLeft.x,topLeft.y+h))
        vv.add(Point(topLeft.x + w,topLeft.y+h))
        return rotateVertices(vv)
    }
}

private fun <E> List<E>.copy(): List<E> {
    val copy = this.toList()
    return copy
}


