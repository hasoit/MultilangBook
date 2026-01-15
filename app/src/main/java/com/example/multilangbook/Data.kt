package com.example.multilangbook

import android.content.Context
import com.example.multilangbook.util.getFileName
import java.io.File

private operator fun <E> List<E>.component6(): E {
    return this[5]
}

class State(var file: File = File("")) {
    lateinit var context: Context

    private val amountBookmarkSep = "\u0000"
    private val bookmarkSep = "\u0001"
    private val indexChapterSep = "\u0002"

    var amount: Int = 8
    var bookmarks: List<Bookmark> = (0..4).map { Bookmark(0, 1) }

    fun update(
        amount: Int = 8,
        bookmarks: List<Bookmark> = listOf(Bookmark(0, 1)),
    ) {
        this.amount = amount
        this.bookmarks = bookmarks
    }

    fun validate(libsize: Int = 1): Boolean {
        val d = file.readText().split("\u0000")
        val ds = d.size == 2
        val d1s = d[1].split("\u0001").size == libsize

        return ds && d1s
    }

    fun read(libsize: Int = 1): String {
        val s = if (file.exists()) file.readText() else {
            val iss = "$amount\u0000${
                (1..libsize)
                    .map { Bookmark(0, 1) }
                    .joinToString(bookmarkSep) { it.s() }
            }"
            file.writeText(iss)
            iss
        }

        if (validate()) {
            val (amount, boomarklist) = s.split(amountBookmarkSep)

            update(
                amount.toInt(),
                boomarklist.split(bookmarkSep)
                    .map { bookmark ->
                        bookmark.split(indexChapterSep)
                            .let { indexAndChapter ->
                                val (index, chapter) = indexAndChapter
                                Bookmark(index.toInt(), chapter.toInt())
                            }
                    }
            )
        } else {
            write(libsize)
        }

        return s
    }

    fun write(libsize: Int = 1) {
        file.writeText(getData(libsize))
    }

    fun getData(libsize: Int = 1): String {
        if (bookmarks.size != libsize)
            return "$amount\u0000${
                (1..libsize)
                    .joinToString(bookmarkSep) { Bookmark(0, 1).s() }
            }"
        return "$amount\u0000${bookmarks.joinToString(bookmarkSep) { it.s() }}"
    }

    override fun toString(): String {
        return """amount: $amount
                 |bookmark: [
                 |  ${bookmarks.joinToString(",\n\t")}
                 |]
                 |""".trimMargin()
    }

    operator fun component1() = amount
    operator fun component2() = bookmarks
}

class AppState(filepath: File = File("")) {
    private var file = filepath

    var lastbook: String = "book.bk"
    var index: Int = 0
    var amount: Int = 8
    var bookmark: Int = 0
    var chapter: Int = 1
    /*var showMenu: Boolean = false*/

    init {
        val s = if (file.exists()) file.readText() else ""
        val l = s.split("\u0000")

        when (l.size) {
            1 -> {
                val (f) = l
                update(f, 0, 8, 0, 1)
            }

            2 -> {
                val (f, i) = l
                update(f, i.toInt(), 8, 0, 1)
            }

            3 -> {
                val (f, i, a) = l
                update(f, i.toInt(), a.toInt(), 0, 1)
            }

            4 -> {
                val (f, i, a, b) = l
                update(f, i.toInt(), a.toInt(), b.toInt(), 1)
            }

            5 -> {
                val (f, i, a, b, c) = l
                update(f, i.toInt(), a.toInt(), b.toInt(), c.toInt())
            }

            6 -> {
                val (f, i, a, b, c, m) = l
                update(f, i.toInt(), a.toInt(), b.toInt(), c.toInt() /*m.toBoolean()*/)
            }

            else -> {
                update("book.bk", 0, 8, 0, 1 /*false*/)
            }
        }
    }

    fun setDatafile(f: File) {
        this.file = f
    }

    fun update(
        lastbook: String,
        index: Int,
        amount: Int,
        bookmark: Int,
        chapter: Int,
        /*showMenu: Boolean,*/
    ) {
        this.lastbook = lastbook
        this.index = index
        this.amount = amount
        this.bookmark = bookmark
        this.chapter = chapter
    }

    fun validate(): Boolean {
        return file.readText().split("\u0000").size == 5
    }

    fun read(): String {
        val s = file.readText()

        if (validate()) {
            val (f, i, a, b, c /*m*/) = s.split("\u0000")
            update(f, i.toInt(), a.toInt(), b.toInt(), c.toInt() /*m.toBoolean()*/)
        }

        return s
    }

    fun write() {
        file.writeText(getData())
    }

    fun getData(): String {
        return "$lastbook\u0000$index\u0000$amount\u0000$bookmark\u0000$chapter"
    }

    override fun toString(): String {
        return """lastbook: ${getFileName(lastbook)}
                 |index: $index
                 |amount: $amount
                 |bookmark: $bookmark
                 |chapter: $chapter""".trimMargin()
    }

    operator fun component1() = lastbook
    operator fun component2() = index
    operator fun component3() = amount
    operator fun component4() = bookmark
    operator fun component5() = chapter
}