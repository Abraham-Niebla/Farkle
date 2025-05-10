package uabc.farkle.presentation

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import uabc.farkle.R
import uabc.farkle.utils.DEFAULT_MAX_SCORE
import uabc.farkle.utils.DEFAULT_THROWS

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangeScoreView(modifier: Modifier, maxScore: Int, maxTiros: Int) {
    val context = LocalContext.current
    var puntaje by remember { mutableStateOf("${maxScore}") }
    var tiros by remember { mutableStateOf("${maxTiros}") }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer,
                    titleContentColor = MaterialTheme.colorScheme.onTertiaryContainer,
                    navigationIconContentColor = MaterialTheme.colorScheme.onTertiaryContainer
                ),
                title = {
                    Text(
                        text = stringResource(R.string.max_score),
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
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            TextField(
                value = puntaje,
                onValueChange = { newValue ->
                    if (newValue.all { it.isDigit() }) {
                        puntaje = newValue
                    }
                },
                label = { Text(stringResource(R.string.max_score)) },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            TextField(
                value = tiros,
                onValueChange = { newValue ->
                    if (newValue.all { it.isDigit() }) {
                        tiros = newValue
                    }
                },
                label = { Text(stringResource(R.string.max_throw)) },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.tertiary,
                    contentColor = MaterialTheme.colorScheme.onTertiary
                ),
                onClick = {
                    var puntajeInt = puntaje.toIntOrNull() ?: 0
                    puntajeInt = if (puntajeInt <= 0) DEFAULT_MAX_SCORE else puntajeInt

                    var tirosInt = tiros.toIntOrNull() ?: 0
                    tirosInt = if (tirosInt <= 0) DEFAULT_THROWS else tirosInt
                    tirosInt = if (tirosInt > DEFAULT_THROWS) DEFAULT_THROWS else tirosInt


                    val activity = context as? Activity
                    val intent = Intent().apply {
                        putExtra("puntaje", puntajeInt)
                        putExtra("tiros", tirosInt)
                    }
                    activity?.setResult(Activity.RESULT_OK, intent)
                    activity?.finish()
                }) {
                Text(
                    text = stringResource(R.string.set),
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}