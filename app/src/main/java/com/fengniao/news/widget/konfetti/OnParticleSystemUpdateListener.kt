package com.fengniao.news.widget.konfetti

interface OnParticleSystemUpdateListener {
    fun onParticleSystemStarted(view: KonfettiView, system: ParticleSystem, activeSystems: Int)

    fun onParticleSystemEnded(view: KonfettiView, system: ParticleSystem, activeSystems: Int)

}