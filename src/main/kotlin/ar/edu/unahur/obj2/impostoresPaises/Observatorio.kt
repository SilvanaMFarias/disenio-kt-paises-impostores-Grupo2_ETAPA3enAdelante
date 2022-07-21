package ar.edu.unahur.obj2.impostoresPaises

import ar.edu.unahur.obj2.impostoresPaises.Pais
import ar.edu.unahur.obj2.impostoresPaises.*
import ar.edu.unahur.obj2.impostoresPaises.api.Country
import ar.edu.unahur.obj2.impostoresPaises.api.CurrencyConverterAPI
import ar.edu.unahur.obj2.impostoresPaises.api.RestCountriesAPI

interface interfaceAPI {
	fun todosLosPaises(): List<Pais>

  fun buscarPaisesPorNombre(nombre: String): List<Pais>

  fun paisConCodigo(codigoIso3: String): Pais    
}

class AdaptadorAPI(val adaptee: RestCountriesAPI): interfaceAPI {

  override fun todosLosPaises(): MutableList<Pais> {
    val allCountries: List<Country> = adaptee.todosLosPaises()
    val todosLosPaises: MutableList<Pais> = mutableListOf()
    var nombre: String
    var codigoIso3: String
    var poblacion: Int
    var superficie: Double
    var continente: String
    var codigoMoneda: String
    var cotizacionDolar: Double
    var bloquesRegionales: List<String>
    var idiomasOficiales: List<String>
    var paisesLimitrofes: List<String>
    var pais: Pais

    allCountries.forEach {
      nombre = it.name
      codigoIso3 = it.alpha3Code
      poblacion = it.population.toInt()//Redefinir a poblacion como long
      superficie = 0.00//it.area ?: it.population.toDouble()-Redefinir superficie como long
      continente = it.region
      codigoMoneda= "USD"//if (it.currencies?.first()?.code.orEmpty().isEmpty()) it.currencies!!.first().code else "USD"
      //cotizacionDolar=0.00
      bloquesRegionales= it.regionalBlocs!!.map{ b->b.name }
      idiomasOficiales= it.languages.map{ b->b.name }
      paisesLimitrofes= it.borders.orEmpty()

      pais = Pais(nombre, codigoIso3, poblacion, superficie, continente, codigoMoneda, bloquesRegionales, idiomasOficiales)
      pais.paisesLimitrofes.addAll(paisesLimitrofes)

      todosLosPaises.add(pais)
    }

    //TODO("Ya estan los datos pedidos a la API, falta transformalos a lista de Paises.")
    return todosLosPaises
  }

  override fun buscarPaisesPorNombre(nombre: String): List<Pais> {
    val countries: List<Country>
    countries = adaptee.buscarPaisesPorNombre(nombre)
    TODO("Ya estan los datos pedidos a la API, falta transformalos a lista de Paises.")
  }

  override fun paisConCodigo(codigoIso3: String): Pais {
    val country: Country
    country = adaptee.paisConCodigo(codigoIso3)
    TODO("Ya estan los datos pedidos a la API, falta transformalos a Pais.")
  }
}

/*
 * Etapa 2 - Observatorio
 * Crear al Observatorio, que es un objeto que conoce a todos los países y debe 
 * poder responder las consultas que se enuncian a continuación.
 *
 * Se utiliza el patron Singleton, ya que de esta clase solo vamos a acceder siempre
 * la misma instancia, en Kotlin para lograr esto tenemos la palabra reservada
 * object que simplifica la implementación del patrón.
 */
object Observatorio {
  var paises :List<Pais> = listOf<Pais>()
  // Etapa 3 - Etapa 3 - Conectando con el mundo real 

  // Servicio a adaptar.
  val apiCountry = RestCountriesAPI()
  // Servicio adaptado.
  val apiAdaptada = AdaptadorAPI(apiCountry)
      // A partir de acá podemos utilizar los metodos de API pero nos va a devolver
      // en vez del formato <Country> devuelve <Pais> gracias al adaptador.
      //
      // Ejemplo: 	apiAdaptada.todosLosPaises(): List<Pais>
      //            apiAdaptada.buscarPaisesPorNombre("Argentina"): List<Pais>
      //            apiAdaptada.paisConCodigo("ARG"): Pais

  // IMPORTANTE: Solamente al inicializar el Singleton se accede a la API por
  // intermedio del adaptador para llenar la lista países con los datos que
  // devuelve el servicio.
  init {
    paises = apiAdaptada.todosLosPaises()
  }

  // Probablemente no se utilize más este método, ya que la lista es fija.
  // fun reset() { paises = mutableListOf<Pais>()}

  // Probablemente no se utilize más este método, ya que la lista es fija.
  // fun agregarPais(pais: Pais) = paises.add(pais)

  fun esPais(nombrePais: String): Boolean = paises.any { p -> p.nombre == nombrePais }

  fun obtenerPais(nombrePais: String): Pais = paises.find { p -> p.nombre == nombrePais }!!
/*
 * Para dos países en particular, cuyos nombres se envían por parámetro, se 
 * pide poder resolver las mismas consultas de la etapa anterior:
 *
 * Poder consultar si son limítrofes.
 */
  // Teniendo en cuenta la integridad de la relación limítrofe si verifico en un pais es suficiente.
  // Por otro lado, según la especificación del problema estos métodos pueden dar VERDADERO, FALSO o NULL
  // en el caso que alguno de los Strings no representen a algún pais válido.

  fun sonLimitrofes(paisA: String, paisB: String): Boolean =
    if (esPais(paisA) and esPais(paisB))
      obtenerPais(paisA).esLimitrofeDe(obtenerPais(paisB))
    else
      throw Exception("Algún pais es inválido.")
/*
 * Saber si necesitan traducción para poder dialogar
 */

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

/*  
 * Ojo, que se pide (a propósito) que los parámetros sean los nombres de los 
 * países y no los objetos que los representan - de hecho es lo único que 
 * cambia entre esta etapa y la anterior. Para este punto puede resultar un 
 * poco molesto, pero nos va a facilitar la etapa siguiente.
 */
/*  
 * Sobre el conjunto de todos los países:
 * 
 * Obtener los códigos ISO de los 5 países con mayor densidad poblacional.
 */
  fun codigosPaisesMasDensamentePoblados(): List<String> =
    paises.sortedByDescending { p->p.densidadPoblacional() }.take(5).map { p -> p.codigoIso3 }.orEmpty()

/*
 * Indicar el nombre del continente con más paises plurinacionales.
 */

  fun continenteConMasPaisesPlurinacionales(): String =
    paises.filter { p -> p.esPlurinacional() }.groupBy { p->p.continente }.maxByOrNull { c -> c.value.size }!!.key

/*
 * Conocer el promedio de densidad poblacional de los países insulares (o sea, 
 * aquellos países que son islas).
 */
  fun hayPaisesInsulares(): Boolean = paises.any { p -> p.esUnaIsla() }

  fun promedioDensidadPoblacionalPaisesInsulares(): Double {
    if (hayPaisesInsulares())
      return paises.filter{ p -> p.esUnaIsla() }.run {
        (this.sumOf { p->p.densidadPoblacional() } / this.size).toDouble()
      }
    else
      throw Exception("No hay países Insulares")
  }
 }
