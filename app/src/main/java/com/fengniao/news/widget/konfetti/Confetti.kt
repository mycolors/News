package com.fengniao.news.widget.konfetti

import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import java.util.*

class Confetti(var location: Vector,
               val color: Int,
               val size: Size,
               val shape: Shape,
               var lifespan: Long = -1L,
               val fadeOut: Boolean = true,
               private var acceleration: Vector = Vector(0f, 0f),
               var velocity: Vector = Vector()) {
    private val mass = size.mass
    private var width = size.sizeDp.toFloat()
    private val paint: Paint = Paint()

    private var rotationSpeed = 1f
    private var rotation = 0f
    private var rotationWidth = width
    private var recf = RectF()


    private var speedF = 60F
    private var alpha: Int = 255

    init {
        val minRotationSpeed = 0.29f * Resources.getSystem().displayMetrics.density
        val maxRotationSpeed = minRotationSpeed
        rotationSpeed = maxRotationSpeed * Random().nextFloat() + minRotationSpeed
        paint.color = color
    }

    private fun getSize(): Float = width

    fun isDead(): Boolean = alpha <= 0f

    fun applyForce(force: Vector) {
        val f = force.copy()
        f.div(mass)
        acceleration.add(f)
    }

    fun render(canves: Canvas, delaTime: Float) {
        update(delaTime)
        display(canves)
    }

    fun update(delaTime: Float) {
        velocity.add(acceleration)
        val v = velocity.copy()
        v.mult(delaTime * speedF)
        location.add(v)

        if (lifespan <= 0) updateAlpha(delaTime)
        else lifespan -= (delaTime * 1000).toLong()

        val rSpeed = (rotationSpeed * delaTime) * speedF
        rotation += rSpeed
        if (rotation >= 360) rotation = 0f

        rotationWidth -= rSpeed
        if (rotationWidth < 0) rotationWidth = width
    }

    private fun updateAlpha(delaTime: Float) {
        if (fadeOut) {
            val interval = 5 * delaTime * speedF
            if (alpha - interval < 0) alpha = 0
            else alpha -= (5 * delaTime * speedF).toInt()
        } else {
            alpha = 0
        }
    }

    private fun display(canvas: Canvas) {
        if (location.y > canvas.height) {
            lifespan = 0
            return
        }
        if (location.x > canvas.width ||
                location.x + getSize() < 0 ||
                location.y + getSize() < 0) {
            return
        }

        var left = location.x + (width - rotationWidth)
        var right = location.x + rotationWidth

        if (left > right) {
            left += right
            right = left - right
            left -= right
        }
        paint.alpha = alpha

        recf.set(left, location.y, right, location.y + getSize())
        canvas.save()
        canvas.rotate(rotation, recf.centerX(), recf.centerY())
        when (shape) {
            Shape.RECT -> canvas.drawRect(recf, paint)
            Shape.CIRCLE -> canvas.drawOval(recf, paint)
        }
        canvas.restore()
    }


}