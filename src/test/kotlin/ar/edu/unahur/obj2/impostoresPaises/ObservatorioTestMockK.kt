package ar.edu.unahur.obj2.impostoresPaises
import ar.edu.unahur.obj2.impostoresPaises.Observatorio
import ar.edu.unahur.obj2.impostoresPaises.api.*
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.doubles.plusOrMinus
import io.mockk.every
import io.mockk.mockk

class ObservatorioTestMockK : DescribeSpec ({


    // Observatorio.reset()

    // ¡para que haga el init Observatorio!


describe("Test de Observatorio") {

    val api = mockk<RestCountriesAPI>(relaxed = true)
    val observatorio = Observatorio(api)

    every { api.todosLosPaises() } returns listOf(
        Country("Argentina", "ARG","","America",47000000, 2780400.0, listOf("BRA", "CHI"),
            listOf(Language("Español"),Language("Qom")),listOf(RegionalBloc("","UNASUR"),RegionalBloc("", "MERCOSUR")),
            listOf(Currency("ARS"))),
        Country("Brasil", "BRA","","America",208388000, 8515770.00, listOf("ARG"),
            listOf(Language("Portugues")),listOf(RegionalBloc("","UNASUR"),RegionalBloc("", "MERCOSUR")),
            listOf(Currency("REA"))),
        Country("Chile", "CHI","","America",18430408, 756950.00, listOf("ARG"),
            listOf(Language("Español"),Language("Rapanui")),listOf(RegionalBloc("","UNASUR")),
            listOf(Currency("CHI"))),
        Country("Australia", "AUS","","Oceania",25900570, 7741220.0, listOf(),
            listOf(Language("Inglés")),listOf(RegionalBloc("","OTAN")),
            listOf(Currency("AUD"))),
        Country("Groenlandia", "GRL","","America",9876600, 2166086.0, listOf(),
            listOf(Language("Inglés"),Language("Danés")),listOf(RegionalBloc("","OTAN")),
            listOf(Currency("DKK"))),
        Country("Islandia", "ISL","","America",457050, 103000.0, listOf(),
            listOf(Language("Inglés"),Language("Islandés")),listOf(RegionalBloc("","OTAN")),
            listOf(Currency("ISK")))
        )

        /*
        it("API adapter") {
          Observatorio.paises.size.shouldBe(250)
          Observatorio.paises.map{n->n.nombre}.shouldBe("")
        }

        */
    it("Son limitrofes") {
            /*
            Observatorio.esPais("Argentina").shouldBeTrue()
            Observatorio.esPais("Brazil").shouldBeTrue()
            Observatorio.esPais("Chile").shouldBeTrue()
            Observatorio.obtenerPais("Argentina").paisesLimitrofes.shouldBe("Argentina")
            Observatorio.obtenerPais("Brazil").paisesLimitrofes.shouldBe("Brazil")
            Observatorio.obtenerPais("Chile").paisesLimitrofes.shouldBe("Chile")

             */

        observatorio.sonLimitrofes("Argentina", "Brasil").shouldBeTrue()
        observatorio.sonLimitrofes("Chile", "Brasil").shouldBeFalse()
        shouldThrow<Exception> { observatorio.sonLimitrofes("Argentina", "Disneyland") }
    }

    it("Necesitan traduccion") {
        observatorio.necesitanTraduccion("Argentina", "Brasil").shouldBeTrue()
        observatorio.necesitanTraduccion("Argentina", "Chile").shouldBeFalse()
        shouldThrow<Exception> { observatorio.necesitanTraduccion("Argentina", "Disneyland") }
    }

    it("Son potenciales aliados") {
        observatorio.sonPotencialesAliados("Argentina", "Brasil").shouldBe(false)
        observatorio.sonPotencialesAliados("Argentina", "Chile").shouldBe(true)
        shouldThrow<Exception> { observatorio.sonPotencialesAliados("Argentina", "Disneyland") }
    }

    /*
    it("Conviene ir de compras") {
        observatorio.convieneIrDeComprasDesdeA("Argentina", "Brazil").shouldBe(false)
        observatorio.convieneIrDeComprasDesdeA("Brazil", "Argentina").shouldBe(true)
        shouldThrow<Exception> { observatorio.convieneIrDeComprasDesdeA("Brazil", "Disneyland") }
    }

    it("A cuanto equivale") {
        observatorio.aCuantoEquivaleEn(20000.00, "Argentina", "Brazil").shouldBe(848.60 plusOrMinus 0.10)
        observatorio.aCuantoEquivaleEn(1485.00, "Brazil", "Argentina").shouldBe(35000.00 plusOrMinus 60.00)
        shouldThrow<Exception> { observatorio.aCuantoEquivaleEn(20000.00, "Argentina", "Disneyland") }
    }

*/

    it("Promedio densidad poblacional paises insulares") {
        //australia densidad 3 - groenlandia densidad 5 - islandia densidad 4
        observatorio.promedioDensidadPoblacionalPaisesInsulares().shouldBe(4.0)
    }


    it("Continente con mas paises plurinacionales") {
        observatorio.continenteConMasPaisesPlurinacionales().shouldBe("America")
    }

    it("Codigos ISO paises son mayor densidad poblacional") {
        observatorio.codigosPaisesMasDensamentePoblados().shouldContainExactlyInAnyOrder("CHI", "ARG", "BRA","GRL","ISL")
    }

    }
})