package core.di

import core.tools.shake.AndroidShakeDetector
import core.tools.shake.ShakeDetector
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val androidModule = module {
    factory<ShakeDetector> { AndroidShakeDetector(context = androidContext()) }
}
