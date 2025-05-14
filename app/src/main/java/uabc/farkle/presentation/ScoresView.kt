package uabc.farkle.presentation

import android.app.Activity
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
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import uabc.farkle.utils.*
import uabc.farkle.R
import uabc.farkle.data.ScoreRegister
import uabc.farkle.dialogs.DatePickerTextField
import uabc.farkle.dialogs.DeleteFileDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScoresView(modifier: Modifier) {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }
    val scoresList: List<ScoreRegister> = readFile(context)
    var nameSearch by remember { mutableStateOf("") }
    var searchDate by remember { mutableStateOf("") }

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
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    modifier = Modifier,
                    value = nameSearch,
                    onValueChange = { nameSearch = it },
                    maxLines = 1,
                    label = { Text(stringResource(R.string.search)) },
                    trailingIcon = {
                        if (nameSearch.isNotBlank()) {
                            IconButton(onClick = {
                                nameSearch = ""
                            }) {
                                Icon(Icons.Default.Clear, contentDescription = "Borrar nombre")
                            }
                        }
                    }
                )

                Spacer(modifier = Modifier.width(10.dp))

                Button(
                    modifier = Modifier,
                    onClick = {}) {
                    Text(
                        text = "AZ"
                    )
                }
            }

            Spacer(modifier = Modifier.height(2.dp))

            Row(
                modifier = Modifier
                    .padding(horizontal = 5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                DatePickerTextField(
                    label = "Selecciona una fecha",
                    initialDate = searchDate,
                    onDateChange = { newDate ->
                        searchDate = newDate
                    }
                )
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 5.dp)
                    .padding(top = 5.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                items(scoresList) { score ->
                    if (((nameSearch == "") || (score.nombreJugador == nameSearch))
                        &&
                        ((searchDate == "") || (score.fechaJuego == searchDate))) {
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