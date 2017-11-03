package com.fengniao.news.widget.konfetti

class StreamEmitter : Emitter() {
    private var maxParticles = -1
    private var particlesCreated = 0
    private var emittingTime: Long = 0
    private var elapsedTime: Float = 0F
    private var amountPerMs: Float = 0f
    private var createParticleMs: Float = 0f

    fun build(particlesPerSecond: Int,
              emittingTime: Long = 0L,
              maxParticles: Int = -1): StreamEmitter {
        this.maxParticles = maxParticles
        this.emittingTime = emittingTime
        this.amountPerMs = 1f / particlesPerSecond
        return this
    }


    override fun createConfetti(deltaTime: Float) {
        createParticleMs += deltaTime
        if (createParticleMs >= amountPerMs && !isTimeElapsed()) {
            val amount: Int = (createParticleMs / amountPerMs).toInt()
            (1..amount).forEach { createParticle() }

            createParticleMs %= amountPerMs
        }
        elapsedTime += deltaTime * 1000


    }

    private fun createParticle() {
        if (reachedMaxParticles()) return
        particlesCreated++
        addConfettiFunc?.invoke()
    }


    private fun reachedMaxParticles(): Boolean = maxParticles in 1..(particlesCreated)

    private fun isTimeElapsed(): Boolean = if (emittingTime == 0L) false else elapsedTime >= emittingTime

    override fun isFinished(): Boolean {
        return if (emittingTime > 0L) {
            elapsedTime >= emittingTime
        } else {
            maxParticles >= particlesCreated
        }
    }
}
