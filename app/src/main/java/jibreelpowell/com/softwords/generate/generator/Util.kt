package jibreelpowell.com.softwords.generate.generator

import java.util.*

/**
 * Created by jibreel on 9/17/17.
 */

fun ClosedRange<Int>.random() : Int {
    val range = (endInclusive - start) + 1
    return when {
        range == 0 -> start
        range > 0 -> Random().nextInt(range) + start
        else -> throw IllegalStateException("Range must be positive")
    }
}

fun <T> Array<T>.random() = this[(0..lastIndex).random()]

fun <T> List<T>.random() = this[(0..lastIndex).random()]

fun Char.isVowel() =
        when (this.toLowerCase()) {
            'a', 'e', 'i', 'o', 'u' -> true
            else -> false
        }
