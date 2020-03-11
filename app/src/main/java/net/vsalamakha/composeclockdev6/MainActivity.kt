package net.vsalamakha.composeclockdev6

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.ui.core.Alignment
import androidx.ui.core.Text
import androidx.ui.core.setContent
import androidx.ui.foundation.Icon
import androidx.ui.graphics.Color
import androidx.ui.layout.*
import androidx.ui.material.IconButton
import androidx.ui.material.MaterialTheme
import androidx.ui.material.TopAppBar
import androidx.ui.res.vectorResource
import androidx.ui.text.TextStyle
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.*

/**
 * @author Adib Faramarzi (adibfara@gmail.com)
 * @author Val Salamakha (salamakha.lab@gmail.com)
 */

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Column {
                    TopAppBar(
                        title = { Text("Clock") },
                        actions = {
                            IconButton(
                                onClick = {
                                    Log.d(TAG, "IconButton was clicked!")
                                },
                                children = {
                                    Icon(
                                        icon = vectorResource(id = R.drawable.ic_baseline_add),
                                        tint = Color.White
                                    )
                                }
                            )
                        })
                    Stack {
                        ComposeClock()

                        Align(alignment = Alignment.BottomStart) {
                            Column {
                                Text(
                                    modifier = LayoutPadding(Dp(16f)),
                                    text = "Compose Clock",
                                    style = TextStyle(Color.White)
                                )
                                Text(
                                    modifier = LayoutPadding(Dp(16f)),
                                    text = "github.com/adibfara/ComposeClock,\ngithab.com/salamakha.lab/ComposeClock",
                                    style = TextStyle(Color.White, TextUnit.Companion.Sp(12f))
                                )

                            }
                        }
                    }
                }
            }
        }
    }
    companion object{
        private val TAG: String = MainActivity::javaClass.name
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview
@Composable
fun DefaultPreview() {
    MaterialTheme {
        Greeting("Android")
    }
}