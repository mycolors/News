package com.fengniao.news.widget.konfetti

abstract class Emitter {

    var addConfettiFunc: (() -> Unit)? = null

    abstract fun createConfetti(deltaTime: Float)

    abstract fun isFinished(): Boolean

}