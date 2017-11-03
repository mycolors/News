package com.fengniao.news.widget.konfetti

import android.graphics.Color
import java.util.*


class ParticleSystem(private val konfettiView: KonfettiView) {

    private val random = Random()

    private var location = LocationModule(random)
    private var velocity = VelocityModule(random)

    private var colors = intArrayOf(Color.RED)
    private var size = arrayOf(Size(16))
    private var shapes = arrayOf(Shape.RECT)
    private var confettiConfig = ConfettiConfig()


    internal lateinit var renderSystem: RenderSystem

    fun setPosition(x: Float, y: Float): ParticleSystem {
        location.setX(x)
        location.setY(y)
        return this
    }

    fun setPosition(minX: Float, maxX: Float? = null, minY: Float, maxY: Float? = null): ParticleSystem {
        location.betweemX(minX, maxX)
        location.betweemY(minY, maxY)
        return this
    }

    //vararg关键字修饰可变参数
    fun addColors(vararg colors: Int): ParticleSystem {
        this.colors = colors
        return this
    }

    fun addSizes(vararg possibleSizes: Size): ParticleSystem {
        this.size = possibleSizes.filterIsInstance<Size>().toTypedArray()
        return this
    }

    fun addShapes(vararg shapes: Shape): ParticleSystem {
        this.shapes = shapes.filterIsInstance<Shape>().toTypedArray()
        return this
    }

    fun setDirection(minDirection: Double, maxDirection: Double): ParticleSystem {
        velocity.minAngle = Math.toRadians(minDirection)
        velocity.maxAngle = Math.toRadians(maxDirection)
        return this
    }

    fun setSpeed(minSpeed: Float, maxSpeed: Float): ParticleSystem {
        velocity.minSpeed = minSpeed
        velocity.maxSpeed = maxSpeed
        return this
    }

    fun setFadeOutEnable(fade: Boolean): ParticleSystem {
        confettiConfig.fadeOut = fade
        return this
    }

    fun setTimeToLive(timeInMs: Long): ParticleSystem {
        confettiConfig.timeToLive = timeInMs
        return this
    }

    fun burst(amount: Int) {
        startRenderSystem(BurstEmitter().build(amount))
    }

    fun stream(particlesPerSecond: Int, emittingTime: Long) {
        val stream = StreamEmitter().build(particlesPerSecond = particlesPerSecond,
                emittingTime = emittingTime)
        startRenderSystem(stream)
    }

    private fun startRenderSystem(emitter: Emitter) {
        renderSystem = RenderSystem(location, velocity, size, shapes, colors, confettiConfig, emitter)
        start()
    }

    private fun start() {
        konfettiView.start(this)
    }

    fun doneEmitting():Boolean = renderSystem.isDoneEmitting()

    fun activeParticles():Int = renderSystem.getActiveParticles()


}