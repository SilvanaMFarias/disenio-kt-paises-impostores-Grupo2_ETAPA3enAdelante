import ar.edu.unahur.obj2.impostoresPaises.Observatorio
import ar.edu.unahur.obj2.impostoresPaises.Pais
import ar.edu.unahur.obj2.impostoresPaises.*
import ar.edu.unahur.obj2.impostoresPaises.api.*
import io.kotest.core.spec.style.DescribeSpec

// Algunos ejemplos para que jueguen un poco
// con lo que devuelve la API
val apiCurrency = CurrencyConverterAPI(apiKey = "294dc89cf3f803ab8787")
var cotizacionPesoArgentino = apiCurrency.convertirDolarA("ARS")

cotizacionPesoArgentino

val api = RestCountriesAPI()
val adapt = AdaptadorAPI(api)
adapt.adaptee.todosLosPaises()

val allCountries: List<Country> = adapt.adaptee.todosLosPaises()
allCountries
allCountries.size
val todosLosPaises: MutableList<Pais> = mutableListOf()
var nombre: String = ""
var codigoIso3: String =""
var poblacion: Long =0
var superficie: Double =0.00
var continente: String =""
var codigoMoneda: String =""
var cotizacionDolar: Double =0.00
var bloquesRegionales: List<String> = listOf()
var idiomasOficiales: List<String> = listOf()
var paisesLimitrofes: MutableList<String> = mutableListOf()


allCountries.forEach {
    nombre = it.name
    codigoIso3 = it.alpha3Code
    poblacion = it.population//Redefinir a poblacion como long,
    superficie = it.area ?: it.population.toDouble()
    continente = it.region
    codigoMoneda= "USD"//it.currencies.first(). ?: "USD"
    bloquesRegionales= it.regionalBlocs!!.map{ b->b.name }
    idiomasOficiales= it.languages.map{ b->b.name }
    paisesLimitrofes=  mutableListOf()


    val pais = Pais(nombre, codigoIso3, poblacion, superficie, continente, codigoMoneda, bloquesRegionales, idiomasOficiales)
    pais.paisesLimitrofes.addAll(paisesLimitrofes)


    todosLosPaises.add(pais)
}

//todosLosPaises.map{n->n.nombre}
val argentina = allCountries.filter{p -> p.name == "Argentina"}.first()
argentina
val brasil = allCountries.filter{p -> p.name == "Brazil"}.first()
brasil
val chile = allCountries.filter{p -> p.name == "Chile"}.first()
chile


