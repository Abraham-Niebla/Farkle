package uabc.farkle.dialogs

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import uabc.farkle.R

@Composable
fun DeleteFileDialog(
    onDelete: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        icon = {
            Icon(
                imageVector = Icons.Filled.Warning,
                contentDescription = stringResource(R.string.warning)
            )
        },
        title = {
            Text(stringResource(R.string.warning))
        },
        text = {
            Text(stringResource(R.string.clear_file_warning))
        },
        confirmButton = {
            TextButton(onClick = onDelete) {
                Text(stringResource(R.string.delete))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { // Acción vacía para cerrar el diálogo sin hacer nada
                Text(stringResource(R.string.cancel))
            }
        },
        onDismissRequest = onDismiss // Cierra el diálogo si se toca fuera de él
    )
}
