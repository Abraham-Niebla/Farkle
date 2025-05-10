package uabc.farkle.presentation

import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uabc.farkle.data.ScoreRegister
import uabc.farkle.ui.theme.extendedColors

@Composable
fun ScoreElement(score: ScoreRegister){
    val nombreStyle     = MaterialTheme.typography.headlineSmall
    val victoriaStyle   = MaterialTheme.typography.headlineSmall
    val puntajeStyle    = MaterialTheme.typography.titleMedium
    val fechaStyle      = MaterialTheme.typography.labelMedium

    val cardColor = when(score.victoria){
        true  -> MaterialTheme.extendedColors.customColor1Container
        false -> when(score.empate){
            false -> MaterialTheme.extendedColors.customColor2Container
            true -> MaterialTheme.extendedColors.customColor3Container
        }
    }

    val cardContainerColor = when(score.victoria){
        true  -> MaterialTheme.extendedColors.onCustomColor1Container
        false -> when(score.empate){
            false -> MaterialTheme.extendedColors.onCustomColor2Container
            true -> MaterialTheme.extendedColors.onCustomColor3Container
        }
    }


    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = cardColor,
            contentColor = cardContainerColor
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ){
        Column(
            modifier = Modifier
                .padding(5.dp)
        ) {
            Row(modifier = Modifier){
                Text(
                    text = score.nombreJugador,
                    style = nombreStyle,
                    modifier = Modifier.padding(3.dp)
                )
                Spacer(modifier = Modifier.padding(horizontal = 10.dp))
                Text(
                    text = when(score.victoria){
                        true -> "(Victoria)"
                        false -> when(score.empate){
                            false -> "(Derrota)"
                            true -> "(Empate)"
                        }
                    },
                    style = victoriaStyle,
                    modifier = Modifier.padding(3.dp)
                )
            }

            Spacer(modifier = Modifier.padding(5.dp))

            Text(
                text = "Puntaje objetivo: ${score.puntajeObjetivo}",
                style = puntajeStyle,
                modifier = Modifier.padding(3.dp)
            )

            Spacer(modifier = Modifier.padding(1.dp))

            Text(
                text = "Puntos conseguidos: ${score.puntajeLogrado}",
                style = puntajeStyle,
                modifier = Modifier.padding(3.dp)
            )

            Spacer(modifier = Modifier.padding(1.dp))

            Text(
                text = "Tiros realizados: ${score.totalTiros}",
                style = puntajeStyle,
                modifier = Modifier.padding(3.dp)
            )

            Spacer(modifier = Modifier.padding(3.dp))

            Text(
                text = "${score.fechaJuego} - ${score.horaJuego}",
                style = fechaStyle,
                modifier = Modifier.padding(3.dp)
            )
        }
    }
}

@Preview
@Composable
fun ScoreElementPreview(){
    ScoreElement(
        ScoreRegister()
    )
}