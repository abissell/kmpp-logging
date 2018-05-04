package kmpp.logging

import klogging.KLogger
import klogging.KLoggers

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
