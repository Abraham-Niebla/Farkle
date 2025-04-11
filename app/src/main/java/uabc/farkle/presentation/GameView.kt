package uabc.farkle.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uabc.farkle.R
import uabc.farkle.utils.*

@Composable
fun GameView(modifier: Modifier, maxScore: Int){
    var result1 by remember { mutableStateOf(1) }
    var result2 by remember { mutableStateOf(2) }
    var result3 by remember { mutableStateOf(3) }
    var result4 by remember { mutableStateOf(4) }
    var result5 by remember { mutableStateOf(5) }
    var result6 by remember { mutableStateOf(6) }

    val scope = rememberCoroutineScope()

    fun getDiceImage(result: Int) = when (result) {
        1 -> R.drawable.dice_1
        2 -> R.drawable.dice_2
        3 -> R.drawable.dice_3
        4 -> R.drawable.dice_4
        5 -> R.drawable.dice_5
        else -> R.drawable.dice_6
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp)
            ) {
                Text(
                    text = "Max Socre: ${maxScore}",
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.displayLarge
                )

                Spacer(modifier = Modifier.height(50.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    listOf(result1, result2, result3).forEach { result ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                                .padding(1.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(getDiceImage(result)),
                                contentDescription = result.toString(),
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(1.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    listOf(result4, result5, result6).forEach { result ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                                .padding(1.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(getDiceImage(result)),
                                contentDescription = result.toString(),
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = {
            scope.launch {
                repeat(10) {
                    result1 = (1..6).random()
                    result2 = (1..6).random()
                    result3 = (1..6).random()
                    result4 = (1..6).random()
                    result5 = (1..6).random()
                    result6 = (1..6).random()
                    delay(150) // Pausa de 100ms entre cambios
                }
            }
        }) {
            Text(text = stringResource(R.string.roll))
        }
    }
}