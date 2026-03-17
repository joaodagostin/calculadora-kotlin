package com.example.calculadora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculadora.ui.theme.CalculadoraTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculadoraTheme {
                CalculatorApp()
            }
        }
    }
}

@Composable
fun CalculatorApp() {
    var display by remember { mutableStateOf("0") }
    var oldNumber by remember { mutableStateOf("") }
    var operator by remember { mutableStateOf("") }

    fun onNumberClick(number: String) {
        display = if (display == "0") number else display + number
    }

    fun onOperatorClick(op: String) {
        oldNumber = display
        operator = op
        display = "0"
    }

    fun onEqualClick() {
        val result = when (operator) {
            "+" -> oldNumber.toDouble() + display.toDouble()
            "-" -> oldNumber.toDouble() - display.toDouble()
            "*" -> oldNumber.toDouble() * display.toDouble()
            "/" -> oldNumber.toDouble() / display.toDouble()
            else -> 0.0
        }
        display = result.toString()
    }

    fun onClear() {
        display = "0"
        oldNumber = ""
        operator = ""
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(androidx.compose.ui.graphics.Color.Black)
        .padding(12.dp),
        verticalArrangement = Arrangement.Bottom

    ) {
        Text(
            text = "Calculadora Top",
            fontSize = 40.sp,
            color = androidx.compose.ui.graphics.Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 25.dp),
            textAlign = androidx.compose.ui.text.style.TextAlign.Start

        )
        Spacer(modifier = Modifier.weight(1f))

        // DISPLAY
        Text(
            text = display,
            fontSize = 60.sp,
            color = androidx.compose.ui.graphics.Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            textAlign = androidx.compose.ui.text.style.TextAlign.End
        )

        Spacer(modifier = Modifier.height(16.dp))

        val buttons = listOf(
            listOf("C", "", "", "/"),
            listOf("7", "8", "9", "*"),
            listOf("4", "5", "6", "-"),
            listOf("1", "2", "3", "+"),
            listOf("0", "=")
        )

        Column {
            buttons.forEach { row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    row.forEach { label ->

                        if (label == "") {
                            Spacer(modifier = Modifier.size(80.dp))
                        } else {

                            val isOperator = label in listOf("+", "-", "*", "/", "=")
                            val isZero = label == "0"

                            Button(
                                onClick = {
                                    when (label) {
                                        in "0".."9" -> onNumberClick(label)
                                        "+", "-", "*", "/" -> onOperatorClick(label)
                                        "=" -> onEqualClick()
                                        "C" -> onClear()
                                    }
                                },
                                shape = androidx.compose.foundation.shape.CircleShape,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = when {
                                        isOperator -> androidx.compose.ui.graphics.Color(0xFFFF9500)
                                        label == "C" -> androidx.compose.ui.graphics.Color.Gray
                                        else -> androidx.compose.ui.graphics.Color.DarkGray
                                    }
                                ),
                                modifier = Modifier
                                    .padding(6.dp)
                                    .height(80.dp)
                                    .then(
                                        if (isZero) Modifier.width(170.dp)
                                        else Modifier.size(80.dp)
                                    )
                            ) {
                                Text(
                                    text = label,
                                    fontSize = 24.sp,
                                    color = androidx.compose.ui.graphics.Color.White
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}