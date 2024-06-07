package core.tools.logger

import io.ktor.client.plugins.logging.Logger
import co.touchlab.kermit.Logger as Kermit

class KtorLogger : Logger {
    override fun log(message: String) {
        Kermit.i("KtorLogger") { message }
    }
}
