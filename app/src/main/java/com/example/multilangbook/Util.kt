package com.example.multilangbook.util

import androidx.compose.foundation.background
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kotlin.random.Random
import kotlin.time.Duration
import kotlin.time.measureTime

fun String.surround(s: String): String {
    return "$s$this$s"
}

fun String.quote(): String {
    return "\"$this\""
}

@Composable
fun <t> printl(list: List<t>) {
    list.forEach { e ->
        Text(e.toString())
    }
}

@Composable
fun <t> List<t>.text() = forEach { e ->
    Text(e.toString())
}

fun getFileName(s: String): String {
    return s.split("/", "%2F").last().replace("%20", " ")
}

class TimerResult<T>(val x: T, val duration: Duration)

fun <T> time(lambda: () -> T): TimerResult<T> {
    val result: T

    return measureTime {
        result = lambda()
    }.let { TimerResult(result, it) }
}

fun generateRandomPairsMutable(x: Int, y: Int): MutableList<MutableList<Pair<String, String>>> {
    return MutableList(x) {
        MutableList(y) {
            fun randomString(a: IntRange, b: CharRange = 'a'..'z'): String {
                return (1..Random.nextInt(a.first, a.last)).map { (b).random() }
                    .joinToString("")
            }

            val firstString = randomString(1..16)
            val secondString = randomString(1..16)

            Pair(firstString, secondString)
        }
    }
}

fun randomColor(): Color {
    val r = { Random.nextInt(0, 256) }
    return Color(r(), r(), r())
}

fun String.split(inds: List<Pair<Int, Int>>) {
//    var r: List<String> = mutableListOf()
    val i = listOf(Pair(0, inds[0].first)) + inds
//    return i.map { if (it.first != -1) }
}

fun randomString(a: IntRange = 1..8, b: CharRange = 'a'..'z'): String {
    return (1..Random.nextInt(a.first, a.last))
        .map { (b).random() }
        .joinToString("")
}

fun randomSentence(wordCount: Int, wordLength: IntRange = 1..8, b: CharRange = 'a'..'z'): String {
    return (0..wordCount).joinToString(" ") {
        (1..Random.nextInt(wordLength.first, wordLength.last))
            .map { (b).random() }
            .joinToString("")
    }
}

fun <T> List<T>.takeFrom(startingIndex: Int, amount: Int): List<T> {
    return if (startingIndex in this.indices && startingIndex + amount in this.indices)
        this.subList(startingIndex, startingIndex + amount)
    else this.subList(startingIndex, this.lastIndex)
//    else if (a in this.indices) this.subList(a, this.size - 1)
//    else this.take(b - a)
//        this.takeLast(b - a)
}

fun <T, Y> List<T>.index(e: Y, mod: (T) -> Y): Int {
    return this.map { mod(it) }.indexOf(e)
}

val cs = mapOf(
    0 to Color.Red, 1 to Color.Green, 2 to Color.Blue,
    3 to Color.Yellow, 4 to Color.Magenta, 5 to Color.Cyan,
)

fun bg(i: Int = 0) = Modifier.background(cs[i] ?: Color.Green)
fun Modifier.bg(i: Int = 0) = background(cs[i] ?: Color.Green)

////////////////////////////////////////////////////////////////

