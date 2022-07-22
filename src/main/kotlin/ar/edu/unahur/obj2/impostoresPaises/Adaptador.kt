package ar.edu.unahur.obj2.impostoresPaises
import ar.edu.unahur.obj2.impostoresPaises.api.Country
import ar.edu.unahur.obj2.impostoresPaises.api.RestCountriesAPI

interface interfaceAPI {

    fun todosLosPaises(): List<Pais>

    fun buscarPaisesPorNombre(nombre: String): List<Pais>

    fun paisConCodigo(codigoIso3: String): Pais
}

class AdaptadorAPI(val adaptee: RestCountriesAPI): interfaceAPI {

    override fun todosLosPaises(): MutableList<Pais> {
        val allCountries: List<Country> = adaptee.todosLosPaises()
        return adaptarCountryAPais(allCountries)
    }

    override fun buscarPaisesPorNombre(nombre: String): List<Pais> {
        val countries: List<Country> = adaptee.buscarPaisesPorNombre(nombre)
        return adaptarCountryAPais(countries)
    }

    override fun paisConCodigo(codigoIso3: String): Pais {
        val country: MutableList<Country> = mutableListOf()
        country.add(adaptee.paisConCodigo(codigoIso3))
        return adaptarCountryAPais(country)?.first()
    }

    fun adaptarCountryAPais(allCountries: List<Country>): MutableList<Pais> {
        val todosLosPaises: MutableList<Pais> = mutableListOf()
        var nombre: String
        var codigoIso3: String
        var poblacion: Long
        var superficie: Double
        var continente: String
        var codigoMoneda: String
        var cotizacionDolar: Double
        var bloquesRegionales: List<String>
        var idiomasOficiales: List<String>
        var paisesLimitrofes: List<String>
        var pais: Pais

        if (allCountries.isNotEmpty()) {
            allCountries.forEach {
                nombre = it.name
                codigoIso3 = it.alpha3Code
                poblacion = it.population
                superficie = it.area ?: it.population.toDouble()
                continente = it.region
                codigoMoneda = if (it.currencies!!.isNotEmpty()) it.currencies!!.first().code else "USD"
                //cotizacionDolar=0.00
                bloquesRegionales = it.regionalBlocs!!.map { b -> b.name }
                idiomasOficiales = it.languages.map { b -> b.name }
                paisesLimitrofes = it.borders.orEmpty()

                pais =
                    Pais(nombre, codigoIso3, poblacion, superficie, continente, codigoMoneda, bloquesRegionales, idiomasOficiales)
                pais.paisesLimitrofes.addAll(paisesLimitrofes)

                todosLosPaises.add(pais)
            }
        }
        return todosLosPaises
    }

}
