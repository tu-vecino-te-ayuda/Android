@file:Suppress("unused")

package org.tuvecinoteayuda.core.ext

/**
 * Allows an else block for null values
 * Similar to let, it returns a value
 */
inline fun <T, R> T?.letOrElse(notNullBlock: (T) -> R, nullBlock: () -> R): R {
    return this?.let(notNullBlock) ?: nullBlock.invoke()
}

/**
 * Allows an else block for null values
 * Similar to also, it's just a side-effect does not return a value
 */
inline fun <T> T.alsoOrElse(notNullBlock: (T) -> Unit, nullBlock: (T) -> Unit) {
    this?.also(notNullBlock) ?: run(nullBlock)
}

inline fun <T, R> T?.ifNullLet(nullBlock: () -> R): R? {
    return this?.let { return null } ?: nullBlock.invoke()
}
