package kmulti.logging

import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * @author Andrew Bissell
 */

private val topLevelLogger = topLevelLogger {}

class JvmLoggersTest {
    companion object : CompanionLogger() {
        private val companionObjectFieldLogger = companionLogger {}
    }

    class NestedClass {
        companion object : CompanionLogger() {
            private val companionObjectFieldLogger = companionLogger {}
        }

        val classStr = this::class.toString().trimClassStr()

        fun inheritedError(msg: () -> Any?) = logger.error { msg() }
        fun fieldError(msg: () -> Any?) = companionObjectFieldLogger.error { msg() }
    }

    @Test
    fun verifyLoggerPrefixes() {
        // Print some output from the various loggers to stderr (via slf4j-simple), and capture
        // in a PrintStream
        val baos = ByteArrayOutputStream()
        val ps = PrintStream(baos)
        val oldErr = System.err
        System.setErr(ps)
        logger.error { "inherited logger on main companion object" }
        companionObjectFieldLogger.error { "field logger on main companion object" }
        val nested = NestedClass()
        nested.inheritedError { "inherited logger on nested class companion object" }
        nested.fieldError { "field logger on nested class companion object" }
        topLevelLogger.error { "top level" }
        System.err.flush()
        System.setErr(oldErr)

        // Validate that the log message prefixes sent to stderr match the expected
        val mainClassStr = this::class.toString().trimClassStr()
        val outputStrs = baos.toString().lines()
        assertEquals(getLoggerPrefix(outputStrs[0]), mainClassStr)
        assertEquals(getLoggerPrefix(outputStrs[1]), mainClassStr)
        assertEquals(getLoggerPrefix(outputStrs[2]), nested.classStr)
        assertEquals(getLoggerPrefix(outputStrs[3]), nested.classStr)
        assertEquals(getLoggerPrefix(outputStrs[4]), mainClassStr)
    }

}

private fun getLoggerPrefix(msg: String): String =
    msg.substringBeforeLast(" - ")
        .substringAfterLast("ERROR ")

private fun String.trimClassStr() = this
    .removePrefix("class ")
    .removeSuffix(" (Kotlin reflection is not available)")
