package com.fengniao.news.widget.konfetti

class BurstEmitter : Emitter() {

    private var amountOfParticles = 0
        set(value) {
            field = if (value > 1000) 1000 else value
        }

    private var isStarted = false

    fun build(amountOfParticles: Int): Emitter {
        this.amountOfParticles = amountOfParticles
        isStarted = true
        return this
    }

    override fun createConfetti(deltaTime: Float) {
        if (!isStarted) {
            isStarted = true
            for (i in 1..amountOfParticles) {
                addConfettiFunc?.invoke()
            }
        }

    }

    override fun isFinished(): Boolean  = isFinished()
}