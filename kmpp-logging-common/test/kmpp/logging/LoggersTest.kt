package kmpp.logging

import kotlin.test.Test
import kotlin.test.assertSame

private val logger = initTopLevelLogger {}

class LoggersTest {
    private class LoggerContainer {
        companion object : Log() {
            const val SOME_TEST_VAL = "42"
        }

        val instanceLogger = logger
        fun error(msg: String) = logger.error { msg }
    }

    @Test fun printLoggerExamples() {
        println("Running the test!")
        val loggerContainer1 = LoggerContainer()
        val loggerContainer2 = LoggerContainer()
        loggerContainer1.error(LoggerContainer.SOME_TEST_VAL)
        loggerContainer2.error(LoggerContainer.SOME_TEST_VAL)
        assertSame(loggerContainer1.instanceLogger, loggerContainer2.instanceLogger)
        logger.error { "topLevelLogger" }
    }
}
