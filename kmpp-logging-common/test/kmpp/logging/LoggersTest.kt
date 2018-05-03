package kmpp.logging

import klogging.KLoggerHolder
import klogging.WithLogging
import kotlin.test.Test
import kotlin.test.assertTrue

private val logger = initTopLevelLogger {}

class LoggersTest {
    private class LoggerContainer {
        companion object : Log() {
            const val SOME_TEST_VAL = "42"
        }

        val instanceLogger = logger
        fun debug(msg: String) = logger.debug { msg }
    }

    private class OtherLoggerContainer {
        companion object : WithLogging by KLoggerHolder()
        fun debug(msg: String) = logger.error { msg }
    }

    @Test fun printLoggerExamples() {
        val loggerContainer1 = LoggerContainer()
        val loggerContainer2 = LoggerContainer()
        loggerContainer1.debug(LoggerContainer.SOME_TEST_VAL)
        loggerContainer2.debug(LoggerContainer.SOME_TEST_VAL)
        assertTrue { loggerContainer1.instanceLogger === loggerContainer2.instanceLogger }
        logger.debug { "topLevelLogger" }
        val loggerContainer3 = OtherLoggerContainer()
        loggerContainer3.debug("otherLoggerContainer")
    }
}
