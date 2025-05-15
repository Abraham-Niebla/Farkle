package uabc.farkle.presentation

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material.icons.filled.SortByAlpha
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import uabc.farkle.utils.*
import uabc.farkle.R
import uabc.farkle.data.ScoreRegister
import uabc.farkle.utils.DatePickerTextField
import uabc.farkle.dialogs.DeleteFileDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScoresView(modifier: Modifier) {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }
    val scoresList: List<ScoreRegister> = readFile(context)

    val buttonColors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.tertiary,
        contentColor = MaterialTheme.colorScheme.onTertiary
    )

    var nameSearch by remember { mutableStateOf("") }
    var searchDate by remember { mutableStateOf("") }

    var isAscendingName by remember { mutableStateOf(true) }
    var isAscendingDate by remember { mutableStateOf(true) }

    val sortedScores by remember(scoresList, isAscendingName, isAscendingDate) {
        derivedStateOf<List<ScoreRegister>> {
            scoresList.sortedWith(
                compareBy<ScoreRegister> { if (isAscendingDate) it.fechaJuego else -it.fechaJuego.hashCode() }
                    .thenBy { if (isAscendingName) it.nombreJugador else it.nombreJugador.reversed() }
            )
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    actionIconContentColor = MaterialTheme.colorScheme.surface
                ),
                title = {
                    Text(
                        text = stringResource(R.string.scores),
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        val activity = context as? Activity
                        activity?.finish()
                    }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.page_return)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        showDialog = true
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Delete, // Puedes cambiar este icono por otro
                            contentDescription = stringResource(R.string.clean)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Filtro de búsqueda por nombre
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    modifier = Modifier,
                    value = nameSearch,
                    onValueChange = { nameSearch = it },
                    maxLines = 1,
                    label = { Text(stringResource(R.string.name_search)) },
                    trailingIcon = {
                        if (nameSearch.isNotBlank()) {
                            IconButton(onClick = {
                                nameSearch = ""
                            }) {
                                Icon(
                                    Icons.Default.Clear,
                                    contentDescription = stringResource(R.string.name_clean)
                                )
                            }
                        }
                    }
                )

                Spacer(modifier = Modifier.width(10.dp))

                // Botón para ordenar por nombre
                Button(
                    modifier = Modifier,
                    colors = buttonColors,
                    onClick = {
                        isAscendingName = !isAscendingName
                    }
                ) {
                    Icon(
                        imageVector = if (isAscendingName) Icons.Filled.ArrowUpward else Icons.Filled.ArrowDownward,
                        contentDescription = stringResource(R.string.name_sort)
                    )
                }
            }

            Spacer(modifier = Modifier.height(2.dp))

            // Filtro de búsqueda por fecha
            Row(
                modifier = Modifier
                    .padding(horizontal = 5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                DatePickerTextField(
                    label = stringResource(R.string.select_date),
                    initialDate = searchDate,
                    onDateChange = { newDate ->
                        searchDate = newDate
                    }
                )

                Spacer(modifier = Modifier.width(10.dp))

                // Botón para ordenar por fecha
                Button(
                    modifier = Modifier,
                    colors = buttonColors,
                    onClick = {
                        isAscendingDate = !isAscendingDate
                    }
                ) {
                    Icon(
                        imageVector = if (isAscendingDate) Icons.Filled.ArrowUpward else Icons.Filled.ArrowDownward,
                        contentDescription = stringResource(R.string.date_sort)
                    )
                }
            }

            // Lista de puntajes
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 5.dp)
                    .padding(top = 5.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                items(sortedScores) { score ->
                    if (((nameSearch == "") || (score.nombreJugador == nameSearch)) &&
                        ((searchDate == "") || (score.fechaJuego == searchDate))
                    ) {
                        ScoreElement(score)
                    }
                }

            }
        }
    }

    if (showDialog) {
        DeleteFileDialog(
            onDismiss = { showDialog = false },
            onDelete = {
                clearFile(context)
                showDialog = false
            }
        )
    }
}
