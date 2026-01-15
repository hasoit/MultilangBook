package com.example.multilangbook.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.unit.dp

class IndexElement<T>(val i: Int, val e: T, val last: Int) {
    fun isLast() = i == last
    fun isFirst() = i == 0
    override fun toString() = "$i: $e"
}

val mod = Modifier

fun sidepad(i: Int) = Modifier.padding(start = i.dp, end = i.dp)
fun Modifier.sidepad(i: Int) = padding(start = i.dp, end = i.dp)

fun topsidepad(i: Int) = Modifier.padding(top = i.dp, start = i.dp, end = i.dp)
fun Modifier.topsidepad(i: Int) = padding(top = i.dp, start = i.dp, end = i.dp)

fun bottomsidepad(i: Int) = Modifier.padding(bottom = i.dp, start = i.dp, end = i.dp)
fun Modifier.bottomsidepad(i: Int) = padding(bottom = i.dp, start = i.dp, end = i.dp)

fun <T> pad(ie: (IndexElement<T>), i: Int = 8) = run { if (ie.isFirst()) pad(i) else bottomsidepad(i) }
fun <T> Modifier.pad(i: Int = 8, ie: (IndexElement<T>)) = run { if (ie.isFirst()) pad(i) else bottomsidepad(i) }

fun pad(i: Int = 8, cond: () -> Boolean = { false }) = run { if (cond()) pad(i) else bottomsidepad(i) }
fun Modifier.pad(i: Int = 8, cond: () -> Boolean = { false }) = run { if (cond()) pad(i) else bottomsidepad(i) }

fun pad(i: Int) = Modifier.padding(i.dp)
fun Modifier.pad(i: Int) = padding(i.dp)

fun pads(i: Int) = Modifier.padding(start = i.dp)
fun Modifier.pads(i: Int) = padding(start = i.dp)

fun pade(i: Int) = Modifier.padding(end = i.dp)
fun Modifier.pade(i: Int) = padding(end = i.dp)

fun padt(i: Int) = Modifier.padding(top = i.dp)
fun Modifier.padt(i: Int) = padding(top = i.dp)

fun padb(i: Int) = Modifier.padding(bottom = i.dp)
fun Modifier.padb(i: Int) = padding(bottom = i.dp)

fun padtb(i: Int) = Modifier.padding(top = i.dp, bottom = i.dp)
fun Modifier.padtb(i: Int) = padding(top = i.dp, bottom = i.dp)

fun bg(c: Color) = Modifier.background(c)
fun Modifier.bg(c: Color) = background(c)

fun maxwidth() = Modifier.fillMaxWidth()
fun Modifier.maxwidth() = fillMaxWidth()

fun maxsize() = Modifier.fillMaxSize()
fun Modifier.maxsize() = fillMaxSize()

fun w(i: Int) = Modifier.width(i.dp)
//fun Modifier.w(i: Int) = width(i.dp)

fun keyinput(f: (KeyEvent) -> Unit) = Modifier.onKeyEvent { f(it);true }
fun Modifier.keyinput(f: (KeyEvent) -> Unit) = onKeyEvent { f(it);true }

//@OptIn(ExperimentalFoundationApi::class)
fun clickable(f: () -> Unit) = Modifier.clickable { f() }

//@OptIn(ExperimentalFoundationApi::class)
//fun Modifier.onclick(f: () -> Unit) = onClick { f() }
//
//@OptIn(ExperimentalComposeUiApi::class)
//fun Modifier.ope(f: () -> Unit) = onPointerEvent(PointerEventType.Enter) { f() }
//
//@OptIn(ExperimentalComposeUiApi::class)
//fun ope(f: () -> Unit) = Modifier.onPointerEvent(PointerEventType.Enter) { f() }
//
//@OptIn(ExperimentalComposeUiApi::class)
//fun Modifier.opl(f: () -> Unit) = onPointerEvent(PointerEventType.Exit) { f() }
//
//@OptIn(ExperimentalComposeUiApi::class)
//fun opl(f: () -> Unit) = Modifier.onPointerEvent(PointerEventType.Exit) { f() }

@Composable
fun focusable() = Modifier.focusRequester(remember { FocusRequester() }).focusable()

val spacebetween = Arrangement.SpaceBetween

val centerH = Alignment.CenterHorizontally
val startH = Alignment.Start
val endH = Alignment.End
val centerV = Alignment.CenterVertically