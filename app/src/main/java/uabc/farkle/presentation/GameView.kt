package uabc.farkle.presentation

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uabc.farkle.utils.*
import uabc.farkle.R
import uabc.farkle.data.ScoreRegister
import uabc.farkle.dialogs.DrawDialog
import uabc.farkle.dialogs.FarkledDialog
import uabc.farkle.dialogs.WinnerDialog
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameView(
    modifier: Modifier,
    maxScore: Int = DEFAULT_MAX_SCORE,
    maxTiros: Int = DEFAULT_THROWS,
    player1: String,
    player2: String
) {
    var dice1 by remember { mutableIntStateOf(1) }
    var dice2 by remember { mutableIntStateOf(2) }
    var dice3 by remember { mutableIntStateOf(3) }
    var dice4 by remember { mutableIntStateOf(4) }
    var dice5 by remember { mutableIntStateOf(5) }
    var dice6 by remember { mutableIntStateOf(6) }

    var rollEnabled by remember { mutableStateOf(true) }
    var showFarkledDialog by remember { mutableStateOf(false) }
    var showWinnerDialog by remember { mutableStateOf(false) }
    var showDrawDialog by remember { mutableStateOf(false) }

    val dateFormatter = SimpleDateFormat("dd-MMMM-yyyy", Locale.getDefault())
    val timeFormatter = SimpleDateFormat("HH:mm:ss", Locale.getDefault())

    var player by remember { mutableIntStateOf(0) }
    var playerCurrentScore by remember { mutableIntStateOf(0) }
    var playerCurrentTiros by remember { mutableIntStateOf(0) }
    var tirosRestantes by remember { mutableIntStateOf(maxTiros) }
    var lastTurn by remember { mutableStateOf(false) }

    var winner by remember { mutableIntStateOf(-1) } //Definir en funcion localmente

    var names = remember { mutableStateListOf(player1, player2) }
    var puntos = remember { mutableStateListOf(0, 0) }
    var tiros = remember { mutableStateListOf(0, 0) }

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

    val diceLocked = remember { mutableStateListOf(false, false, false, false, false, false) }
    val diceFrozen = remember { mutableStateListOf(false, false, false, false, false, false) }
    var hasRolledOnce by remember { mutableStateOf(false) }

    fun changePlayer(player: Int) = when (player) {
        0 -> 1
        1 -> 0
        else -> 0
    }

    fun playerWin(){
        winner = when(puntos[0] > puntos[1]){
            true -> 0 // Gana jugador 0
            else -> when(puntos[0] < puntos[1]){
                true -> 1 // Gana jugador 1
                else -> -1 // Empate
            }
        }

        var playerSave = 0
        saveFile(
            context = context,
            data = ScoreRegister(
                nombreJugador = names[playerSave],
                puntajeObjetivo = maxScore,
                puntajeLogrado = puntos[playerSave],
                totalTiros = tiros[playerSave],
                victoria = playerSave == winner,
                empate = winner == -1,
                fechaJuego = dateFormatter.format(Date()),
                horaJuego = timeFormatter.format(Date())
            )
        )

        playerSave = 1
        saveFile(
            context = context,
            data = ScoreRegister(
                nombreJugador = names[playerSave],
                puntajeObjetivo = maxScore,
                puntajeLogrado = puntos[playerSave],
                totalTiros = tiros[playerSave],
                victoria = playerSave == winner,
                empate = winner == -1,
                fechaJuego = dateFormatter.format(Date()),
                horaJuego = timeFormatter.format(Date())
            )
        )

        if (winner == -1){
            showDrawDialog = true
        }
        else{
            showWinnerDialog = true
        }
    }

    fun endTurn() {
        puntos[player] += playerCurrentScore

        if (lastTurn){
            playerWin()
        }
        else if (puntos[player] >= maxScore){
            lastTurn = true
        }

        // Desbloquear todos los dados
        for (i in 0 until diceLocked.size) {
            diceLocked[i] = false
        }
        // Resetear el estado de congelación
        for (i in 0 until diceFrozen.size) {
            diceFrozen[i] = false
        }

        playerCurrentScore = 0
        playerCurrentTiros = 0
        tirosRestantes = maxTiros

        hasRolledOnce = false // Resetear para el nuevo jugador
        player = changePlayer(player)
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
                            contentDescription = stringResource(R.string.page_return)
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
                text = stringResource(R.string.objetivo, maxScore),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = stringResource(R.string.remaining_throws, tirosRestantes),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = stringResource(R.string.actual_player, names[player]),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = stringResource(R.string.actual_player_score, playerCurrentScore),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = stringResource(R.string.player_score, puntos[player]),
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
                        listOf(dice1, dice2, dice3).forEachIndexed { index, dice ->
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .aspectRatio(1f)
                                    .padding(1.dp)
                                    .clickable(enabled = hasRolledOnce && !diceFrozen[index]) {
                                        diceLocked[index] = !diceLocked[index]
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(getDiceImage(dice)),
                                    contentDescription = dice.toString(),
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .graphicsLayer(
                                            alpha = if (diceLocked[index]) 0.5f else 1f
                                        )
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(1.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        listOf(dice4, dice5, dice6).forEachIndexed { index, dice ->
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .aspectRatio(1f)
                                    .padding(1.dp)
                                    .clickable(enabled = hasRolledOnce && !diceFrozen[index + 3]) {
                                        diceLocked[index + 3] = !diceLocked[index + 3]
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(getDiceImage(dice)),
                                    contentDescription = dice.toString(),
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .graphicsLayer(
                                            alpha = if (diceLocked[index + 3]) 0.5f else 1f
                                        )
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
                    // Congelar el estado de los dados bloqueados antes de la tirada
                    for (i in 0 until diceLocked.size) {
                        diceFrozen[i] = diceLocked[i]
                    }
                    scope.launch {
                        repeat(6) {
                            if (!diceFrozen[0]) dice1 = (1..6).random()
                            if (!diceFrozen[1]) dice2 = (1..6).random()
                            if (!diceFrozen[2]) dice3 = (1..6).random()
                            if (!diceFrozen[3]) dice4 = (1..6).random()
                            if (!diceFrozen[4]) dice5 = (1..6).random()
                            if (!diceFrozen[5]) dice6 = (1..6).random()
                            delay(10)
                        }
                        // Sumar solo los valores de los dados que NO estaban bloqueados al tirar
                        var currentScore = calculateScore(
                            listOf(
                                if (!diceFrozen[0]) dice1 else 0,
                                if (!diceFrozen[1]) dice2 else 0,
                                if (!diceFrozen[2]) dice3 else 0,
                                if (!diceFrozen[3]) dice4 else 0,
                                if (!diceFrozen[4]) dice5 else 0,
                                if (!diceFrozen[5]) dice6 else 0
                            )
                        )

                        if (currentScore == 0) {
                            showFarkledDialog = true
                            playerCurrentScore = 0
                        }

                        playerCurrentScore += currentScore
                        tiros[player]++
                        playerCurrentTiros++
                        tirosRestantes--

                        if (!hasRolledOnce) {
                            hasRolledOnce = true
                        }

                        rollEnabled = true
                        // No cambiamos de jugador aquí, el botón "Terminar Turno" se encargará de eso
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                enabled = (!(diceLocked.all { it } || diceFrozen.all { it })) && (tirosRestantes > 0)
            ) {
                Text(
                    text = stringResource(R.string.roll),
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    endTurn()
                },
                enabled = diceLocked.any { it } // Habilitado solo si hay al menos un dado bloqueado
            ) {
                Text(
                    text = stringResource(R.string.end_turn),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
    if (showFarkledDialog) {
        FarkledDialog(
            onDismiss = {
                endTurn()
                showFarkledDialog = false
            }
        )
    }
    if (showWinnerDialog) {
        WinnerDialog(
            onDismiss = {
                val activity = context as? Activity
                activity?.finish()
                showWinnerDialog = false
            },
            winner = names[winner]
        )
    }
    if (showDrawDialog) {
        DrawDialog(
            onDismiss = {
                val activity = context as? Activity
                activity?.finish()
                showDrawDialog = false
            }
        )
    }
}