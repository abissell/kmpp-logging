package kmpp.logging

import kotlin.test.Test
import kotlin.test.assertSame

/**
 * @author Andrew Bissell
 */

private val logger = initTopLevelLogger {}

class LoggersTest {
    private class LoggerContainer {
        companion object : Log()

        val instanceLogger = logger
        fun error(msg: String) = logger.error { msg }
    }

    @Test fun printLoggerExamples() {
        val loggerContainer1 = LoggerContainer()
        val loggerContainer2 = LoggerContainer()
        assertSame(loggerContainer1.instanceLogger, loggerContainer2.instanceLogger)
        loggerContainer1.error("companion object")
        logger.error { "top level" }
    }
}
