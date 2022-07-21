import ar.edu.unahur.obj2.impostoresPaises.Observatorio
import ar.edu.unahur.obj2.impostoresPaises.Pais
import ar.edu.unahur.obj2.impostoresPaises.*
import ar.edu.unahur.obj2.impostoresPaises.api.Country
import ar.edu.unahur.obj2.impostoresPaises.api.CurrencyConverterAPI
import ar.edu.unahur.obj2.impostoresPaises.api.RestCountriesAPI
import io.kotest.core.spec.style.DescribeSpec

// Algunos ejemplos para que jueguen un poco
// con lo que devuelve la API

val api = RestCountriesAPI()
val adapt = AdaptadorAPI(api)
adapt.adaptee.todosLosPaises()

val allCountries: List<Country> = adapt.adaptee.todosLosPaises()
allCountries
allCountries.size
val todosLosPaises: MutableList<Pais> = mutableListOf()
var nombre: String = ""
var codigoIso3: String =""
var poblacion: Int =0
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
    poblacion = it.population.toInt()//Redefinir a poblacion como long,
    superficie = 0.00//it.area ?: it.population.toDouble()-Redefinir superficie como long,
    continente = it.region
    codigoMoneda= "1"//if (it.currencies?.first()?.code.orEmpty().isEmpty()) it.currencies!!.first().code else "USD"
    cotizacionDolar=0.00
    bloquesRegionales= it.regionalBlocs!!.map{ b->b.name }
    idiomasOficiales= it.languages.map{ b->b.name }
    paisesLimitrofes=  mutableListOf()


    val pais = Pais(nombre, codigoIso3, poblacion, superficie, continente, codigoMoneda, cotizacionDolar, bloquesRegionales, idiomasOficiales)
    pais.paisesLimitrofes.addAll(paisesLimitrofes)


    todosLosPaises.add(pais)
}

todosLosPaises.map{n->n.nombre}




