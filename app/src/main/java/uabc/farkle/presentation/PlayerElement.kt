package uabc.farkle.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uabc.farkle.R
import uabc.farkle.ui.theme.FarkleTheme
import uabc.farkle.ui.theme.extendedColors

@Composable
fun PlayerElement(nombre: String, puntos: Int, enTurno: Boolean){
    val nombreStyle  = MaterialTheme.typography.titleLarge
    val puntajeStyle = MaterialTheme.typography.titleSmall

    val cardColor = when(enTurno){
        true  -> MaterialTheme.extendedColors.customColor4Container
        false -> MaterialTheme.colorScheme.surfaceContainer
    }

    val cardContainerColor = when(enTurno){
        true  -> MaterialTheme.extendedColors.onCustomColor4Container
        false -> MaterialTheme.colorScheme.onSurface
    }


    ElevatedCard(
        modifier = Modifier
            .padding(5.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = cardColor,
            contentColor = cardContainerColor
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ){
        Column(
            modifier = Modifier.padding(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = nombre,
                style = nombreStyle,
                modifier = Modifier.padding(3.dp),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.padding(2.dp))

            Text(
                text = stringResource(R.string.score),
                style = puntajeStyle,
                modifier = Modifier.padding(3.dp),
                textAlign = TextAlign.Center
            )
            Text(
                text = "$puntos",
                style = puntajeStyle,
                modifier = Modifier.padding(3.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
fun PlayerElementPreviewInTurn(){
    FarkleTheme(darkTheme = false){
        PlayerElement(
            nombre = "Nombre",
            puntos = 10000,
            enTurno = true
        )
    }
}

@Preview
@Composable
fun PlayerElementPreviewNotInTurn(){
    FarkleTheme(darkTheme = false){
        PlayerElement(
            nombre = "Nombre",
            puntos = 10000,
            enTurno = false
        )
    }
}

@Preview
@Composable
fun PlayerElementPreviewInTurnDark(){
    FarkleTheme(darkTheme = true){
        PlayerElement(
            nombre = "Nombre",
            puntos = 10000,
            enTurno = true
        )
    }
}

@Preview
@Composable
fun PlayerElementPreviewNotInTurnDark(){
    FarkleTheme(darkTheme = true){
        PlayerElement(
            nombre = "Nombre",
            puntos = 10000,
            enTurno = false
        )
    }
}