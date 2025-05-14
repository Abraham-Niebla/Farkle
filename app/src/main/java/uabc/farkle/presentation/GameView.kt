package uabc.farkle.presentation

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uabc.farkle.R
import uabc.farkle.data.ScoreRegister
import uabc.farkle.dialogs.DrawDialog
import uabc.farkle.dialogs.FarkledDialog
import uabc.farkle.dialogs.WinnerDialog
import uabc.farkle.ui.theme.extendedColors
import uabc.farkle.utils.*
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameView(
    modifier: Modifier,
    maxScore: Int = DEFAULT_MAX_SCORE,
    maxTiros: Int = DEFAULT_THROWS,
    player1: String,
    player2: String
) {
    var rollEnabled by remember { mutableStateOf(true) }
    var showFarkledDialog by remember { mutableStateOf(false) }
    var showWinnerDialog by remember { mutableStateOf(false) }
    var showDrawDialog by remember { mutableStateOf(false) }

    val dateFormatter = SimpleDateFormat("dd-MMMM-yyyy", Locale.getDefault())
    val timeFormatter = SimpleDateFormat("HH:mm:ss", Locale.getDefault())

    var player by remember { mutableIntStateOf(0) }
    var playerCurrentScore by remember { mutableIntStateOf(0) }
    var playerCurrentThrowScore by remember { mutableIntStateOf(0) }
    var playerCurrentTiros by remember { mutableIntStateOf(0) }
    var tirosRestantes by remember { mutableIntStateOf(maxTiros) }
    var lastTurn by remember { mutableStateOf(false) }
    var hotDices by remember { mutableStateOf(false) }

    var winner by remember { mutableIntStateOf(-1) }

    val names = remember { mutableStateListOf(player1, player2) }
    val puntos = remember { mutableStateListOf(0, 0) }
    val tiros = remember { mutableStateListOf(0, 0) }

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
    var rollValues = remember { mutableStateListOf(1, 2, 3, 4, 5, 6) }

    fun changePlayer(player: Int) = if (player == 0) 1 else 0

    fun playerWin() {
        winner = when {
            puntos[0] > puntos[1] -> 0
            puntos[0] < puntos[1] -> 1
            else -> -1
        }

        for (i in 0..1) {
            saveFile(
                context = context,
                data = ScoreRegister(
                    nombreJugador = names[i],
                    puntajeObjetivo = maxScore,
                    puntajeLogrado = puntos[i],
                    totalTiros = tiros[i],
                    victoria = i == winner,
                    empate = winner == -1,
                    fechaJuego = dateFormatter.format(Date()),
                    horaJuego = timeFormatter.format(Date())
                )
            )
        }

        if (winner == -1) showDrawDialog = true else showWinnerDialog = true
    }

    fun endTurn() {
        playerCurrentScore += playerCurrentThrowScore
        puntos[player] += playerCurrentScore

        if (lastTurn){
            playerWin()
        }
        else if (puntos[player] >= maxScore){
            lastTurn = true
        }

        diceLocked.fill(false)
        diceFrozen.fill(false)

        playerCurrentScore = 0
        playerCurrentThrowScore = 0
        playerCurrentTiros = 0
        tirosRestantes = maxTiros
        hasRolledOnce = false
        hotDices = false

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
                        (context as? Activity)?.finish()
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
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.Center,
            ) {
                PlayerElement(
                    nombre = names[0],
                    puntos = puntos[0],
                    enTurno = player == 0
                )

                PlayerElement(
                    nombre = names[1],
                    puntos = puntos[1],
                    enTurno = player == 1
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            InfoElement(
                objetivo = maxScore,
                tirosRestantes = tirosRestantes,
                puntosTurno = (playerCurrentScore + playerCurrentThrowScore)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = stringResource(R.string.hot_dices),
                color = MaterialTheme.extendedColors.hotDicesColor,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
                    .graphicsLayer(alpha = if (hotDices) 1f else 0f)
            )

            Spacer(modifier = Modifier.height(5.dp))

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = when(hotDices){
                        true -> MaterialTheme.extendedColors.hotDicesColor
                        false -> MaterialTheme.colorScheme.secondaryContainer
                    },
                ),
            ) {
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
                        (0..2).forEach { index ->
                            val diceValue = rollValues[index]
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .aspectRatio(1f)
                                    .padding(1.dp)
                                    .clickable(enabled = hasRolledOnce && !diceFrozen[index]) {
                                        if(!hotDices){
                                            diceLocked[index] = !diceLocked[index]

                                            val selectedDice = listOf(
                                                if (diceLocked[0] && !diceFrozen[0]) rollValues[0] else 0,
                                                if (diceLocked[1] && !diceFrozen[1]) rollValues[1] else 0,
                                                if (diceLocked[2] && !diceFrozen[2]) rollValues[2] else 0,
                                                if (diceLocked[3] && !diceFrozen[3]) rollValues[3] else 0,
                                                if (diceLocked[4] && !diceFrozen[4]) rollValues[4] else 0,
                                                if (diceLocked[5] && !diceFrozen[5]) rollValues[5] else 0
                                            )
                                            val scoreFromLocked = calculateScore(selectedDice)
                                            playerCurrentThrowScore = scoreFromLocked
                                        }
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(getDiceImage(diceValue)),
                                    contentDescription = diceValue.toString(),
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .graphicsLayer(alpha = if (diceLocked[index]) 0.5f else 1f)
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(1.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        (3..5).forEach { index ->
                            val diceValue = rollValues[index]
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .aspectRatio(1f)
                                    .padding(1.dp)
                                    .clickable(enabled = hasRolledOnce && !diceFrozen[index]) {
                                        if (!hotDices) {
                                            diceLocked[index] = !diceLocked[index]

                                            val selectedDice = listOf(
                                                if (diceLocked[0] && !diceFrozen[0]) rollValues[0] else 0,
                                                if (diceLocked[1] && !diceFrozen[1]) rollValues[1] else 0,
                                                if (diceLocked[2] && !diceFrozen[2]) rollValues[2] else 0,
                                                if (diceLocked[3] && !diceFrozen[3]) rollValues[3] else 0,
                                                if (diceLocked[4] && !diceFrozen[4]) rollValues[4] else 0,
                                                if (diceLocked[5] && !diceFrozen[5]) rollValues[5] else 0
                                            )
                                            val scoreFromLocked = calculateScore(selectedDice)
                                            playerCurrentThrowScore = scoreFromLocked
                                        }
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(getDiceImage(diceValue)),
                                    contentDescription = diceValue.toString(),
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .graphicsLayer(alpha = if (diceLocked[index]) 0.5f else 1f)
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
                    hotDices = false

                    playerCurrentScore += playerCurrentThrowScore
                    playerCurrentThrowScore = 0

                    // Congelar el estado de los dados bloqueados antes de la tirada
                    for (i in 0 until diceLocked.size) {
                        diceFrozen[i] = diceLocked[i]
                    }
                    scope.launch {
                        repeat(6) {
                            for (i in 0 until rollValues.size) {
                                if (!diceFrozen[i]) {
                                    rollValues[i] = (1..6).random()
                                }
                            }
                            delay(10)
                        }

                        val frozenRoll = rollValues.mapIndexed { index, value -> if (!diceFrozen[index]) value else 0 }
                        val score = calculateScore(frozenRoll)

                        if (score == 0) { // Player Farkled
                            showFarkledDialog = true
                            playerCurrentScore = 0
                            playerCurrentThrowScore = 0
                        }
                        else { // Player not Farkled
                            hotDices = calculateHotDices(rollValues)
                            if(hotDices){
                                playerCurrentThrowScore = score

                                diceLocked.fill(false)
                                diceFrozen.fill(false)
                            }

                            tiros[player]++
                            playerCurrentTiros++
                            tirosRestantes--

                            hasRolledOnce = true
                            rollEnabled = true
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                enabled = ((!(diceLocked.all { it } || diceFrozen.all { it })) && (tirosRestantes > 0))
            ) {
                Text(
                    text = stringResource(R.string.roll),
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { endTurn() },
                enabled = (diceLocked.any { it } || diceFrozen.any { it }) || hotDices
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
                (context as? Activity)?.finish()
                showWinnerDialog = false
            },
            winner = names[winner]
        )
    }
    if (showDrawDialog) {
        DrawDialog(
            onDismiss = {
                (context as? Activity)?.finish()
                showDrawDialog = false
            }
        )
    }
}
