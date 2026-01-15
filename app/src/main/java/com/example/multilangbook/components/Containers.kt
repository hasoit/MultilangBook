package com.example.multilangbook.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.multilangbook.Colors
import com.example.multilangbook.R

@Composable
fun col(
    modifier: Modifier = Modifier.fillMaxSize().background(Colors.black()),
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier
    ) {
        content()
    }
}

@Composable
fun scroll(content: @Composable ColumnScope.() -> Unit) = Column { content() }

@Composable
fun menubox(
    visible: Boolean = true,
//    onBack: () -> Unit = {},
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    if (visible) Column(
        modifier
//            .bg(3)
            .background(colorResource(R.color.gray))
            .fillMaxWidth().padding(8.dp)
    ) {
//        BackHandler {
//            onBack()
//        }

        content()
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun swipeablePages(content: @Composable (Int) -> Unit) {
    val pagerState = rememberPagerState(pageCount = { 3 })

    HorizontalPager(pagerState) { page ->
        content(page)
    }
}
