package com.example.multilangbook

import androidx.compose.runtime.Composable

class Book(data: String) {
    val dictionary: List<Word>
    val chapters: List<Chapter>
    var dbg = "\n"

    @Composable
    fun render() {

    }

    init {
        val content = data.split(Sep.DICTIONARY_CONTENT)

        if (content.size == 2) {
            val (dictionaryContent, bookContent) = content

            dictionary = dictionaryContent.split(Sep.DICTIONARY_ENTRY).mapIndexed() { i, dictionaryEntry ->
                val entry = dictionaryEntry
                    .split(Sep.WORD_TRANSLATION)
                if (entry.size == 2) {
                    val (word, translation) = entry
                    Word(word, translation)
                } else {
                    dbg += "Error splitting word and translation in parsing dictionary: (@$i) [${entry}] <${entry.size}>"
                    Word()
                }
            }

            chapters = bookContent.split(Sep.CHAPTER).map { Chapter(it, dictionary) }
        } else {
            dbg += "Probably just an invalid book file at this point"
            dictionary = emptyList()
            chapters = emptyList()
        }
    }

    operator fun get(index: Int) = chapters[index]

    class Sep {
        companion object {
            const val WORD_TRANSLATION = '\u0000'
            const val DICTIONARY_ENTRY = '\u0001'
            const val DICTIONARY_CONTENT = '\u0002'
            const val CHAPTER = '\u0003'
        }
    }
}

class Bookmark(val index: Int, val chapter: Int) {
    override fun toString(): String {
        return "<$index : $chapter>"
    }

    fun s(): String {
        return "$index${Sep.INDEX_CHAPTER}$chapter"
    }

    class Sep {
        companion object {
            const val AMOUNT_BOOKMARK = "\u0000"
            const val BOOKMARK = "\u0001"
            const val INDEX_CHAPTER = "\u0002"
        }
    }

    operator fun component1() = index
    operator fun component2() = chapter
}

class Chapter(data: String, dictionary: List<Word>) {
    @Composable
    fun render() {

    }

    val list: List<Ruby> = data.split(Sep.RUBY).map { ruby ->
        val wordIndicesSentence = ruby.split(Sep.WORDINDICES_SENTENCE)

        if (wordIndicesSentence.size == 2) {
            val (wordIndices, sentence) = wordIndicesSentence
            val words = wordIndices.split(Sep.WORD_INDEX).map { dictionary[it.toInt()] }

            Ruby(words, sentence)
        } else {
            Ruby()
        }
    }

    operator fun invoke(amount: Int) = list.chunked(amount)

    class Sep {
        companion object {
            const val WORD_INDEX = '\u0004'
            const val WORDINDICES_SENTENCE = '\u0005'
            const val RUBY = '\u0006'
        }
    }
}
