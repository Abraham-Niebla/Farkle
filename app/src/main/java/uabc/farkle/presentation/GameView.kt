package uabc.farkle.presentation

import android.app.Activity
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uabc.farkle.utils.*
import uabc.farkle.R
import uabc.farkle.data.ScoreRegister
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameView(modifier: Modifier, maxScore: Int = DEFAULT_MAX_SCORE, player1: String, player2: String) {
    var dice1 by remember { mutableStateOf(1) }
    var dice2 by remember { mutableStateOf(2) }
    var dice3 by remember { mutableStateOf(3) }
    var dice4 by remember { mutableStateOf(4) }
    var dice5 by remember { mutableStateOf(5) }
    var dice6 by remember { mutableStateOf(6) }

    var rollEnabled by remember { mutableStateOf(true) }

    val dateFormatter = SimpleDateFormat("dd-MMMM-yyyy", Locale.getDefault())
    val timeFormatter = SimpleDateFormat("HH:mm:ss", Locale.getDefault())

    var player by remember { mutableStateOf(0)}

    var names = remember { mutableStateListOf(player1, player2)}
    var puntos = remember { mutableStateListOf(0, 0)}
    var tiros = remember { mutableStateListOf(0, 0)}

    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    fun getDiceImage(dice: Int) = when (dice) {
        1 -> R.drawable.dice_1
        2 -> R.drawable.dice_2
        3 -> R.drawable.dice_3
        4 -> R.drawable.dice_4
        5 -> R.drawable.dice_5
        else -> R.drawable.dice_6
    }

    fun changePlayer(player: Int) = when(player){
        0 -> 1
        1 -> 0
        else -> 0
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer,
                    titleContentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    navigationIconContentColor = MaterialTheme.colorScheme.onSecondaryContainer
                ),
                title = {
                    Text(
                        text = stringResource(R.string.play),
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        val activity = context as? Activity
                        activity?.finish()
                    }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Regresar"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Max Score: $maxScore",
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleSmall
            )

            Spacer(modifier = Modifier.height(50.dp))

            Card {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(2.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        listOf(dice1, dice2, dice3).forEach { dice ->
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .aspectRatio(1f)
                                    .padding(1.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(getDiceImage(dice)),
                                    contentDescription = dice.toString(),
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
                        listOf(dice4, dice5, dice6).forEach { dice ->
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .aspectRatio(1f)
                                    .padding(1.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(getDiceImage(dice)),
                                    contentDescription = dice.toString(),
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    rollEnabled = false
                    scope.launch {
                        repeat(6) {
                            dice1 = (1..6).random()
                            dice2 = (1..6).random()
                            dice3 = (1..6).random()
                            dice4 = (1..6).random()
                            dice5 = (1..6).random()
                            dice6 = (1..6).random()
                            delay(10)
                        }
                        puntos[player] += dice1 + dice2 + dice3 + dice4 + dice5 + dice6
                        tiros[player] ++

                        saveFile(
                            context = context,
                            data = ScoreRegister(
                                nombreJugador = names[player],
                                puntajeObjetivo = maxScore,
                                puntajeLogrado = puntos[player],
                                totalTiros = tiros[player],
                                victoria = puntos[player] %2 == 0,
                                fechaJuego = dateFormatter.format(Date()),
                                horaJuego = timeFormatter.format(Date())
                            )
                        )
                        rollEnabled = true
                        player = changePlayer(player)
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                enabled = rollEnabled
            ) {
                Text(
                    text = stringResource(R.string.roll),
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}
