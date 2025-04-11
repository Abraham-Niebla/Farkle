package uabc.farkle.presentation

import android.content.Context
import android.content.Intent
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uabc.farkle.intents.*
import uabc.farkle.R

@Composable
fun MainView(modifier: Modifier, context: Context) {
    val buttonStyle = MaterialTheme.typography.titleLarge
    val buttonColors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.tertiary,       // Color de fondo del botón
        contentColor = MaterialTheme.colorScheme.onTertiary        // Color del texto/icono dentro del botón
    )
    val buttonSpacer = Modifier.height(20.dp)

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
                val intent = Intent(context, GameActivity::class.java)
                intent.putExtra("maxScore", 10000)
                context.startActivity(intent)
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
                intent.putExtra("maxScore", 10000)
                context.startActivity(intent)
            }) {
            Text(
                text = stringResource(R.string.max_score),
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
                intent.putExtra("maxScore", 10000)
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
                intent.putExtra("maxScore", 10000)
                context.startActivity(intent)
            }) {
            Text(
                text = stringResource(R.string.rules),
                style = buttonStyle
            )
        }
    }
}