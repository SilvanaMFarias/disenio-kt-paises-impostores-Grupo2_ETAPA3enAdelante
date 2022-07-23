package ar.edu.unahur.obj2.impostoresPaises

import kotlin.math.roundToInt

class Pais(
    val nombre: String,
    val codigoIso3: String,
    var poblacion: Long,
    var superficie:Double,
    val continente: String,
    var codigoMoneda: String,
    var cotizacionDolar: Double,
    val bloquesRegionales: List<String>,
    val idiomasOficiales: List<String>) {

    val paisesLimitrofes: MutableList<Pais> = mutableListOf()

    fun agregarPaisLimitrofe(pais: Pais) {
        paisesLimitrofes.add(pais)
    }

    fun agregarPaisLimitrofeMutuo(pais: Pais) {
        this.agregarPaisLimitrofe(pais)
        pais.agregarPaisLimitrofe(this)
    }

    fun esPlurinacional(): Boolean = idiomasOficiales.size > 1

    fun esUnaIsla(): Boolean = paisesLimitrofes.isEmpty()

    fun densidadPoblacional(): Int = (poblacion/superficie).roundToInt()

    fun vecinoMasPoblado(): Pais = (paisesLimitrofes + this).maxByOrNull { p -> p.poblacion }!!

    fun esLimitrofeDe(pais:Pais): Boolean = this.paisesLimitrofes.any{ p->p.nombre == pais.nombre}

    fun necesitaTraduccionCon(pais: Pais): Boolean = this.idiomasOficiales.intersect(pais.idiomasOficiales).isEmpty()

    fun comparteBloqueRegionalCon(pais: Pais): Boolean = this.bloquesRegionales.intersect(pais.bloquesRegionales).isNotEmpty()

    fun esPotencialAliadoDe(pais: Pais): Boolean = !necesitaTraduccionCon(pais) and comparteBloqueRegionalCon(pais)

    fun convieneIrDeComprasA(pais: Pais) = this.cotizacionDolar < pais.cotizacionDolar

    fun cambioADolar(montoMonedaLocal: Double): Double = montoMonedaLocal/cotizacionDolar

    fun cambioAMonedaLocal(montoDolar: Double): Double = montoDolar * cotizacionDolar

    fun aCuantoEquivaleEn(montoMonedaLocal: Double, pais: Pais): Double = pais.cambioAMonedaLocal(this.cambioADolar(montoMonedaLocal))
}
