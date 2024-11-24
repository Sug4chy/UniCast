package ru.sug4chy.uni_cast.utils

import mu.KLogger
import mu.KotlinLogging
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class LoggingDelegate<in T : Any> : ReadOnlyProperty<T, KLogger> {

    override fun getValue(thisRef: T, property: KProperty<*>): KLogger =
        KotlinLogging.logger(thisRef.javaClass.name)
}

fun <T : Any> logger(): LoggingDelegate<T> =
    LoggingDelegate()