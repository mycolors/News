package com.fengniao.news.widget.konfetti

data class Vector(var x: Float = 0F, var y: Float = 0F) {
    fun add(v: Vector) {
        x += v.x
        y += v.y
    }


    fun mult(n: Float) {
        x *= n
        y *= n
    }

    fun div(n: Float) {
        x /= n
        y /= n
    }
}