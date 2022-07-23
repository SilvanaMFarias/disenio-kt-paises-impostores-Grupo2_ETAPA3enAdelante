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
        // En esta etapa pasamos el Singleton a una clase para mayor claridad en el código
        //
        // (COMPLETAR CON LO QUE SE COMPLICO PARA TOMAR ESTA DECISION)
        //
        val observatorio = Observatorio(apiCountry, apiCurrency)

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
            observatorio.obtenerPais("Argentina").paisesLimitrofes.shouldContainExactlyInAnyOrder("BRA","CHI")
            observatorio.obtenerPais("Brasil").paisesLimitrofes.shouldContainExactlyInAnyOrder("ARG")
            observatorio.obtenerPais("Chile").paisesLimitrofes.shouldContainExactlyInAnyOrder("ARG")

            observatorio.obtenerPais("Argentina").paisesLimitrofes2.shouldContainExactlyInAnyOrder("BRA","CHI")
            observatorio.obtenerPais("Brasil").paisesLimitrofes2.shouldContainExactlyInAnyOrder("ARG")
            observatorio.obtenerPais("Chile").paisesLimitrofes2.shouldContainExactlyInAnyOrder("ARG")


            observatorio.sonLimitrofes("Argentina", "Brasil").shouldBeTrue()
            observatorio.sonLimitrofes("Chile", "Brasil").shouldBeFalse()
            shouldThrow<Exception> { observatorio.sonLimitrofes("Argentina", "Disneyland") }
        }

        it("Vecino mas poblado") {
            observatorio.vecinoMasPoblado("Argentina").nombre.shouldBe("Brasil")
            observatorio.vecinoMasPoblado("Brasil").nombre.shouldBe("Brasil")
            observatorio.vecinoMasPoblado("Chile").nombre.shouldBe("Argentina")
            observatorio.vecinoMasPoblado("Australia").nombre.shouldBe("Australia")
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

        it("Conviene ir de compras") {
            observatorio.convieneIrDeComprasDesdeA("Argentina", "Brasil").shouldBe(false)
            observatorio.convieneIrDeComprasDesdeA("Brasil", "Argentina").shouldBe(true)
            shouldThrow<Exception> { observatorio.convieneIrDeComprasDesdeA("Brazil", "Disneyland") }
        }

        it("A cuanto equivale") {
            observatorio.aCuantoEquivaleEn(20000.00, "Argentina", "Brasil").shouldBe(848.60 plusOrMinus 10.00)
            observatorio.aCuantoEquivaleEn(1485.00, "Brasil", "Argentina").shouldBe(34800.00 plusOrMinus 100.00)
            shouldThrow<Exception> { observatorio.aCuantoEquivaleEn(20000.00, "Argentina", "Disneyland") }
        }

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