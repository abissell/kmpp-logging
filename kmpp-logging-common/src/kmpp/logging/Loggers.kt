package kmpp.logging

import klogging.KLogger
import klogging.KLoggers

/**
 * @author Andrew Bissell
 */

/**
 * Provides a [logger] for use in an extending class, which has `"$Companion"` and other unwanted
 * noise removed from its standard logger prefix. Intended to be used by companion objects to
 * provide a static logger for any given class implementation:
 * ```
 * companion object : Log() {}
 * ```
 */
abstract class Log {
    @Suppress("LeakingThis")
    val logger: KLogger = initObjectLogger(this)
}

private fun initObjectLogger(obj: Any): KLogger {
    val className = obj::class.toString()
        .removeCommonPrefixAndSuffix()
        .removeCompanionSuffix()
    return KLoggers.logger(className)
}

private fun String.removeCompanionSuffix() = this.removeSuffix("\$Companion")

/**
 * Provides a top-level [logger] for use in Kotlin files, which has `"$Companion"`, `"Kt"`, and
 * other unwanted noise removed from its standard logger prefix. Intended to be used to provide a
 * static logger as a top-level property:
 * ```
 * private val logger = initTopLevelLogger {}
 * ```
 */
fun initTopLevelLogger(obj: Any): KLogger {
    val className = obj::class.toString()
        .removeCommonPrefixAndSuffix()
        .removeNumeralSuffix()
        .removeLoggerNameSuffix()
        .removeKtSuffix()
    return KLoggers.logger(className)
}

private fun String.removeNumeralSuffix() = this.substringBeforeLast('$')

private fun String.removeLoggerNameSuffix() = this.substringBeforeLast('$')

private fun String.removeKtSuffix() = this.substringBefore("Kt")

private fun String.removeCommonPrefixAndSuffix() =
    this.removePrefix("class ").removeSuffix(" (Kotlin reflection is not available)")
