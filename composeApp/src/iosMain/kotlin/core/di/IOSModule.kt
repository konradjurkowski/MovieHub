package core.di

import core.tools.shake.IOSShakeDetector
import core.tools.shake.ShakeDetector
import org.koin.dsl.module

val iosModule = module {
    factory<ShakeDetector> { IOSShakeDetector() }
}
