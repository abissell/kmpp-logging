package kmulti.logging

import klogging.KLogger
import klogging.KLoggers

/**
 * @author Andrew Bissell
 */

/**
 * Provides a [logger] for use in an extending `companion object`, which has `"$Companion"` and
 * other unwanted noise removed from its standard logger prefix. Intended to be used to provide a
 * static logger for any given class:
 * ```
 * companion object : CompanionLogger()
 * ```
 */
abstract class CompanionLogger {
    @Suppress("LeakingThis")
    val logger: KLogger = initObjectLogger(this)
}

/**
 * Provides a [logger][KLogger] for use as a field on `companion object`, which has `"$Companion"`
 * and other unwanted noise removed from its standard logger prefix. Intended to be used to provide
 * a static logger for any given class:
 * ```
 * companion object {
 *     val logger = companionLogger {}
 * }
 * ```
 */
fun companionLogger(obj: Any): KLogger = initObjectLogger(obj)

private fun initObjectLogger(obj: Any): KLogger {
    val className = obj::class.toString()
        .removeCommonPrefixAndSuffix()
        .substringBefore("\$Companion")
    return KLoggers.logger(className)
}

/**
 * Provides a top-level [logger][KLogger] for use in Kotlin files, which has the numeral suffix,
 * `"Kt$"`, and other unwanted noise removed from its standard logger prefix. Intended to be used to
 * provide a static logger as a top-level property:
 * ```
 * private val logger = topLevelLogger {}
 * ```
 */
fun topLevelLogger(obj: Any): KLogger {
    val className = obj::class.toString()
        .removeCommonPrefixAndSuffix()
        .substringBefore("Kt\$")
    return KLoggers.logger(className)
}

private fun String.removeCommonPrefixAndSuffix() =
    this.removePrefix("class ").removeSuffix(" (Kotlin reflection is not available)")
