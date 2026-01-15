package com.example.multilangbook

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.mlkit.nl.translate.TranslateLanguage

class Word(val word: String = "", val translation: String = "") {
    @Composable
    fun render(
//        word: Word,
//        showRubyInit: Boolean = false,
//        showTransliterations: Boolean = false,
//        compare: Boolean = false,
//        compare: String? = null,
        o: Options = Options(),
        lastIndex: Int = 0,
    ) {
        var showRuby by remember { mutableStateOf(false) }
        var lastIndexInner by remember { mutableStateOf(lastIndex) }
        var ct by remember { mutableStateOf("") }
//        var cl by remember { mutableStateOf("") }

//        var c by remember { mutableStateOf(o.compare) }
//        var op by remember { mutableStateOf(o) }
//        var t by remember { mutableStateOf(translator) }
//        val clang = "${Translator.languageToFlag[translator.targetLanguage]}"

//        LaunchedEffect(translator.targetLanguage) {
//            cl = translator.targetLanguage
//        }

        SideEffect {
            if (lastIndexInner != lastIndex) {
                showRuby = false
                lastIndexInner = lastIndex
            }
        }

        Column(
            Modifier.padding(4.dp).clickable { showRuby = !showRuby },
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (o.ruby || showRuby) Text(
//                if (o.ruby || showRuby) {
                translation
//                } else ""
                ,
                Modifier, Colors.gold(), o.textsize.sp / 2, //10.sp,
//                Modifier.background(Colors.green()),
                maxLines = 1
            )

            Text(
                word, Modifier, colorResource(R.color.purple_200), o.textsize.sp// 20.sp,
//                Modifier.background(Colors.green()),
            )

//            if (compare != null) {
            if (o.compare) {
                translator.translateAsync(word).addOnSuccessListener {
                    ct = it
                }

                Text(
                    if (o.ruby || showRuby) ct else "",
                    Modifier, Colors.red(), o.textsize.sp / 2,// 10.sp,
                    maxLines = 1,
                )
            }

            if (o.transliteration) Text(
                Translator.transliterate(word), Modifier,
                Colors.teal(), o.textsize.sp / 2,// 10.sp,
            )
        }
    }

    override fun toString(): String {
        return "'$word' :: '$translation'"
    }

    fun repr(): String {
        return "'$word' :: '$translation'"
    }

    operator fun component1() = word
    operator fun component2() = translation

    data class Options(
        val ruby: Boolean = false,
        val transliteration: Boolean = false,
        val compare: Boolean = false,
        val cl: String = TranslateLanguage.UKRAINIAN,
        val textsize: Int = 16,
    )
}