package com.example.multilangbook

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource

class Colors {
    companion object {
        @Composable
        fun dunno() = colorResource(R.color.dunno)
        @Composable
        fun black() = colorResource(R.color.black)
        @Composable
        fun gold() = colorResource(R.color.gold)
        @Composable
        fun teal() = colorResource(R.color.teal_200)
        @Composable
        fun green() = colorResource(R.color.green)
        @Composable
        fun red() = colorResource(R.color.red)
    }
}