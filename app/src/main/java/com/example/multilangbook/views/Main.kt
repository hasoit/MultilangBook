package com.example.multilangbook.views

/*
@Composable
fun mainView() {
    var showmenu by remember { mutableStateOf(true) }
    var showsettings by remember { mutableStateOf(!showmenu) }

    Column(
        Modifier.fillMaxSize()
            .background(Colors.black())
            .padding(start = 16.dp, end = 16.dp, top = 32.dp, bottom = 8.dp)
            .onKeyEvent
            { e ->
                if (e.type == KeyEventType.KeyUp)
                    when (e.key) {
                        Key.DirectionRight -> increaseIndex()
                        Key.DirectionLeft -> decreaseIndex()
                    }
                true
            }
    ) {
        Column {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    {
                        showsettings = false
                        showmenu = !showmenu
                        writeDataFile()
                    },
                    colors = ButtonDefaults.buttonColors(
                        if (showmenu) Colors.gold()
                        else Colors.green()
                    )
                ) { menuIcon() }

                Text(
                    "${index + 1}-${index + amount} / ${list.size}",
                    Modifier.padding(top = 8.dp, bottom = 16.dp),
                    Colors.dunno(),
                    32.sp,
                )

                Button(
                    {
                        showmenu = false
                        showsettings = !showsettings
                        writeDataFile()
                    },
                    colors = ButtonDefaults.buttonColors(
                        if (showsettings) Colors.gold()
                        else Colors.green()
                    )
                ) { settingsIcon() }
            }

            if (showsettings) settings()
            if (showmenu) menu()
        }

        if (debug) Text(debugString, Modifier.padding(16.dp), color = Colors.dunno())

        Column(scrollableColumnModifier) {
            LaunchedEffect(shouldScroll) {
                if (shouldScroll) {
                    scrollState.scrollTo(0)
                    shouldScroll = false
                }
            }

            if (list.size > amount) {
                (0..<amount)
            } else {
                list.indices
            }.forEach { i ->
                val bookmarked = index + i == bookmark && bookmarkedChapter == chapter

                Row {
                    if (bookmarked) bmi()

                    Text(
                        "${index + i + 1}",
                        fontSize = 32.sp,
                        color = if (bookmarked) Colors.gold()
                        else Colors.teal(),
                        modifier = Modifier.clickable {
                            bookmark = index + i
                            bookmarkedChapter = chapter
                            writeDataFile()
                        }
                    )

                    if (bookmarked) bmi()
                }

                ruby(list[index + i], showTranslations, showRuby, showTransliterations, compare, index)
            }

            dualButton(
                ButtonOptions(
                    { decreaseIndex() },
                    Colors.dunno(),
                    Modifier.width(144.dp).align(Alignment.Start)
                ) {
                    Text("previous", fontSize = 16.sp)
                },
                ButtonOptions(
                    { increaseIndex() },
                    Colors.dunno(),
                    Modifier.width(144.dp).align(Alignment.End)
                ) {
                    Text("next", fontSize = 16.sp)
                },
                Modifier
                    .padding(top = 16.dp)
                    .fillMaxSize()
            )
        }
    }
}
*/
