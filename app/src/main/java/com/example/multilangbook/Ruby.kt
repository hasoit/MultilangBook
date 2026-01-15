package com.example.multilangbook

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.mlkit.nl.translate.TranslateLanguage

class Ruby(
    val words: List<Word> = listOf(Word()),
    val translation: String = "", val o: Options = Options()
) {
    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    fun render(lastIndex: Int = 0, o: Options /*= Options()*/) {
        var showTranslation by remember { mutableStateOf(false) }
        var lastIndexInner by remember { mutableStateOf(lastIndex) }
        var op by remember { mutableStateOf(o) }

//        LaunchedEffect(o) {}

        SideEffect {
            if (lastIndexInner != lastIndex) {
                showTranslation = false
                lastIndexInner = lastIndex
            }
        }

        Column(Modifier.background(colorResource(R.color.gray))) {
            FlowRow(Modifier.fillMaxWidth()) {
                words.forEach { word ->
                    word.render(
                        Word.Options(o.ruby, o.transliteration, o.compare, o.comparelanguage, o.textsize + 4),
                        lastIndex
                    )
                }
            }

            val enflag = if (o.flags) "${Translator.languageToFlag["en"]} " else ""

            Text(
                /*"${op.translation} " + */
                if (o.translation || showTranslation) {
                    "$enflag$translation"
                } else "$enflag[Click to show translation]",
                Modifier.clickable {
                    showTranslation = !showTranslation
//                    o.translation = !o.translation
//                    op = op.copy(translation = showTranslation)
                    op = op.translation()
                }.padding(4.dp),
                color = Colors.dunno(),
                fontSize =  o.textsize.sp
            )

            if (o.compare) {
                val sent = words.joinToString(" ") { it.word }
                var comparedTranslation by remember { mutableStateOf("") }
                val clang = if (o.flags) "${Translator.languageToFlag[translator.targetLanguage]} " else ""

                translator.translateAsync(sent).addOnSuccessListener { comparedTranslation = it }

                Text(
                    if (o.translation || showTranslation) {
                        "$clang$comparedTranslation"
                    } else "$clang[Click to show translation]",
                    Modifier.clickable {
//                        showTranslation = !showTranslation
                        op = op.translation()
                    }.padding(4.dp),
                    color = Colors.green(),
                    fontSize = o.textsize.sp
                )
            }
        }
    }

    override fun toString(): String {
        return "[\n${words.joinToString("\n")}\n] :: $translation"
    }

    fun repr(): String {
        return "[\n${words.joinToString("\n")}\n] :: $translation"
    }

    operator fun component1() = words
    operator fun component2() = translation

    data class Options(
        var translation: Boolean = false,
        var ruby: Boolean = false,
        var transliteration: Boolean = false,
        var flags: Boolean = false,
        var compare: Boolean = false,
        val comparelanguage: String = TranslateLanguage.UKRAINIAN,
        var textsize: Int = 16,
    ) {
        fun translation() = copy(translation = !translation)
    }
}