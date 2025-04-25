package uabc.farkle.dialogs

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import uabc.farkle.R

@Composable
fun FarkledDialog(
    onDismiss: () -> Unit
) {
    AlertDialog(
        icon = {
            Icon(
                imageVector = Icons.Filled.Error,
                contentDescription = stringResource(R.string.farkled)
            )
        },
        title = {
            Text(stringResource(R.string.farkled))
        },
        text = {
            Text(stringResource(R.string.farkled_msg))
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.end_turn))
            }
        },
        onDismissRequest = onDismiss
    )
}
