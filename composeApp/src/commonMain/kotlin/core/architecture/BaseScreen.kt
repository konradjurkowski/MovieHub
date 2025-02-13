package core.architecture

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import kotlin.random.Random

abstract class BaseScreen : Screen {

    final override val key: ScreenKey = super.key + Random.nextDouble()

    @Composable
    abstract override fun Content()
}
