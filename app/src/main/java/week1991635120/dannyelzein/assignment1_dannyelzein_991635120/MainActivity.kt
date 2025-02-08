package week1991635120.dannyelzein.assignment1_dannyelzein_991635120

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.tooling.preview.Preview
import week1991635120.dannyelzein.assignment1_dannyelzein_991635120.ui.theme.Assignment1_DannyElzein_991635120Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Assignment1_DannyElzein_991635120Theme {
                TipCalculator()
            }
        }
    }
}

@Composable
fun TipCalculator() {
    var billAmount by remember { mutableStateOf("") }
    var tipPercentage by remember { mutableStateOf("15") }
    var customTipPercentage by remember { mutableStateOf("") }
    var peopleCount by remember { mutableStateOf("1") }
    var totalAmount by remember { mutableStateOf("0.00") }
    var perPersonAmount by remember { mutableStateOf("0.00") }
    val context = LocalContext.current
    val tipOptions = listOf("10", "15", "20", "other")
    val peopleOptions = (1..10).map { it.toString() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Tip Calculator", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = billAmount,
            onValueChange = { billAmount = it },
            label = { Text("Bill Amount") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Tip Selection
        DropdownMenuWithSelection(
            label = "Tip %",
            selectedOption = tipPercentage,
            options = tipOptions,
            onOptionSelected = {
                tipPercentage = it
                if(it != "other") {
                    customTipPercentage = ""
                }
            }
        )

        if(tipPercentage == "other") {
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = customTipPercentage,
                onValueChange = { customTipPercentage = it},
                label = { Text("Custom Tip %")},
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
        }


        Spacer(modifier = Modifier.height(16.dp))

        DropdownMenuWithSelection(
            label = "# People",
            selectedOption = peopleCount,
            options = peopleOptions,
            onOptionSelected = { peopleCount = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = {
                if (billAmount.isEmpty()) {
                    Toast.makeText(context, "Enter bill amount!", Toast.LENGTH_SHORT).show()
                } else {
                    val bill = billAmount.toDouble()
                    // val tip = tipPercentage.toDouble()
                    val tipValue = if (tipPercentage == "other") {
                        customTipPercentage.toDoubleOrNull() ?: 0.0
                    } else {
                        tipPercentage.toDoubleOrNull() ?: 0.0
                    }
                    val total = bill + (bill * tipValue / 100)
                    val perPerson = total / peopleCount.toInt()
                    totalAmount = "%.2f".format(total)
                    perPersonAmount = "%.2f".format(perPerson)
                }
            }) {
                Text("Calculate")
            }

            Button(onClick = {
                billAmount = ""
                tipPercentage = "15"
                peopleCount = "1"
                totalAmount = "0.00"
                perPersonAmount = "0.00"
            }) {
                Text("Clear")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Total: $$totalAmount", style = MaterialTheme.typography.bodyLarge)
        if (peopleCount != "1") {
            Text("Per Person: $$perPersonAmount", style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Composable
fun DropdownMenuWithSelection(label: String, selectedOption: String, options: List<String>, onOptionSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        OutlinedButton(onClick = { expanded = true }) {
            Text("$label: $selectedOption")
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text("$option") },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TipCalculatorPreview() {
    Assignment1_DannyElzein_991635120Theme {
        TipCalculator()

    }
}
