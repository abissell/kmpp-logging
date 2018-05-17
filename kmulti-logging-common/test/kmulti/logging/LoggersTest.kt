package kmulti.logging

import kotlin.test.Test
import kotlin.test.assertSame

/**
 * @author Andrew Bissell
 */

private val topLevelLogger = topLevelLogger {}

class LoggersTest {
    companion object : CompanionLogger() {
        private val companionObjectFieldLogger = companionLogger {}
    }

    private class NestedClass {
        companion object : CompanionLogger() {
            private val companionObjectFieldLogger = companionLogger {}
        }

        val inheritedLogger = logger
        fun inheritedError(msg: () -> Any?) = logger.error { msg() }
        val fieldLogger = companionObjectFieldLogger
        fun fieldError(msg: () -> Any?) = companionObjectFieldLogger.error { msg() }
    }

    @Test
    fun printLoggerExamples() {
        val nested1 = NestedClass()
        val nested2 = NestedClass()
        assertSame(nested1.inheritedLogger, nested2.inheritedLogger)
        assertSame(nested1.fieldLogger, nested2.fieldLogger)
        logger.error { "inherited logger on main companion object" }
        companionObjectFieldLogger.error { "field logger on main companion object" }
        nested1.inheritedError { "inherited logger on nested class companion object" }
        nested1.fieldError { "field logger on nested class companion object" }
        topLevelLogger.error { "top level" }
    }
}
