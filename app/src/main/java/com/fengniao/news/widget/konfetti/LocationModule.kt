package com.fengniao.news.widget.konfetti

import java.util.*


class LocationModule(private val random: Random) {

    private var minX: Float = 0f
    private var maxX: Float? = null

    private var minY: Float = 0f
    private var maxY: Float? = null

    val x: Float; get() {
            return if (maxX == null) {
                minX
            } else {
                random.nextFloat().times(maxX!!.minus(minX)) + minX
            }
        }

    val y: Float;get() {
            return if (maxY == null) {
                return minY
            } else {
                random.nextFloat().times(maxY!!.minus(minY)) + minY
            }
        }


    fun betweemX(minX: Float, maxX: Float?) {
        this.minX = minX
        this.maxX = maxX
    }

    fun betweemY(minY: Float, maxY: Float?) {
        this.minY = minY
        this.maxY = maxY
    }

    fun setX(x: Float) {
        this.minX = x
    }

    fun setY(y: Float) {
        this.minY = y
    }


}