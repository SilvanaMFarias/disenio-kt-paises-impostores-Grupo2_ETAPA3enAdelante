import ar.edu.unahur.obj2.impostoresPaises.Pais
import ar.edu.unahur.obj2.impostoresPaises.*
import ar.edu.unahur.obj2.impostoresPaises.api.CurrencyConverterAPI
import ar.edu.unahur.obj2.impostoresPaises.api.RestCountriesAPI
import io.kotest.core.spec.style.DescribeSpec

// Algunos ejemplos para que jueguen un poco
// con lo que devuelve la API

val api = RestCountriesAPI()

api.buscarPaisesPorNombre("argentina")
api.paisConCodigo("CHL")
api.todosLosPaises()
/*
val currencyApi = CurrencyConverterAPI("poné acá la API key")
// PEN es el código del sol peruano
currencyApi.convertirDolarA("PEN")*/

val pais = api.buscarPaisesPorNombre("argentina")

val nombre = pais.first().name
val codigoIso3 = pais.first().alpha3Code
var poblacion = pais.first().population
var superficie = pais.first().area
val continente = pais.first().region
var codigoMoneda= pais.first().currencies?.first()?.code
//var cotizacionDolar=
val bloquesRegionales= pais.first().regionalBlocs
val idiomasOficiales= pais.first().languages
val paisesLimitrofes= pais.first().borders

nombre
codigoIso3
poblacion
superficie
continente
codigoMoneda
bloquesRegionales
idiomasOficiales
paisesLimitrofes

/*
val argentina = Pais( "Argentina", "ARG",47000000, 2780400.0, "America",
    "ARS", 135.6, listOf("UNASUR", "MERCOSUR"), listOf("español","guarani","qom")
)

val brasil = Pais("Brasil","BRA",208388000,8515770.00,"America",
    "REA",5.41, listOf("UNASUR", "MERCOSUR"),
    listOf("portugues","español")
)

val chile = Pais("Chile","CHI",18430408,756950.00,"America",
    "CHI",0.00095, listOf("UNASUR"),
    listOf("español","rapanui")
)


val australia = Pais(
    "Australia","AUS",25900570,7741220.0,"Oceania",
    "AUD", 1.23, listOf("OTAN"), listOf("ingles")
)
val groenlandia = Pais(
    "Groenlandia","GRL",9876600,2166086.0,"America",
    "DKK", 1.23, listOf("OTAN"), listOf("ingles", "danes")
)
val islandia = Pais(
    "Islandia","ISL",457050,103000.0,"Europa",
    "ISK", 1.23, listOf("OTAN"), listOf("ingles","islandes")
)

argentina.agregarPaisLimitrofeMutuo(chile)
argentina.agregarPaisLimitrofeMutuo(brasil)
Observatorio.agregarPais(argentina)
Observatorio.agregarPais(brasil)
Observatorio.agregarPais(chile)
Observatorio.agregarPais(australia)
Observatorio.agregarPais(groenlandia)
Observatorio.agregarPais(islandia)

Observatorio.paises

Observatorio.paises.map { p->p.densidadPoblacional() }.sorted().take(2)
Observatorio.paises.filter { p->p.densidadPoblacional() == 24}.map{p->p.codigoIso3}

argentina.paisesLimitrofes
chile.paisesLimitrofes
brasil.paisesLimitrofes


Observatorio.paises.any { p -> p.esUnaIsla() }
Observatorio.paises.sortedBy { p->p.densidadPoblacional() }.map{p->p.codigoIso3}
Observatorio.paises.sortedByDescending { p->p.densidadPoblacional() }.take(5).map { p -> p.codigoIso3 }.orEmpty()

Observatorio.paises.filter{ p -> p.esUnaIsla() }.run {
    (this.sumOf { p->p.densidadPoblacional() } / this.size).toDouble()
}
Observatorio.codigosPaisesMasDensamentePoblados()
Observatorio.paises.sortedByDescending { p->p.densidadPoblacional() }
Observatorio.paises.sortedByDescending { p->p.densidadPoblacional() }.take(5)
Observatorio.paises.sortedByDescending { p->p.densidadPoblacional() }.take(5).map { p -> p.codigoIso3 }.toSet()
Observatorio.paises.map { p->p.nombre }

Observatorio.paises.toSet().toList().sortedByDescending { p->p.densidadPoblacional() }.map { p -> p.codigoIso3 }.orEmpty()*/