package ar.edu.unahur.obj2.impostoresPaises
import ar.edu.unahur.obj2.impostoresPaises.api.Country
import ar.edu.unahur.obj2.impostoresPaises.api.CurrencyConverterAPI
import ar.edu.unahur.obj2.impostoresPaises.api.RestCountriesAPI

interface interfaceAPI {

    fun todosLosPaises(): List<Pais>

    fun buscarPaisesPorNombre(nombre: String): List<Pais>

    fun paisConCodigo(codigoIso3: String): Pais
}

class AdaptadorAPI(val adaptee: RestCountriesAPI, val apiCurrency: CurrencyConverterAPI): interfaceAPI {

    override fun todosLosPaises(): List<Pais> =
        adaptarCountryAPais(adaptee.todosLosPaises())

    override fun buscarPaisesPorNombre(nombre: String): List<Pais> =
        adaptarCountryAPais(adaptee.buscarPaisesPorNombre(nombre))

    override fun paisConCodigo(codigoIso3: String): Pais =
        adaptarCountryAPais(listOf(adaptee.paisConCodigo(codigoIso3))).first()

    fun adaptarCountryAPais(allCountries: List<Country>): MutableList<Pais> {
        val todosLosPaises: MutableList<Pais> = mutableListOf()
        var codigoMoneda: String
        var pais: Pais
        var limitrofes : MutableList<Pair<String,List<String>>> = mutableListOf()

        allCountries.forEach {
            codigoMoneda = if (it.currencies!!.isNotEmpty()) it.currencies!!.first().code else "USD"

            pais = Pais(
                it.name,
                it.alpha3Code,
                it.population,
                it.area ?: it.population.toDouble(),
                it.region,
                codigoMoneda,
                0.00,//*apiCurrency.convertirDolarA(codigoMoneda)!!,*/
                it.regionalBlocs!!.map { b -> b.name },
                it.languages.map { b -> b.name }
            )
            limitrofes.add(Pair(it.name, it.borders.orEmpty()))

            todosLosPaises.add(pais)
        }
        // La relacion con los objetos paises en limitrofes debe realizarse una vez que se
        // crearon todos los paises, por eso se utiliza la lista de Pares (PAIS,LIMITROFES)
        // para luego vincularlos.
        limitrofes.forEach {
            todosLosPaises.find { p -> p.nombre == it.first }!!.paisesLimitrofes.addAll(
                todosLosPaises.filter { p -> it.second.contains(p.codigoIso3) }
            )
        }
        return todosLosPaises
    }
}