package uabc.farkle.dialogs

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import uabc.farkle.R

@Composable
fun DrawDialog(
    onDismiss: () -> Unit
) {
    AlertDialog(
        icon = {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = stringResource(R.string.draw)
            )
        },
        title = {
            Text(stringResource(R.string.draw))
        },
        text = {
            Text(stringResource(R.string.draw_dialog))
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.accept))
            }
        },
        onDismissRequest = onDismiss
    )
}
