package uabc.farkle.utils

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import uabc.farkle.R

@Composable
fun PlayersNamesDialog(
    onConfirm: (String, String) -> Unit,
    onDismiss: () -> Unit
) {
    var jugador1 by remember { mutableStateOf("") }
    var jugador2 by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        icon = {
            Icon(
                imageVector = Icons.Filled.SportsEsports,
                contentDescription = stringResource(R.string.play_control)
            )
        },
        title = {
            Text(stringResource(R.string.enter_names))
        },
        text = {
            Column {
                TextField(
                    value = jugador1,
                    onValueChange = { jugador1 = it },
                    label = { Text(stringResource(R.string.player_1)) }
                )
                Spacer(modifier = Modifier.padding(vertical = 5.dp))
                TextField(
                    value = jugador2,
                    onValueChange = { jugador2 = it },
                    label = { Text(stringResource(R.string.player_2)) }
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm(jugador1, jugador2)
                }
            ) {
                Text(stringResource(R.string.play))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel))
            }
        }
    )
}