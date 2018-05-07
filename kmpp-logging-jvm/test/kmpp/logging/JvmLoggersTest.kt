package kmpp.logging

import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * @author Andrew Bissell
 */

private val topLevelLogger = initTopLevelLogger {}

class JvmLoggersTest {
    companion object : Log()

    @Test fun printLoggerExamples() {
        // Print some output from the companion object and top level logger to stderr, and
        // capture in a PrintStream
        val baos = ByteArrayOutputStream()
        val ps = PrintStream(baos)
        val oldErr = System.err
        System.setErr(ps)
        logger.error { "companion object" }
        topLevelLogger.error { "top level" }
        System.err.flush()
        System.setErr(oldErr)

        // Validate that the log message prefixes match the expected
        val classStr =
            this::class.toString()
                .removePrefix("class ")
                .removeSuffix(" (Kotlin reflection is not available)")
        val outputStrs = baos.toString().split('\n')
        assertEquals(getLoggerPrefix(outputStrs[0]), classStr)
        assertEquals(getLoggerPrefix(outputStrs[1]), classStr)
    }

    private fun getLoggerPrefix(msg: String): String =
            msg.substringBeforeLast(" - ")
                .substringAfterLast("ERROR ")
}
