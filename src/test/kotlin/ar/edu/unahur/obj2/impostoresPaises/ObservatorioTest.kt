package ar.edu.unahur.obj2.impostoresPaises
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

class ObservatorioTest : DescribeSpec ({

    describe("Test de Observatorio") {

        val apiCountry = mockk<RestCountriesAPI>(relaxed = true)
        val apiCurrency = mockk<CurrencyConverterAPI>(relaxed = true)

        Observatorio.setApis(apiCountry, apiCurrency)

        // Mock de la apiCurrency
        every { apiCurrency.convertirDolarA("ARS") } returns 129.0
        every { apiCurrency.convertirDolarA("REA") } returns 5.5
        every { apiCurrency.convertirDolarA("CHI") } returns 925.0
        every { apiCurrency.convertirDolarA("AUD") } returns 32.0
        every { apiCurrency.convertirDolarA("DKK") } returns 1.44
        every { apiCurrency.convertirDolarA("ISK") } returns 137.0

        // Mock de la apiCountry
        every { apiCountry.todosLosPaises() } returns listOf(
            Country("Argentina", "ARG", "", "America", 47000000, 2780400.0,
                listOf("BRA", "CHI"), listOf(Language("Español"), Language("Qom")),
                listOf(RegionalBloc("","UNASUR"), RegionalBloc("", "MERCOSUR")),
                listOf(Currency("ARS"))
            ),
            Country("Brasil", "BRA","","America",208388000, 8515770.00,
                listOf("ARG"), listOf(Language("Portugues")),
                listOf(RegionalBloc("","UNASUR"), RegionalBloc("", "MERCOSUR")),
                listOf(Currency("REA"))
            ),
            Country("Chile", "CHI","","America",18430408, 756950.00,
                listOf("ARG"), listOf(Language("Español"),Language("Rapanui")),
                listOf(RegionalBloc("","UNASUR")),
                listOf(Currency("CHI"))
            ),
            Country("Australia", "AUS","","Oceania",25900570, 7741220.0,
                listOf(), listOf(Language("Inglés")),
                listOf(RegionalBloc("","OTAN")),
                listOf(Currency("AUD"))),
            Country("Groenlandia", "GRL","","America",9876600, 2166086.0,
                listOf(), listOf(Language("Inglés"), Language("Danés")),
                listOf(RegionalBloc("","OTAN")),
                listOf(Currency("DKK"))),
            Country("Islandia", "ISL","","America",457050, 103000.0,
                listOf(), listOf(Language("Inglés"), Language("Islandés")),
                listOf(RegionalBloc("","OTAN")),
                listOf(Currency("ISK")))
            )

        every { apiCountry.paisConCodigo("ARG") } returns
          Country("Argentina", "ARG", "", "America", 47000000, 2780400.0,
            listOf("BRA", "CHI"), listOf(Language("Español"), Language("Qom")),
            listOf(RegionalBloc("","UNASUR"), RegionalBloc("", "MERCOSUR")),
            listOf(Currency("ARS"))
          )

        every { apiCountry.paisConCodigo("BRA") } returns
          Country("Brasil", "BRA","","America",208388000, 8515770.00,
              listOf("ARG"), listOf(Language("Portugues")),
              listOf(RegionalBloc("","UNASUR"), RegionalBloc("", "MERCOSUR")),
              listOf(Currency("REA"))
          )

        every { apiCountry.paisConCodigo("CHI") } returns
          Country("Chile", "CHI","","America",18430408, 756950.00,
              listOf("ARG"), listOf(Language("Español"),Language("Rapanui")),
              listOf(RegionalBloc("","UNASUR")),
              listOf(Currency("CHI"))
          )

        it("Son limitrofes") {
            Observatorio.sonLimitrofes("Argentina", "Brasil").shouldBeTrue()
            Observatorio.sonLimitrofes("Chile", "Brasil").shouldBeFalse()
            shouldThrow<Exception> { Observatorio.sonLimitrofes("Argentina", "Disneyland") }
        }

        it("Vecino mas poblado") {
            Observatorio.vecinoMasPoblado("Argentina").nombre.shouldBe("Brasil")
            Observatorio.vecinoMasPoblado("Brasil").nombre.shouldBe("Brasil")
            Observatorio.vecinoMasPoblado("Chile").nombre.shouldBe("Argentina")
            Observatorio.vecinoMasPoblado("Australia").nombre.shouldBe("Australia")
        }

        it("Necesitan traduccion") {
            Observatorio.necesitanTraduccion("Argentina", "Brasil").shouldBeTrue()
            Observatorio.necesitanTraduccion("Argentina", "Chile").shouldBeFalse()
            shouldThrow<Exception> { Observatorio.necesitanTraduccion("Argentina", "Disneyland") }
        }

        it("Son potenciales aliados") {
            Observatorio.sonPotencialesAliados("Argentina", "Brasil").shouldBe(false)
            Observatorio.sonPotencialesAliados("Argentina", "Chile").shouldBe(true)
            shouldThrow<Exception> { Observatorio.sonPotencialesAliados("Argentina", "Disneyland") }
        }

        it("Conviene ir de compras") {
            Observatorio.convieneIrDeComprasDesdeA("Argentina", "Brasil").shouldBe(false)
            Observatorio.convieneIrDeComprasDesdeA("Brasil", "Argentina").shouldBe(true)
            shouldThrow<Exception> { Observatorio.convieneIrDeComprasDesdeA("Brazil", "Disneyland") }
        }

        it("A cuanto equivale") {
            Observatorio.aCuantoEquivaleEn(20000.00, "Argentina", "Brasil").shouldBe(848.60 plusOrMinus 10.00)
            Observatorio.aCuantoEquivaleEn(1485.00, "Brasil", "Argentina").shouldBe(34800.00 plusOrMinus 100.00)
            shouldThrow<Exception> { Observatorio.aCuantoEquivaleEn(20000.00, "Argentina", "Disneyland") }
        }

        it("Promedio densidad poblacional paises insulares") {
            //australia densidad 3 - groenlandia densidad 5 - islandia densidad 4
            Observatorio.promedioDensidadPoblacionalPaisesInsulares().shouldBe(4.0)
        }

        it("Continente con mas paises plurinacionales") {
            Observatorio.continenteConMasPaisesPlurinacionales().shouldBe("America")
        }

        it("Codigos ISO paises son mayor densidad poblacional") {
            Observatorio.codigosPaisesMasDensamentePoblados().shouldContainExactlyInAnyOrder("CHI", "ARG", "BRA","GRL","ISL")
        }
    }
})