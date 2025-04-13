package uabc.farkle.intents

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import uabc.farkle.ui.theme.FarkleTheme
import uabc.farkle.presentation.*

class GameActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = intent
        val maxScore = intent.getIntExtra("maxScore", 0)
        val player1 = intent.getStringExtra("player1").takeIf { !it.isNullOrBlank() } ?: "Jugador 1"
        val player2 = intent.getStringExtra("player2").takeIf { !it.isNullOrBlank() } ?: "Jugador 2"

        enableEdgeToEdge()
        setContent {
            FarkleTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    GameView(
                        modifier = Modifier.padding(innerPadding).fillMaxSize(),
                        maxScore = maxScore,
                        player1 = player1,
                        player2 = player2
                    )
                }
            }
        }
    }
}