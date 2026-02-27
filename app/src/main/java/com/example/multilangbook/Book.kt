package com.example.multilangbook

import androidx.compose.runtime.Composable

class Book(data: String) {
    val chapters = data.split(Sep.CHAPTERS).map { Chapter(it) }

    @Composable
    fun render() {

    }

    operator fun get(index: Int) = chapters[index]

    class Sep {
        companion object {
            const val CHAPTERS = '\u0000'
        }
    }
}

class Chapter(data: String) {
    val nameAndContent = data.split(Sep.NAME_CONTENT)
    val name = nameAndContent.first()
    val content = nameAndContent.last()
    val sections = content.split(Sep.SECTIONS).map { Section(it) }

    operator fun get(i: Int) = sections[i]

    class Sep {
        companion object {
            const val NAME_CONTENT = '\u0001'
            const val SECTIONS = '\u0002'
        }
    }
}

class Section(data: String) {
    val nameAndContent = data.split(Sep.NAME_CONTENT)
    val name = nameAndContent.first()
    val content = nameAndContent.last()
    val list = content.split(Sep.RUBY).map {
        val (words, translation) = it.split(Sep.WORDS_TRANSLATION)
        Ruby(words.split(Sep.WORDS_AND_TRANSLATIONS).map {
            it.split(Sep.WORD_AND_TRANSLATION).let { Word(it.first(), it.last()) }
        }, translation)
    }

    operator fun invoke(amount: Int) = list.chunked(amount)

    class Sep {
        companion object {
            const val NAME_CONTENT = '\u0003'
            const val RUBY = '\u0004'
            const val WORDS_AND_TRANSLATIONS = '\u0005'
            const val WORD_AND_TRANSLATION = '\u0006'
            const val WORDS_TRANSLATION = '\u0007'
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