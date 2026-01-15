package com.example.multilangbook

import com.google.android.gms.tasks.Task
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.nl.translate.TranslatorOptions
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class Translator(
    val sourceLanguage: String = TranslateLanguage.ENGLISH,
    val targetLanguage: String = TranslateLanguage.RUSSIAN,
) {
    private val translator: Translator

    init {
//        Log.d("translator", "translator instance created")

        val options = TranslatorOptions.Builder()
            .setSourceLanguage(sourceLanguage)
            .setTargetLanguage(targetLanguage)
            .build()

        translator = Translation.getClient(options)

        translator.downloadModelIfNeeded()
            .addOnSuccessListener {}
            .addOnFailureListener {}
    }

    fun translateAsync(s: String): Task<String> {
        return translator.translate(s)
    }

    fun translate(s: String): String {
        try {
            val translation = translator.translate(s)
            while (!translation.isComplete) {
                if (translation.isCanceled) break
            }
            return translation.result!!
        } catch (e: Exception) {
            return e.toString()
        }
    }

    suspend fun st(s: String): String {
        return suspendCoroutine { c ->
            translator.translate(s).addOnSuccessListener {
                c.resume(it)
            }
        }
    }

    companion object {
        fun transliterate(text: String): String {
            return text.map { russianToEnglishTransliterationMap[it] ?: it.toString() }.joinToString("")
        }

        private val russianToEnglishTransliterationMap = mapOf(
            'А' to "A", 'Б' to "B", 'В' to "V", 'Г' to "G", 'Д' to "D",
            'Е' to "E", 'Ё' to "Yo", 'Ж' to "Zh", 'З' to "Z", 'И' to "I",
            'Й' to "Y", 'К' to "K", 'Л' to "L", 'М' to "M", 'Н' to "N",
            'О' to "O", 'П' to "P", 'Р' to "R", 'С' to "S", 'Т' to "T",
            'У' to "U", 'Ф' to "F", 'Х' to "Kh", 'Ц' to "Ts", 'Ч' to "Ch",
            'Ш' to "Sh", 'Щ' to "Shch", 'Ъ' to "\"", 'Ы' to "Y", 'Ь' to "'",
            'Э' to "E", 'Ю' to "Yu", 'Я' to "Ya",

            'а' to "a", 'б' to "b", 'в' to "v", 'г' to "g", 'д' to "d",
            'е' to "e", 'ё' to "yo", 'ж' to "zh", 'з' to "z", 'и' to "i",
            'й' to "y", 'к' to "k", 'л' to "l", 'м' to "m", 'н' to "n",
            'о' to "o", 'п' to "p", 'р' to "r", 'с' to "s", 'т' to "t",
            'у' to "u", 'ф' to "f", 'х' to "kh", 'ц' to "ts", 'ч' to "ch",
            'ш' to "sh", 'щ' to "shch", 'ъ' to "\"", 'ы' to "y", 'ь' to "'",
            'э' to "e", 'ю' to "yu", 'я' to "ya"
        )

        val languages = listOf(
            TranslateLanguage.UKRAINIAN,
            TranslateLanguage.FINNISH,
            TranslateLanguage.ROMANIAN,
            TranslateLanguage.HUNGARIAN,
            TranslateLanguage.GERMAN,
            TranslateLanguage.POLISH,
            TranslateLanguage.ALBANIAN,
            TranslateLanguage.JAPANESE,
            TranslateLanguage.CHINESE,
            TranslateLanguage.FRENCH,
            TranslateLanguage.SPANISH,
            TranslateLanguage.SLOVAK,
            TranslateLanguage.CROATIAN,
            TranslateLanguage.MACEDONIAN,
            TranslateLanguage.ENGLISH,
            TranslateLanguage.LITHUANIAN,
            TranslateLanguage.LATVIAN,
            TranslateLanguage.ITALIAN,
            TranslateLanguage.ARABIC,
        )

        val languageToFlag = mapOf(
            TranslateLanguage.UKRAINIAN to "🇺🇦",
            TranslateLanguage.FINNISH to "🇫🇮",
            TranslateLanguage.ROMANIAN to "🇷🇴",
            TranslateLanguage.HUNGARIAN to "🇭🇺",
            TranslateLanguage.GERMAN to "🇩🇪",
            TranslateLanguage.POLISH to "🇵🇱",
            TranslateLanguage.ALBANIAN to "🇦🇱",
            TranslateLanguage.JAPANESE to "🇯🇵",
            TranslateLanguage.CHINESE to "🇨🇳",
            TranslateLanguage.FRENCH to "🇫🇷",
            TranslateLanguage.SPANISH to "🇪🇸",
            TranslateLanguage.SLOVAK to "🇸🇰",
            TranslateLanguage.CROATIAN to "🇭🇷",
            TranslateLanguage.MACEDONIAN to "🇲🇰",
            TranslateLanguage.ENGLISH to "🇺🇸",
            TranslateLanguage.LITHUANIAN to "\uD83C\uDDF1\uD83C\uDDF9",
            TranslateLanguage.LATVIAN to "la",
            TranslateLanguage.ITALIAN to "it",
            TranslateLanguage.ARABIC to "ar",
        )

    }
}
