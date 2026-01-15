package com.example.multilangbook

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

class Icon {
    companion object {
        fun size(i: Int) = Modifier.size(i.dp)

        @Composable
        fun icon(id: Int, desc: String, size: Int) = Image(painterResource(id), desc, size(size))

        @Composable
        fun icon(id: Int, desc: String, modifier: Modifier) = Image(painterResource(id), desc, modifier)

        @Composable
        fun icon(id: Int, desc: String, modifier: Modifier, colorFilter: ColorFilter) = Image(
            painterResource(id), desc, modifier, colorFilter = colorFilter
        )

        @Composable
        fun menu() = icon(R.drawable.menu, "menu", 10)

        @Composable
        fun settings() = icon(R.drawable.settings, "settings", 10)

        @Composable
        fun close() = icon(R.drawable.exit, "exit", 16)

        @Composable
        fun ruby() = icon(R.drawable.ruby, "ruby", 32)

        @Composable
        fun dictionary() = icon(R.drawable.dictionary, "dictionary", 32)

        @Composable
        fun bookmark(color: Color = Color.Red, size: Int = 24) = icon(
            R.drawable.bookmark, "bookmark", size(size), ColorFilter.tint(color)
        )
    }
}