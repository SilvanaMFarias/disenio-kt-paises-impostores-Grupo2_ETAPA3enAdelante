package ar.edu.unahur.obj2.impostoresPaises

import ar.edu.unahur.obj2.impostoresPaises.api.CurrencyConverterAPI
import ar.edu.unahur.obj2.impostoresPaises.api.RestCountriesAPI

object Observatorio {
    lateinit var apiAdaptada: AdaptadorAPI

    fun setApis(apiCountry: RestCountriesAPI, apiCurrency: CurrencyConverterAPI) {
        if (!this::apiAdaptada.isInitialized)
            apiAdaptada = AdaptadorAPI(apiCountry, apiCurrency)
    }

    fun paises() = apiAdaptada.todosLosPaises()

    fun paisConCodigo(codigoIso: String) = apiAdaptada.paisConCodigo(codigoIso)

    fun paisesConNombre(nombrePais: String) = apiAdaptada.buscarPaisesPorNombre(nombrePais)

    fun esPais(nombrePais: String): Boolean = paises().any { p -> p.nombre == nombrePais }

    fun obtenerPais(nombrePais: String): Pais = paises().find { p -> p.nombre == nombrePais }!!

    fun vecinoMasPoblado(nombrePais: String): Pais {
        if(esPais(nombrePais))
            with (obtenerPais(nombrePais)) {
                return (this.paisesLimitrofes + this).maxByOrNull { p->p.poblacion }!!
            }
        else
            throw Exception("Pais es inválido.")
    }

    fun sonLimitrofes(paisA: String, paisB: String): Boolean {
        if (paisA == paisB)
            throw Exception("Los países límitrofes no pueden ser iguales")
        else if (esPais(paisA) and esPais(paisB))
            return obtenerPais(paisA).esLimitrofeDe(obtenerPais(paisB))
        else
            throw Exception("Algún pais es inválido.")
    }

    fun necesitanTraduccion(paisA: String, paisB: String): Boolean =
        if (esPais(paisA) and esPais(paisB))
            obtenerPais(paisA).necesitaTraduccionCon(obtenerPais(paisB))
        else
            throw Exception("Algún pais es inválido.")

    fun compartenBloqueRegional(paisA: String, paisB: String): Boolean =
        if (esPais(paisA) and esPais(paisB))
            obtenerPais(paisA).comparteBloqueRegionalCon(obtenerPais(paisB))
        else
            throw Exception("Algún pais es inválido.")

    fun sonPotencialesAliados(paisA: String, paisB: String): Boolean =
        !necesitanTraduccion(paisA, paisB) and compartenBloqueRegional(paisA, paisB)

    fun convieneIrDeComprasDesdeA(paisOrigen: String, paisDestino: String): Boolean =
        if (esPais(paisOrigen) and esPais(paisDestino))
            obtenerPais(paisOrigen).convieneIrDeComprasA(obtenerPais(paisDestino))
        else
            throw Exception("Algún pais es inválido.")

    fun aCuantoEquivaleEn(monto: Double, paisOrigen: String, paisDestino: String): Double =
        if (esPais(paisOrigen) and esPais(paisDestino))
            obtenerPais(paisOrigen).aCuantoEquivaleEn(monto, obtenerPais(paisDestino))
        else
            throw Exception("Algún pais es inválido.")

    fun codigosPaisesMasDensamentePoblados(): List<String> =
        paises().sortedByDescending { p->p.densidadPoblacional() }.take(5).map { p -> p.codigoIso3 }.orEmpty()

    fun continenteConMasPaisesPlurinacionales(): String =
        paises().filter { p -> p.esPlurinacional() }.groupBy { p->p.continente }.maxByOrNull { c -> c.value.size }!!.key

    fun hayPaisesInsulares(): Boolean = paises().any { p -> p.esUnaIsla() }

    fun promedioDensidadPoblacionalPaisesInsulares(): Double {
        if (hayPaisesInsulares())
            return paises().filter { p -> p.esUnaIsla() }.run {
                (this.sumOf { p -> p.densidadPoblacional() } / this.size).toDouble()
            }
        else
            // return 0.00 // como no hay paises insulares el promedio es 0
            throw Exception("No hay países Insulares")
    }
}
