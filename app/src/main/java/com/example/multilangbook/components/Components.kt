package com.example.multilangbook.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.multilangbook.Colors
import com.example.multilangbook.Translator

@Composable
fun intSetting(text: String, onIncrease: () -> Unit, onDecrease: () -> Unit, modifier: Modifier = Modifier) {
    val textMod = Modifier
        .padding(top = 14.dp, bottom = 0.dp, start = 16.dp, end = 16.dp)

    Row(modifier) {
        Button(
            onClick = onDecrease,
            modifier = Modifier.padding(end = 8.dp),
        ) {
            Text("-", fontSize = 24.sp)
        }

        Button(onClick = onIncrease) {
            Text("+", fontSize = 24.sp)
        }

        Text(
            text,
            textMod,
            Color.Magenta,
            20.sp
        )
    }
}

@Composable
fun intInput(modifier: Modifier = Modifier, onDone: (n: Int) -> Unit) {
    var number by remember { mutableStateOf("") }
//    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

//    BackHandler {
//        focusManager.clearFocus()
//    }

    TextField(
        value = number,
        onValueChange = { input ->
            if (input.all { it.isDigit() }) {
                number = input
            }
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done,
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                if (number != "") {
                    onDone(number.toInt())
                }
//                Log.d("textfield", "done")
                focusManager.clearFocus()
            },
        ),
        label = { Text("Enter index") },
        modifier = modifier.onKeyEvent { e ->
            if (e.key == Key.Enter) {
                if (number != "")
                    onDone(number.toInt())
//                Log.d("textfield", "enter")
                focusManager.clearFocus()
            }
            true
        }.fillMaxWidth().onFocusChanged {
            number = ""
        }
    )
}

@Composable
fun text(s: String, modifier: Modifier = Modifier) {
    Text(s, modifier, Colors.dunno())
}

@Composable
fun strInput(
    text: String = "Enter query",
    closeOnDone: Boolean = true,
    loseFocusOnDone: Boolean = true,
    modifier: Modifier = Modifier,
//    ob: () -> Unit = {},
    onDone: (s: String) -> Unit
) {
    var value by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

//    BackHandler {
//        ob()
//    }

    Row {
        Button({ onDone(value.trim()) }) {
            Text("search")
        }

        TextField(
            value = value.trim(),
            onValueChange = { input ->
                value = input
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    if (closeOnDone && value != "") onDone(value)
                    if (loseFocusOnDone) focusManager.clearFocus()
                }
            ),
            label = { Text(text) },
            modifier = modifier.onKeyEvent { e ->
                if (e.key == Key.Enter) {
                    if (value != "") onDone(value.trim())
                    if (loseFocusOnDone) focusManager.clearFocus()
                }
                true
            }.fillMaxWidth().onFocusChanged {
                if (closeOnDone) value = ""
            }
        )
    }
}

fun Color.str(): String {
    return "(${(256 * this.red).toInt()}, ${(256 * this.green).toInt()}, ${(256 * this.blue).toInt()})"
}

@Composable
fun <T> out(s: T) {
    text(s.toString())
    Spacer(Modifier.height(8.dp))
}

class Mct(val s: String, val c: Color) {
    override fun toString(): String {
        return "$s ${c.str()}"
    }
}

@Composable
fun multiColorText(t: List<Mct>) {
    val styledText = buildAnnotatedString {
        t.forEach {
            pushStyle(SpanStyle(it.c))
            append(it.s)
        }
        pop()
    }

    Text(styledText)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun dropdown(
    options: List<String> = listOf(""),
    modifier: Modifier = Modifier,
    so: String = options[0],
    onClick: (i: Int) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(so) }
//    var selectedOption by remember { mutableStateOf(options[so]) }

    ExposedDropdownMenuBox(expanded, { expanded = it }) {
        TextField(
            value = Translator.languageToFlag[selectedOption] ?: selectedOption,
            onValueChange = {},
            readOnly = true,
            modifier = modifier.menuAnchor().fillMaxWidth(),
        )

        ExposedDropdownMenu(expanded, { expanded = false }) {
            options.forEachIndexed { i, option ->
                DropdownMenuItem(
                    { Text(Translator.languageToFlag[option] ?: option) },
                    {
                        onClick(i)
                        selectedOption = Translator.languageToFlag[option] ?: option
                        expanded = false
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun multiChoiceDropdown(
    options: List<String> = listOf(""),
    modifier: Modifier = Modifier,
    onClick: (i: Int, sos: List<Int>) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    val selectedOptions by remember { mutableStateOf(mutableListOf(0)) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it }
    ) {
        TextField(
            value = options[selectedOptions.last()],
            onValueChange = {},
            readOnly = true,
            modifier = modifier.menuAnchor().fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEachIndexed { i, option ->
                DropdownMenuItem(
                    {
                        Text(option)
                    },
                    {
                        onClick(i, selectedOptions)
                        if (i !in selectedOptions) selectedOptions.add(i)
                        else selectedOptions.remove(i)
                        expanded = false
                    },
                    Modifier.background(if (i in selectedOptions) Colors.green() else Colors.red())
                )
            }
        }
    }
}
