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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import uabc.farkle.intents.ChangeScoreActivity

@Composable
fun ChangeScoreView(modifier: Modifier, maxScore: Int) {
    val context = LocalContext.current
    var puntaje by remember { mutableStateOf("${maxScore}") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
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

        Button(
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.tertiary,
                contentColor = MaterialTheme.colorScheme.onTertiary
            ),
            onClick = {
                var puntajeInt = puntaje.toIntOrNull() ?: 0
                puntajeInt = if (puntajeInt <= 0) 10000 else puntajeInt
                val activity = context as? Activity
                val intent = Intent().apply {
                    putExtra("puntaje", puntajeInt)
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