package com.example.multilangbook

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.*
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.multilangbook.components.*
import com.example.multilangbook.util.getFileName
import com.example.multilangbook.util.takeFrom
import com.google.mlkit.nl.translate.TranslateLanguage
import java.io.File
import kotlin.math.abs
import kotlin.math.min

var translator = Translator(TranslateLanguage.RUSSIAN, TranslateLanguage.UKRAINIAN)

class MainActivity : ComponentActivity() {
    private val fileName = "data.txt"
    private val debug = false
    private var state = AppState()
    private var stat = State()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
//            fixDataFile()
            app()
//            testbook()
        }
    }

    @Composable
    fun testbook() {
        val context = LocalContext.current

        val b = Book(context.resources.openRawResource(R.raw.crime_and_punishment).bufferedReader().readText())
        val x = b.chapters.first().sections.first()
//        val s = "${x.name.quote()}\n${x.content.quote()}"
        val s = "${x.list.first()}"
        Text(s)
    }

    @Composable
    fun testDataFile() {
        val context = LocalContext.current
        val datafile = File(context.filesDir, fileName)

        stat.file = datafile
        stat.context = context

        stat.read(2)
        stat.write(2)

        Column(Modifier.padding(top = 32.dp, start = 8.dp)) {
            Text(
                datafile.readText()
                    .replace("\u0000", ":")
                    .replace("\u0001", ",")
                    .replace("\u0002", ".")
            )
            Text(stat.toString())
        }
    }

    @Composable
    fun fixDataFile() {
        val context = LocalContext.current
        val datafile = File(context.filesDir, fileName)

//        val found = datafile.exists()
//        datafile.writeText("")
//        Text("$found")

        state.setDatafile(datafile)
        state.read()
        state.chapter = 0
        state.write()

        Text(state.toString(), Modifier.padding(64.dp))
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun app() {
        val context = LocalContext.current

        val scrollState = rememberScrollState()
        var shouldScroll by remember { mutableStateOf(false) }

        val librarydir = File(context.filesDir, "library/")
        if (!librarydir.exists()) librarydir.mkdirs()

        val bookfiles = librarydir.listFiles()
        val datafile = File(context.filesDir, fileName)

        if (!datafile.exists()) datafile.writeText("")

        state.setDatafile(datafile)
        state.read()

        var showTranslations by remember { mutableStateOf(true) }
        var showRuby by remember { mutableStateOf(true) }
        var showTransliterations by remember { mutableStateOf(false) }
        var showflags by remember { mutableStateOf(false) }
        var compare by remember { mutableStateOf(false) }

        var keyhandling by remember { mutableStateOf(false) }
        var showTopButtons by remember { mutableStateOf(false) }
        var pageMode by remember { mutableStateOf(false) }

        val rawBook = context.resources.openRawResource(R.raw.crime_and_punishment)
        val rawBookData = rawBook.bufferedReader().readText()

        var book by remember { mutableStateOf(Book(rawBookData)) }

        var bookmarkedChapter by remember { mutableStateOf(state.chapter) }
        var bookmarkedSection by remember { mutableStateOf(state.section) }
        val lastbook by remember { mutableStateOf(state.lastbook) }
        var index by remember {
            mutableStateOf(
                if (state.bookmark <= book[bookmarkedChapter][bookmarkedSection].list.size) state.bookmark else 0
            )
        }
        var amount by remember { mutableStateOf(state.amount) }
        var bookmark by remember { mutableStateOf(state.bookmark) }
        var chapter by remember { mutableStateOf(state.chapter) }
        var section by remember { mutableStateOf(state.section) }

        var textsize by remember { mutableStateOf(12) }

        var page by remember { mutableStateOf(index / amount) }
        var list by remember { mutableStateOf(book[chapter][section].list) }
        val pagerState = rememberPagerState(page, pageCount = { list.size / amount })
        var debugString by remember { mutableStateOf("") }

        fun <T> dbp(s: T) {
            if (debug) debugString = s.toString()
        }

        fun <T> dbpa(s: T) {
            if (debug) debugString += s.toString()
        }

        @Composable
        fun switchOptionButtons() = switchButtons(
            SwitchButton("translation", showTranslations) { showTranslations = !showTranslations },
            SwitchButton("ruby", showRuby) { showRuby = !showRuby },
            SwitchButton("Transliterations", showTransliterations) { showTransliterations = !showTransliterations },
            SwitchButton("Compare", compare) { compare = !compare },
            SwitchButton("Key handling", keyhandling) { keyhandling = !keyhandling },
            SwitchButton("Show flags", showflags) { showflags = !showflags },
            SwitchButton("Top buttons", showTopButtons) { showTopButtons = !showTopButtons },
            SwitchButton("Page mode", pageMode) { pageMode = !pageMode },
        )

        fun writeDataFile() {
            state.update(lastbook, index, amount, bookmark, bookmarkedChapter, section)
            state.write()
        }

        fun loadBook(content: String) {
            book = Book(content)

            index = 0
            chapter = 0
            bookmarkedChapter = 0
            bookmark = 0
            list = book[chapter][section].list
        }

        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.OpenDocument(), onResult = { uri ->
                uri?.let {
                    writeDataFile()

                    val content = context.contentResolver.openInputStream(it)
                        ?.bufferedReader()?.use { reader ->
                            reader.readText()
                        } ?: "Failed to read file"

                    dbpa("\n$uri\n\n${getFileName(uri.toString())}")

                    File(
                        context.filesDir, "library/${
                            getFileName(uri.toString()).replace("%20", " ")
                        }"
                    ).writeText(content)

                    loadBook(content)
                }
            }
        )

        fun increaseIndex() {
            if (index + amount < list.size - 1) {
                index += amount
                page++
            } else {
                index = list.size - amount - 1
            }
            shouldScroll = true
        }

        fun decreaseIndex() {
            if (index - amount >= 0) {
                index -= amount
                page--
            } else {
                index = 0
                page = 0
            }
            shouldScroll = true
        }

        fun setIndex(i: Int) {
            index = if (i >= 0 && i + amount - 1 < list.size) i
            else if (i + amount > list.size) list.size - amount else 0
            page = index / amount
            shouldScroll = true
        }

        fun increaseAmount() {
            if (index + amount > list.size - 1) index--
            amount++
            if (amount >= list.size) amount = list.size - 1
            writeDataFile()
        }

        fun decreaseAmount() {
            if (amount > 1) amount--
            writeDataFile()
        }

        fun increaseChapter() {
            if (chapter + 1 < book.chapters.size) {
                chapter++
                index = 0
                section = 0
                list = book[chapter][section].list
                if (amount > list.size) amount = list.size
                shouldScroll = true
            }
            writeDataFile()
        }

        fun decreaseChapter() {
            if (chapter > 0) {
                chapter--
                section = book[chapter].sections.lastIndex
                list = book[chapter][section].list
                index = list.size - amount
                if (amount > list.size) amount = list.size
                shouldScroll = true
            }
            writeDataFile()
        }

        fun increaseSection() {
            if (section + 1 < book[chapter].sections.size) {
                section++
                index = 0
                list = book[chapter][section].list
                if (amount > list.size) amount = list.size
                shouldScroll = true
            }
            writeDataFile()
        }

        fun decreaseSection() {
            if (section > 0) {
                section--
                index = 0
                list = book[chapter][section].list
                if (amount > list.size) amount = list.size
                shouldScroll = true
            }
            writeDataFile()
        }

        @Composable
        fun menu() {
            bookfiles?.let { files ->
                if (files.isNotEmpty()) dropdown(
                    files.map { file -> getFileName(file.toString()) },
                    Modifier.padding(bottom = 16.dp)
                ) { loadBook(bookfiles[it].readText()) }
            }

            Row {
                Button({ launcher.launch(arrayOf("*/*")) }, Modifier.padding(top = 4.dp)) { Text("Open") }
                intInput(Modifier.padding(start = 8.dp)) { setIndex(it - 1) }
            }

            intSetting(
                "Chapter: ${chapter + 1}/${book.chapters.size}", { increaseChapter() }, { decreaseChapter() }, padt(8)
            )

            intSetting(
                "Section: ${section + 1}/${book[chapter].sections.size}",
                { increaseSection() },
                { decreaseSection() },
                padt(8)
            )
        }

        @Composable
        fun settings() {
            if (compare) Row {
                Text(
                    "Comparison language: ",
                    Modifier.padding(top = 12.dp, end = 8.dp),
                    Color.Magenta,
                    fontSize = 28.sp
                )

                dropdown(Translator.languages, so = translator.targetLanguage) {
                    compare = false
                    translator = Translator(TranslateLanguage.RUSSIAN, Translator.languages[it])
                    compare = true
                }
            }

            intSetting("text size: $textsize", { textsize++ }, { textsize-- })
            intSetting("Amount: $amount", { increaseAmount() }, { decreaseAmount() })

            switchOptionButtons()
        }

        @Composable
        fun sentenceNumber(i: Int) {
            val bookmarked = index + i == bookmark && bookmarkedChapter == chapter && bookmarkedSection == section

            Row {
                if (bookmarked) Icon.bookmark()

                Text(
                    "${index + i + 1}",
                    clickable {
                        bookmark = index + i
                        bookmarkedChapter = chapter
                        bookmarkedSection = section
                        writeDataFile()
                    },
                    color = if (bookmarked) Colors.gold() else Colors.teal(), fontSize = 24.sp
                )

                if (bookmarked) Icon.bookmark()
            }
        }

        @Composable
        fun navibuttons() = dualButton(
            (index == 0).let {
                val x = section == 0
                ButtonOptions(
                    { if (it && x) decreaseChapter() else if (it) decreaseSection() else decreaseIndex() },
                    if (it) Colors.gold() else Colors.dunno(), w(180)
                ) {
                    val what = if (section == 0) "chapter" else "section"
                    Text(if (it) "previous $what" else "previous", fontSize = 16.sp)
                }
            },
            (index + amount >= list.lastIndex).let {
                val x = section == book[chapter].sections.lastIndex
                ButtonOptions(
                    { if (it && x) increaseChapter() else if (it) increaseSection() else increaseIndex() },
                    if (it) Colors.gold() else Colors.dunno(), w(180)
                ) {
                    val what = if (x) "chapter" else "section"
                    Text(if (it) "next $what" else "next", fontSize = 16.sp)
                }
            },
            maxwidth()
        )

        @Composable
        fun pageNumber() = Text(
            if (pageMode) "Page: ${page + 1}/${book[chapter][section](amount).lastIndex}"
            else "${index + 1}-${min(list.lastIndex, index + amount)} / ${list.lastIndex}",
            padt(4), Colors.dunno(), fontSize = 42.sp,
        )

        @Composable
        fun mainView() {
            var showmenu by remember { mutableStateOf(false) }
            var showsettings by remember { mutableStateOf(false) }
            var swipeHandled = true

            BackHandler {
                showmenu = false
                showsettings = false
            }

            Column(
                maxsize().bg(Colors.black()).bottomsidepad(8).let { modifier ->
                    if (keyhandling) modifier.onKeyEvent { e ->
                        when (e.key) {
                            Key.DirectionRight -> increaseIndex()
                            Key.DirectionLeft -> decreaseIndex()
                            Key.S -> showsettings = !showsettings

                            Key.A -> {
                                Log.d("key", "pressed ${e.key}")
                                showsettings = false
                                showmenu = true
                            }

                            Key.Escape -> {
                                showmenu = false
                                showsettings = false
                            }
                        }

                        true
                    } else modifier
                }
            ) {
                Column {
                    Row(maxwidth(), Arrangement.SpaceBetween) {
                        menubutton(showmenu, { Icon.menu() }) {
                            showsettings = false
                            showmenu = !showmenu
                            writeDataFile()
                        }

                        if (!pageMode) pageNumber()

                        menubutton(showsettings, { Icon.settings() }) {
                            showmenu = false
                            showsettings = !showsettings
                            writeDataFile()
                        }
                    }

                    menubox(showsettings) { settings() }
                    menubox(showmenu) { menu() }
                }

                if (debug) Text(debugString, pad(16), Colors.dunno())

                Column(maxwidth().verticalScroll(scrollState).weight(1f).padtb(16)) {
                    LaunchedEffect(shouldScroll) {
                        if (shouldScroll) {
                            scrollState.scrollTo(0)
                            shouldScroll = false
                        }
                    }

                    @Composable
                    fun List<Ruby>.render() = col {
                        if (pageMode) pageNumber()

                        forEachIndexed { i, ruby ->
                            sentenceNumber(i)
                            ruby.render(
                                index + i,
                                Ruby.Options(
                                    showTranslations,
                                    showRuby,
                                    showTransliterations,
                                    showflags,
                                    compare,
                                    translator.targetLanguage,
                                    textsize,
                                ),
                            )
                        }
                    }

                    if (pageMode) HorizontalPager(pagerState, maxsize()) {
                        shouldScroll = true
                        page = it
                        index = page * amount
                        book[chapter][section](amount)[it].render()
                    } else {
                        if (showTopButtons) navibuttons()
                        list.takeFrom(index, amount).render()
                        if (showTopButtons) navibuttons()
                    }
                }

                if (!showTopButtons && !pageMode) navibuttons()
            }
        }

        mainView()
    }
}