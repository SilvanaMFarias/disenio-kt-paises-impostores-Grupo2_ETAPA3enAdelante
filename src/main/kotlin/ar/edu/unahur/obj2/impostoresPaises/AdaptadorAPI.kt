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

        allCountries.forEach {
            codigoMoneda = if (it.currencies!!.isNotEmpty()) it.currencies!!.first().code else "USD"

            pais = Pais(
                it.name,
                it.alpha3Code,
                it.population,
                it.area ?: it.population.toDouble(),
                it.region,
                codigoMoneda,
                apiCurrency.convertirDolarA(codigoMoneda)!!,
                it.regionalBlocs!!.map { b -> b.name },
                it.languages.map { b -> b.name }
            )
            pais.paisesLimitrofes.addAll(it.borders.orEmpty())

            todosLosPaises.add(pais)
        }
        return todosLosPaises
    }
}