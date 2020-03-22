package org.tuvecinoteayuda.utils

/**
 * Allows an else block for null values
 * Similar to let, it returns a value
 */
inline fun <T, R> T?.letOrElse(notNullBlock: (T) -> R, nullBlock: () -> R): R {
    return this?.let(notNullBlock) ?: nullBlock.invoke()
}