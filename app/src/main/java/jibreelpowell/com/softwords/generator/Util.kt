package jibreelpowell.com.softwords.generator

import java.util.*

/**
 * Created by jibreel on 9/17/17.
 */

fun ClosedRange<Int>.random() : Int {
    val range = endInclusive - start
    when {
        range == 0 -> return start
        range > 0 -> return Random().nextInt(range) + start
        else -> throw IllegalStateException("Range must be positive")
    }
}

fun <T> Array<T>.random() = this[(0..lastIndex).random()]

fun <T> List<T>.random() = this[(0..lastIndex).random()]
