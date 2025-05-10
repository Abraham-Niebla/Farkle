package uabc.farkle.presentation

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import uabc.farkle.intents.*
import uabc.farkle.R
import uabc.farkle.dialogs.PlayersNamesDialog
import uabc.farkle.utils.DEFAULT_MAX_SCORE
import uabc.farkle.utils.DEFAULT_THROWS

@Composable
fun MainView(modifier: Modifier, context: Context) {
    var puntaje by remember { mutableStateOf(DEFAULT_MAX_SCORE) }
    var tiros by remember { mutableStateOf(DEFAULT_THROWS) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val puntajeRecibido = result.data?.getIntExtra("puntaje", 0)
            val tirosRecibido = result.data?.getIntExtra("tiros", 0)

            puntaje = puntajeRecibido ?: 0
            tiros = tirosRecibido ?: 0
        }
    }

    val buttonStyle = MaterialTheme.typography.titleLarge
    val buttonColors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.tertiary,       // Color de fondo del botón
        contentColor = MaterialTheme.colorScheme.onTertiary        // Color del texto/icono dentro del botón
    )
    val buttonSpacer = Modifier.height(20.dp)

    var showDialog by remember { mutableStateOf(false) }
    var player1 by remember { mutableStateOf("") }
    var player2 by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.app_name),
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.displayLarge
        )

        Spacer(modifier = Modifier.height(50.dp))

        Button(
            modifier = Modifier
                .fillMaxWidth(),
            colors = buttonColors,
            onClick = {
                showDialog = true
            }) {
            Text(
                text = stringResource(R.string.play),
                style = buttonStyle
            )
        }

        Spacer(modifier = buttonSpacer)

        Button(
            modifier = Modifier
                .fillMaxWidth(),
            colors = buttonColors,
            onClick = {
                val intent = Intent(context, ChangeScoreActivity::class.java)
                intent.putExtra("maxScore", puntaje)
                intent.putExtra("maxTiros", tiros)
                launcher.launch(intent)
            }) {
            Text(
                text = stringResource(R.string.config),
                style = buttonStyle
            )
        }

        Spacer(modifier = buttonSpacer)

        Button(
            modifier = Modifier
                .fillMaxWidth(),
            colors = buttonColors,
            onClick = {
                val intent = Intent(context, ScoresActivity::class.java)
                context.startActivity(intent)
            }) {
            Text(
                text = stringResource(R.string.scores),
                style = buttonStyle
            )
        }

        Spacer(modifier = buttonSpacer)

        Button(
            modifier = Modifier
                .fillMaxWidth(),
            colors = buttonColors,
            onClick = {
                val intent = Intent(context, RulesActivity::class.java)
                context.startActivity(intent)
            }) {
            Text(
                text = stringResource(R.string.rules),
                style = buttonStyle
            )
        }
    }
    if (showDialog) {
        PlayersNamesDialog(
            onConfirm = { j1, j2 ->
                player1 = j1
                player2 = j2
                showDialog = false

                val intent = Intent(context, GameActivity::class.java)
                intent.putExtra("maxScore", puntaje)
                intent.putExtra("maxTiros", tiros)
                intent.putExtra("player1", player1)
                intent.putExtra("player2", player2)
                context.startActivity(intent)
            },
            onDismiss = {
                showDialog = false
            }
        )
    }
}