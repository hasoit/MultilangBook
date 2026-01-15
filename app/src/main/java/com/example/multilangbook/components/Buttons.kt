package com.example.multilangbook.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.multilangbook.Book
import com.example.multilangbook.Colors

class SwitchButtonColor(
    val on: Color = Color.Green, /*Colors.green()*/ // :(
    val off: Color = Color.Red, /*Colors.red()*/ // :(
)

class ButtonOptions(
    private val onClick: () -> Unit,
    private val color: Color = Color.Magenta,
    private val modifier: Modifier = Modifier,
    private val enabled: Boolean = true,
    private val content: @Composable (RowScope.() -> Unit),
) {
    //    val colors = ButtonDefaults.buttonColors(color, disabledContainerColor = Color.Gray)
    val shape = RoundedCornerShape(64.dp)

    @Composable
    fun button() = Button(
        onClick, modifier, enabled, shape,
        ButtonDefaults.buttonColors(color, disabledContainerColor = Color.Gray)
    ) { content() }
}

class SwitchButton(
    private val text: String,
    private val state: Boolean = true,
    private val modifier: Modifier = Modifier,
    private val colors: SwitchButtonColor = SwitchButtonColor(),
    private val onClick: () -> Unit
) {
    @Composable
    fun switchButton() = Button(
        onClick, modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(if (state) colors.on else colors.off)
    ) { Text(text) }
}

@Composable
fun switchButtons(vararg bos: SwitchButton) {
    val buttonAmount = if (bos.size % 2 == 0) bos.size else bos.size - 1
    val range = (0..<buttonAmount step 2)

//    Column {
    if (bos.size > 1) range.forEach {
        Row(Modifier.fillMaxWidth()) {
            Box(Modifier.weight(1f), Alignment.Center) {
                bos[it].switchButton()
            }
            Spacer(Modifier.width(8.dp))
            Box(Modifier.weight(1f), Alignment.Center) {
                bos[it + 1].switchButton()
            }
        }
    }

    if (bos.size == 1 || bos.size % 2 != 0) {
        Row(Modifier.fillMaxWidth()) {
            Box(Modifier.weight(1f), Alignment.Center) {
                bos.last().switchButton()
            }
        }
    }
//    }
}

@Composable
fun dualButton(
    button1: ButtonOptions,
    button2: ButtonOptions,
    modifier: Modifier,
    arrangement: Arrangement.Horizontal = Arrangement.SpaceBetween,
//    alignment: Alignment.Vertical = Alignment.Bottom
) {
    Row(modifier, arrangement/*, alignment*/) {
        button1.button()
        button2.button()
    }
}

@Composable
fun menubutton(b: Boolean, icon: @Composable () -> Unit, f: () -> Unit) = Button(
    f, colors = ButtonDefaults.buttonColors(
        if (b) Colors.gold() else Colors.green()
    )
) { icon() }

@Composable
fun db(
    oc1: () -> Unit, oc2: () -> Unit, e1: Boolean, e2: Boolean
) = Row(Modifier.fillMaxWidth().padding(8.dp), Arrangement.SpaceBetween) {
    Button(oc1, w(144), e1) { Text("previous") }
    Button(oc2, w(144), e2) { Text("next") }
}

@Composable
fun hdictionaryExplorer(book: Book, ob: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        // Scrollable content
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            repeat(50) {
                Text("Item $it", modifier = Modifier.padding(4.dp))
            }
        }

        // Fixed bottom buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = { /* action */ }) {
                Text("Cancel")
            }
            Button(onClick = { /* action */ }) {
                Text("Confirm")
            }
        }
    }
}