package com.fengniao.news.widget.konfetti

import java.util.*


class VelocityModule(val random: Random) {

    var minAngle: Double = 0.0

    var maxAngle: Double? = null

    var minSpeed: Float = 0f
        set(value) {
            if (value < 0) field = 0f else field = value
        }


    var maxSpeed: Float? = null
        set(value) {
            if (value!! < 0) field = 0f else field = value
        }


    fun getSpeed(): Float {
        if (maxSpeed == null) {
            return minSpeed
        } else {
            //获取随机数
            return ((maxSpeed!! - minSpeed) * random.nextFloat()) + minSpeed
        }
    }


    fun getRadian(): Double{
        if (maxAngle == null){
            return  minAngle
        } else{
            return ((maxAngle!!- minAngle)*random.nextDouble())+minAngle
        }
    }


    fun getVelocity():Vector{
        val speed = getSpeed()
        val radian = getRadian()
        val vx = speed*Math.cos(radian).toFloat()
        val vy = speed*Math.sin(radian).toFloat()
        return Vector(vx,vy)
    }



}