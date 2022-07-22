package ar.edu.unahur.obj2.impostoresPaises

import ar.edu.unahur.obj2.impostoresPaises.api.CurrencyConverterAPI
import kotlin.math.roundToInt
import kotlin.reflect.jvm.internal.impl.types.AbstractTypeCheckerContext.SupertypesPolicy.None
import java.text.SimpleDateFormat
import java.util.Date
import java.time.*
import kotlin.math.absoluteValue

class Pais(
  val nombre: String,
  val codigoIso3: String,
  var poblacion: Long,
  var superficie:Double,
  val continente: String,
  var codigoMoneda: String,
//  var cotizacionDolar: Double,
  val bloquesRegionales: List<String>,
  val idiomasOficiales: List<String>) {
  val paisesLimitrofes: MutableList<String> = mutableListOf()

  // Servicio de cotizacion NO necesita adpater.
  val apiCurrency = CurrencyConverterAPI(apiKey = "294dc89cf3f803ab8787")
  
  var ultimaCotizacionDolar: Double = 0.0
  var timeStampCotizacion = LocalTime.of(0,0,0)
  // intervalo entre consultas en minutos
  val intervalo: Int = 5

  fun pasoIntervaloEntreConsultas(actualTimeStamp :LocalTime): Boolean = (Duration.between(actualTimeStamp, timeStampCotizacion).getSeconds()/60).toInt().absoluteValue > intervalo

  fun cotizacionDolar(): Double {
    if(pasoIntervaloEntreConsultas(LocalTime.now())) {
      // actualizo la ultima Cotizacion y su hora.
      timeStampCotizacion = LocalTime.now()
      ultimaCotizacionDolar = apiCurrency.convertirDolarA(codigoMoneda) ?: 0.0
    }
    return ultimaCotizacionDolar
  }
  
  fun agregarPaisLimitrofe(pais: Pais) {
      paisesLimitrofes.add(pais.codigoIso3)
  }
/*
 * Acá creo un método que al asignar limítrofe
 * a un pais con otro completa la relación recíproca.
 */
  fun agregarPaisLimitrofeMutuo(pais: Pais) {
    this.agregarPaisLimitrofe(pais)
    pais.agregarPaisLimitrofe(this)
  }
/* Para un país:
 *
 * Indicar si es plurinacional. En una mega-simplificación de este concepto, 
 * diremos que un país es plurinacional si tiene más de un idioma oficial.
 */
  fun esPlurinacional(): Boolean = idiomasOficiales.size > 1
/*
 * Saber si es una isla, lo cual es cierto si no tiene ningún país limítrofe.
 */
  fun esUnaIsla(): Boolean = paisesLimitrofes.isEmpty()
/*
 * Calcular su densidad poblacional, la cual se obtiene dividiendo a la 
 * población por la superficie. Para no tener problemas en etapas posteriores, 
 * redondeen este número de forma tal que devuelva un entero (pueden usar 
 * roundToInt() para eso).
 */
  fun densidadPoblacional(): Int = (poblacion/superficie).toInt()
/*
 * Conocer al vecino más poblado, que sería el país con mayor población dentro 
 * de la vecindad, que incluye tanto a los limítrofes como al país que 
 * consulta. Por ejemplo: asumiendo que Brasil es el país más poblado de su 
 * vecindad, tanto brasil.vecinoMasPoblado() como peru.vecinoMasPoblado() nos 
 * deberían dar como resultado brasil.
 */
//  fun vecinoMasPoblado(): Pais = (paisesLimitrofes + this).maxByOrNull { p -> p.poblacion }!!
fun vecinoMasPoblado(): Pais {
  // Aca hay que utilizar la API para consultar los paises vecinos ya que perdemos la
  // referencia por pasar solo el codigo ISO3.
  return this //(paisesLimitrofes + this.codigoIso3)
}
/* Para dos países en particular:
 *
 * Poder consultar si son limítrofes.
 */ 
  fun esLimitrofeDe(pais:Pais): Boolean = this.paisesLimitrofes.contains(pais.codigoIso3)
/*
 * Saber si necesitan traducción para poder dialogar. Esto ocurre si no 
 * comparten ninguno de sus idiomas oficiales.
 */
  fun necesitaTraduccionCon(pais: Pais): Boolean = this.idiomasOficiales.intersect(pais.idiomasOficiales).isEmpty()

  fun comparteBloqueRegionalCon(pais: Pais): Boolean = this.bloquesRegionales.intersect(pais.bloquesRegionales).isNotEmpty()
/*
 * Conocer si son potenciales aliados. Esto es así cuando no necesitan 
 * traducción y además comparten algún bloque regional.
 */
  fun esPotencialAliadoDe(pais: Pais): Boolean = !necesitaTraduccionCon(pais) and comparteBloqueRegionalCon(pais)
/* 
 * Saber si conviene ir de compras de uno al otro, lo cual diremos que es 
 * verdadero cuando la cotización del dólar en el país de destino es mayor. Por 
 * ejemplo, si en Argentina la cotización es de 190 y en Bolivia de 6.89, no 
 * conviene ir de Argentina a Bolivia pero sí al revés.
 */
  fun convieneIrDeComprasA(pais: Pais) = this.cotizacionDolar() < pais.cotizacionDolar()
/*
 * Conocer a cuánto equivale un determinado monto en la moneda local, 
 * transformado en la moneda del país de destino. Como la referencia que 
 * estamos tomando es el precio del dólar, la equivalencia se haría 
 * convirtiendo primero a dólar y luego a la moneda de destino.
 */
  fun cambioADolar(montoMonedaLocal: Double): Double = montoMonedaLocal/cotizacionDolar()

  fun cambioAMonedaLocal(montoDolar: Double): Double = montoDolar * cotizacionDolar()

  fun aCuantoEquivaleEn(montoMonedaLocal: Double, pais: Pais): Double = pais.cambioAMonedaLocal(this.cambioADolar(montoMonedaLocal))
}
