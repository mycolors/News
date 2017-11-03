package com.fengniao.news.widget.konfetti

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View


class KonfettiView : View {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    private val systems: MutableList<ParticleSystem> = mutableListOf()

    private val timer: TimerIntegration = TimerIntegration()

    private var onParticleSystemUpdateListener: OnParticleSystemUpdateListener? = null

    fun getActiveSystems() = systems

    fun build(): ParticleSystem = ParticleSystem(this)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val delaTime = timer.getDelatime()
        for (i in systems.size - 1 downTo 0) {
            val particleSystem = systems[i]
            particleSystem.renderSystem.render(canvas, delaTime)
            if (particleSystem.doneEmitting()){
                systems.removeAt(i)
                onParticleSystemUpdateListener?.onParticleSystemEnded(this,particleSystem,systems.size)
            }
        }

        if (systems.size!=0){
            invalidate()
        }else{
            timer.reset()
        }
    }

    class TimerIntegration {
        private var previousTime: Long = -1L

        fun reset() {
            previousTime = -1L
        }

        fun getDelatime(): Float {
            if (previousTime == -1L) previousTime = System.nanoTime()

            val currentTime = System.nanoTime()
            val dt: Long = (currentTime - previousTime) / 1000000
            previousTime = currentTime
            return dt.toFloat() / 1000
        }
    }


    fun start(particleSystem: ParticleSystem){
        systems.add(particleSystem)
        onParticleSystemUpdateListener?.onParticleSystemStarted(this,particleSystem,systems.size)
        invalidate()
    }

}