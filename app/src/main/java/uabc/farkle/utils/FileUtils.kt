package uabc.farkle.utils

import android.content.Context
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import uabc.farkle.data.ScoreRegister
import kotlin.String

fun saveFile(context: Context, data: ScoreRegister) {
//    val dateFormatter = SimpleDateFormat("dd-MMMM-yyyy", Locale.getDefault())
//    val timeFormatter = SimpleDateFormat("HH:mm", Locale.getDefault())
//    val currentDate = dateFormatter.format(Date())
//    val currentTime = timeFormatter.format(Date())

    val fileString = data.toString() + '\n'

    context.openFileOutput(SCORES_FILE_NAME, Context.MODE_APPEND).use {
        it.write(fileString.toByteArray())
    }
}

fun readFile(context: Context): List<ScoreRegister> {
    var data = ""
    var dataList = listOf<String>()
    var scoresList = mutableListOf<ScoreRegister>()

    try {
        data = context.openFileInput(SCORES_FILE_NAME).bufferedReader().use { it.readText() }
        dataList = data.split('\n')

        dataList.forEach{dataString ->
            val dataSeparated = dataString.split('|')
            scoresList.add(ScoreRegister(
                nombreJugador   = dataSeparated[0].toString(), // String
                puntajeObjetivo = dataSeparated[1].toString().toInt(), // Int
                puntajeLogrado  = dataSeparated[2].toString().toInt(), // Int
                totalTiros      = dataSeparated[3].toString().toInt(), // Int
                victoria        = dataSeparated[4].toString().toBoolean(), // Boolean
                empate          = dataSeparated[5].toString().toBoolean(), // Boolean
                fechaJuego      = dataSeparated[6].toString(), // String
                horaJuego       = dataSeparated[7].toString()  // String
            ))
        }
    } catch (e: Exception) {
        data = "No hay datos guardados"
    }


    return scoresList
}

fun clearFile(context: Context) {
    context.openFileOutput(SCORES_FILE_NAME, Context.MODE_PRIVATE).use {
        it.write("".toByteArray())
    }
}