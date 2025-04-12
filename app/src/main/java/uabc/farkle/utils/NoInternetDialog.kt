package uabc.farkle.utils

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import uabc.farkle.R

@Composable
fun NoInternetDialog(
    onDismiss: () -> Unit
) {
    AlertDialog(
        icon = {
            Icon(
                imageVector = Icons.Filled.WifiOff,
                contentDescription = stringResource(R.string.no_conection)
            )
        },
        title = {
            Text(stringResource(R.string.no_conection))
        },
        text = {
            Text(stringResource(R.string.need_conection))
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.accept))
            }
        },
        onDismissRequest = onDismiss
    )
}
