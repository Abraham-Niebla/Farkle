package uabc.farkle.data

data class ScoreRegister(
    val nombreJugador:   String   = "Jugador",
    val puntajeObjetivo: Int      = 0,
    val puntajeLogrado:  Int      = 0,
    val totalTiros:      Int      = 0,
    val victoria:        Boolean  = false,
    val fechaJuego:      String   = "Sin fecha",
    val horaJuego:       String   = "Sin hora"
){
    override fun toString(): String {
        return "${nombreJugador}|${puntajeObjetivo}|${puntajeLogrado}|${totalTiros}|${victoria}|${fechaJuego}|${horaJuego}"
    }
}
