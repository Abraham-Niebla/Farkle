package uabc.farkle.dialogs

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import uabc.farkle.R

@Composable
fun WinnerDialog(
    onDismiss: () -> Unit,
    winner: String
) {
    AlertDialog(
        icon = {
            Icon(
                imageVector = Icons.Filled.EmojiEvents,
                contentDescription = stringResource(R.string.winner)
            )
        },
        title = {
            Text(stringResource(R.string.end_game, winner))
        },
        text = {
            Text(stringResource(R.string.end_dialog, winner))
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.accept))
            }
        },
        onDismissRequest = onDismiss
    )
}
