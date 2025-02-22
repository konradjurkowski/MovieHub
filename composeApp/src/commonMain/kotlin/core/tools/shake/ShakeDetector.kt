package core.tools.shake

interface ShakeDetector {
    fun start(onShake: () -> Unit)
    fun stop()
}
