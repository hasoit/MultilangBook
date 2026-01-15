package com.example.multilangbook

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URI
import java.net.URLEncoder

@Composable
fun nwtest() {
    val context = LocalContext.current

    val s = "hello world"
    Column(Modifier.padding(top = 64.dp, start = 16.dp)) {
        Text(s)
//        Text(translate(s, "en", "ru"))
        CoroutineScope(Dispatchers.Main).launch {
            val result = translate("Hello", "en", "ru")
            Toast.makeText(context, "Translated: $result", Toast.LENGTH_LONG).show()
        }
    }
}

suspend fun translate(text: String = "Hello, world!", sl: String = "en", tl: String = "fi"): String {
    return withContext(Dispatchers.IO) {
        try {
            val baseUrl = "https://translate.googleapis.com/translate_a/single"
            val dtParams = listOf("at", "bd", "ex", "ld", "md", "qca", "rw", "rm", "ss", "t")

            val params = mapOf(
                "client" to "gtx",
                "sl" to sl,
                "tl" to tl,
                "hl" to tl,
                "ie" to "UTF-8",
                "oe" to "UTF-8",
                "otf" to "1",
                "ssel" to "0",
                "tsel" to "0",
                "tk" to "xxxx",
                "q" to text
            )

            val dtEncoded = dtParams
                .joinToString("&") { "dt=${URLEncoder.encode(it, "UTF-8")}" }

            val queryString = params.entries
                .joinToString("&") {
                    "${it.key}=${URLEncoder.encode(it.value, "UTF-8")}"
                } + "&$dtEncoded"

            val url = URI("$baseUrl?$queryString").toURL()
            val connection = url.openConnection() as HttpURLConnection
            connection.connectTimeout = 10000
            connection.readTimeout = 10000
            connection.requestMethod = "GET"

            val stream = if (connection.responseCode in 200..299) {
                connection.inputStream
            } else {
                connection.errorStream // Handles API errors properly
            }

            val response = stream?.bufferedReader()?.use { it.readText() } ?: "Error fetching data"

//            val response = connection.inputStream.bufferedReader().use { it.readText() }
            Log.e("networking", response)
            connection.disconnect()

            response // Return the API response
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("networking", e.toString())
            "fail"
        }
    }
}