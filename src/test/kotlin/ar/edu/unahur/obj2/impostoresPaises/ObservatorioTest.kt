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

  // Observatorio.reset()
  /*
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
  */
  // ¡para que haga el init Observatorio!
  Observatorio

  describe("Test de Observatorio") {
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
      Observatorio.sonLimitrofes("Argentina", "Brazil").shouldBeTrue()
      Observatorio.sonLimitrofes("Chile", "Brazil").shouldBeFalse()
      shouldThrow<Exception> { Observatorio.sonLimitrofes("Argentina", "Disneyland") }
    }

    it("Necesitan traduccion") {
      Observatorio.necesitanTraduccion("Argentina", "Brazil").shouldBeTrue()
      Observatorio.necesitanTraduccion("Argentina", "Chile").shouldBeFalse()
      shouldThrow<Exception> { Observatorio.necesitanTraduccion("Argentina", "Disneyland") }
    }

    it("Son potenciales aliados") {
      Observatorio.sonPotencialesAliados("Argentina", "Brazil").shouldBe(false)
      Observatorio.sonPotencialesAliados("Argentina", "Chile").shouldBe(true)
      shouldThrow<Exception> { Observatorio.sonPotencialesAliados("Argentina", "Disneyland") }
    }

    it("Conviene ir de compras") {
      Observatorio.convieneIrDeComprasDesdeA("Argentina", "Brazil").shouldBe(false)
      Observatorio.convieneIrDeComprasDesdeA("Brazil", "Argentina").shouldBe(true)
      shouldThrow<Exception> { Observatorio.convieneIrDeComprasDesdeA("Brazil", "Disneyland") }
    }

    it("A cuanto equivale") {
      Observatorio.aCuantoEquivaleEn(20000.00, "Argentina", "Brazil").shouldBe(848.60 plusOrMinus 0.10)
      Observatorio.aCuantoEquivaleEn(1485.00, "Brazil", "Argentina").shouldBe(35000.00 plusOrMinus 60.00)
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