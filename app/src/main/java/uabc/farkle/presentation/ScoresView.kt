package uabc.farkle.presentation

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import uabc.farkle.dialogs.DeleteFileDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScoresView(modifier: Modifier){
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }
    val scoresList: List<ScoreRegister> = readFile(context)

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
                            contentDescription = "Regresar"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        showDialog = true
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Delete, // Puedes cambiar este icono por otro
                            contentDescription = "Más opciones"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 5.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            items(scoresList) { score ->
                ScoreElement(score)
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