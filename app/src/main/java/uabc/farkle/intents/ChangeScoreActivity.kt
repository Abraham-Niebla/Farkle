package uabc.farkle.intents

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import uabc.farkle.presentation.*
import uabc.farkle.ui.theme.FarkleTheme

class ChangeScoreActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = intent
        val maxScore = intent.getIntExtra("maxScore", 0)
        val maxTiros = intent.getIntExtra("maxTiros", 0)

        enableEdgeToEdge()
        setContent {
            FarkleTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ChangeScoreView(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize(),
                        maxScore = maxScore,
                        maxTiros = maxTiros
                    )
                }
            }
        }
    }
}