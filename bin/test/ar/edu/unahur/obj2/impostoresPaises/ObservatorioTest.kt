import ar.edu.unahur.obj2.impostoresPaises.Observatorio
import ar.edu.unahur.obj2.impostoresPaises.Pais
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.engine.toTestResult
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.doubles.plusOrMinus
import io.kotest.matchers.shouldBe
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder

class ObservatorioTest : DescribeSpec ({

  Observatorio.reset()

  val argentina = Pais( "Argentina", "ARG",47000000, 2780400.0, "America",
    "ARS", 222.75, listOf("UNASUR", "MERCOSUR"), listOf("español","guarani","qom")
  )

  val brasil = Pais("Brasil","BRA",208388000,8515770.00,"America",
    "REA",5.41, listOf("UNASUR", "MERCOSUR"), listOf("portugues")
  )

  val chile = Pais("Chile","CHI",18430408,756950.00,"America",
    "CHI",975.71, listOf("UNASUR"), listOf("español","rapanui")
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


  describe("Test de Observatorio") {

    it("Son limitrofes") {
      Observatorio.sonLimitrofes("Argentina", "Brasil").shouldBeTrue()
      Observatorio.sonLimitrofes("Chile", "Brasil").shouldBeFalse()
      shouldThrow<Exception> { Observatorio.sonLimitrofes("Argentina", "Disneyland") }
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
      shouldThrow<Exception> { Observatorio.convieneIrDeComprasDesdeA("Brasil", "Disneyland") }
    }

    it("A cuanto equivale") {
      Observatorio.aCuantoEquivaleEn(20000.00, "Argentina", "Brasil").shouldBe(485.74 plusOrMinus 0.01)
      Observatorio.aCuantoEquivaleEn(485.74, "Brasil", "Argentina").shouldBe(20000.00 plusOrMinus 20.00)
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