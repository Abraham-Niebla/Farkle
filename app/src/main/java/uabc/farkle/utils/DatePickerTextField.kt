package uabc.farkle.utils

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import java.text.SimpleDateFormat
import java.util.Locale
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.filled.Clear
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerTextField(
    label: String = "Fecha",
    initialDate: String = "",
    onDateChange: (String) -> Unit
) {
    val formatter = remember { SimpleDateFormat("dd-MMMM-yyyy", Locale.getDefault()) }
    var dateText by remember { mutableStateOf(initialDate) }
    var showDatePicker by remember { mutableStateOf(false) }

    // Para Compose DatePicker
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = runCatching { formatter.parse(dateText)?.time }.getOrNull()
    )

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    val millis = datePickerState.selectedDateMillis
                    if (millis != null) {
                        val calendar = Calendar.getInstance().apply {
                            timeInMillis = millis
                            add(Calendar.DAY_OF_MONTH, 1) // suma 1 día
                        }
                        val newDate = formatter.format(calendar.time)
                        dateText = newDate
                        onDateChange(newDate)
                    }
                    showDatePicker = false
                }) {
                    Text("Aceptar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancelar")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    OutlinedTextField(
        value = dateText,
        onValueChange = {
            dateText = it
            runCatching {
                formatter.parse(it)
                onDateChange(it)
            }
        },
        label = { Text(label) },
        trailingIcon = {
            Row {
                if (dateText.isNotBlank()) {
                    IconButton(onClick = {
                        dateText = ""
                        onDateChange("")
                    }) {
                        Icon(Icons.Default.Clear, contentDescription = "Borrar fecha")
                    }
                }
                IconButton(onClick = { showDatePicker = true }) {
                    Icon(Icons.Default.DateRange, contentDescription = "Seleccionar fecha")
                }
            }
        },
        modifier = Modifier
    )
}
