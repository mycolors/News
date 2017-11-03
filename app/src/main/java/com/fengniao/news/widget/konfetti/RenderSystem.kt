package com.fengniao.news.widget.konfetti

import android.graphics.Canvas
import java.util.*

class RenderSystem(private val location: LocationModule,
                   private val velocity: VelocityModule,
                   private val sizes: Array<Size>,
                   private val shapes: Array<Shape>,
                   private val colors: IntArray,
                   private val config: ConfettiConfig,
                   private val emitter: Emitter) {
    private val random = Random()
    private var gravity = Vector(0f, 0.01f)
    private val particles: MutableList<Confetti> = mutableListOf()

    init {
        emitter.addConfettiFunc = this::addConfetti
    }

    private fun addConfetti() {
        particles.add(Confetti(location = Vector(location.x, location.y),
                size = sizes[random.nextInt(sizes.size)],
                shape = shapes[random.nextInt(shapes.size)],
                color = colors[random.nextInt(colors.size)],
                lifespan = config.timeToLive,
                fadeOut = config.fadeOut,
                velocity = this.velocity.getVelocity()))
    }

    fun render(canvas: Canvas, deltaTime: Float) {
        emitter.createConfetti(deltaTime)

        for (i in particles.size - 1 downTo 0) {
            val particle = particles[i]
            particle.applyForce(gravity)
            particle.render(canvas, deltaTime)
            if (particle.isDead()) particles.removeAt(i)
        }
    }

    fun getActiveParticles():Int = particles.size

    fun isDoneEmitting():Boolean = emitter.isFinished()&&particles.size == 0

}