package com.example.multilangbook

//@Composable
//fun menu() {
//    menubox {
//        Row {
//            Button({ showdict = !showdict }, Modifier.padding(bottom = 8.dp)) { dictionaryIcon() }
//            Button({ showdic = !showdic }, Modifier.padding(bottom = 8.dp)) { dictionaryIcon() }
//        }
//
//        bookfiles?.let { files ->
//            if (files.isNotEmpty()) files
//                .map { file -> getFileName(file.toString()) }
//                .let { filenames ->
//                    dropdown(filenames, Modifier.padding(bottom = 16.dp)) { i ->
//                        loadBook(bookfiles[i].readText())
//                    }
//                }
//        }
//
//        Row {
//            Button({ launcher.launch(arrayOf("*/*")) }, Modifier.padding(top = 4.dp)) {
//                Text("Open")
//            }
//
//            intInput(Modifier.padding(start = 8.dp)) {
//                setIndex(it - 1)
//            }
//        }
//
//        intSetting(
//            "Chapter: ${chapter}/${book.chapters.size}",
//            { increaseChapter() }, { decreaseChapter() },
//            Modifier.padding(top = 8.dp)
//        )
//    }
//}
