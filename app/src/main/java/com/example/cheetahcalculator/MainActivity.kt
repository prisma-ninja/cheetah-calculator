package com.example.cheetahcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.example.cheetahcalculator.ui.theme.CheetahcalculatorTheme
import java.lang.Integer.parseInt
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CheetahcalculatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {

                    val mutableList = mutableListOf<Int>()
                    var result by remember { mutableStateOf(0) }
                    var details by remember { mutableStateOf(mutableList)  }


                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        Row(
                            modifier = Modifier.weight(1f),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            BigDisplayer(result, unit = "pf")
                        }
                        Row(
                            modifier = Modifier.weight(2f),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            var toPrint = "";
                            for (detail in details) {
                                if (toPrint.isEmpty()) {
                                    toPrint += detail
                                }
                                else {
                                    toPrint += " + $detail"
                                }
                            }
                            SmallDisplayer(toPrint)
                        }
                        Row(modifier = Modifier.weight(4f)) {
                            Keyboard(
                                onValueChange = {
                                    result += parseInt(it)
                                    details.add(parseInt(it))
                                                },
                                onReset = { result = 0
                                            details.clear()
                                          },
                                onRemoveLast = {
                                    if (details.isNotEmpty()) {
                                        details.removeLast()
                                        result = details.sum()
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SmallDisplayer(value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = value)
    }
}

@Composable
fun BigDisplayer(acqua: Int = 0, unit: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = acqua.toString(), fontSize = 18.em)
        Spacer(modifier = Modifier.width(20.dp))
        Text(text = unit)
    }
}

@Composable
fun Keyboard(
    onValueChange: (String) -> Unit,
    onReset: (String) -> Unit,
    onRemoveLast: () -> Unit
) {

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(end = 16.dp)
                    .clickable { onRemoveLast() }, horizontalAlignment = Alignment.End
            ) {
                KeyboardKey(value = "Correct")
            }
            Column(modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .clickable { onReset("") },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                KeyboardKey(value = "Clear")
            }
        }
        for (row in 3 downTo 1) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                for (col in 1..3) {
                    Column(modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clickable { onValueChange((row*col + (3 - col)*(row - 1)).toString()) },
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        KeyboardKey(value = (row*col + (3 - col)*(row - 1)).toString())
                    }
                }
            }
        }
    }
}

@Composable
fun KeyboardKey(value: String) {
    Text(value, color = Color.White, fontSize = 10.em)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CheetahcalculatorTheme {
    }
}