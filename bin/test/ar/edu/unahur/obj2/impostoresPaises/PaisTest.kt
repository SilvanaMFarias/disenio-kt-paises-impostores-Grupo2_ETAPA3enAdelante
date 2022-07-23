package ar.edu.unahur.obj2.impostoresPaises

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.engine.toTestResult
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.doubles.plusOrMinus
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.internal.artificialFrame

class PaisTest : DescribeSpec ({

  val argentina = Pais( "Argentina", "ARG",47000000, 2780400.0, "America",
    "ARS", 222.75, listOf("UNASUR", "MERCOSUR"), listOf("español","guarani","qom")
  )

  val brasil = Pais("Brasil","BRA",208388000,8515770.00,"America",
    "REA",5.41, listOf("UNASUR", "MERCOSUR"), listOf("portugues","español")
  )

  val chile = Pais("Chile","CHI",18430408,756950.00,"America",
    "CHI",975.71, listOf("UNASUR"), listOf("español","rapanui")
  )

  val coreaDelNorte = Pais("Corea del Norte","CDN",25026772,120540.00,"Asia",
    "WON",0.00905, listOf(), listOf("coreano")
  )

  val coreaDelSur = Pais("Corea del Sur","CDs",51709000,100339.00,"Asia",
    "WON",0.00903, listOf(), listOf("coreano")
  )

  val islandia = Pais(
    "Islandia","ISL",457050,103000.0,"Europa",
    "ISK", 1.23, listOf("OTAN"), listOf("ingles","islandes")
  )

  argentina.agregarPaisLimitrofeMutuo(chile)
  argentina.agregarPaisLimitrofeMutuo(brasil)
  coreaDelNorte.agregarPaisLimitrofeMutuo(coreaDelSur)

  describe("Test de Pais") {

    it("Es plurinacional") {
      argentina.esPlurinacional().shouldBeTrue()
      brasil.esPlurinacional().shouldBeTrue()
      chile.esPlurinacional().shouldBeTrue()
      coreaDelNorte.esPlurinacional().shouldBeFalse()
      coreaDelSur.esPlurinacional().shouldBeFalse()
    }

    it("Es una isla") {
      argentina.esUnaIsla().shouldBeFalse()
      brasil.esUnaIsla().shouldBeFalse()
      chile.esUnaIsla().shouldBeFalse()
      coreaDelNorte.esPlurinacional().shouldBeFalse()
      islandia.esUnaIsla().shouldBeTrue()
    }

    it("Densidad poblacional") {
      argentina.densidadPoblacional().shouldBe(17)
      brasil.densidadPoblacional().shouldBe(24)
      chile.densidadPoblacional().shouldBe(24)
      coreaDelNorte.densidadPoblacional().shouldBe(208)
      coreaDelSur.densidadPoblacional().shouldBe(515)
      islandia.densidadPoblacional().shouldBe(4)
    }

    it("Vecino mas poblado") {
      argentina.vecinoMasPoblado().shouldBe(brasil)
      brasil.vecinoMasPoblado().shouldBe(brasil)
      chile.vecinoMasPoblado().shouldBe(argentina)
      coreaDelNorte.vecinoMasPoblado().shouldBe(coreaDelSur)
      coreaDelSur.vecinoMasPoblado().shouldBe(coreaDelSur)
      islandia.vecinoMasPoblado().shouldBe(islandia)
    }

    it("Es limitrofe de") {
      argentina.esLimitrofeDe(chile).shouldBeTrue()
      argentina.esLimitrofeDe(brasil).shouldBeTrue()
      argentina.esLimitrofeDe(coreaDelSur).shouldBeFalse()
      brasil.esLimitrofeDe(chile).shouldBeFalse()
      islandia.esLimitrofeDe(argentina).shouldBeFalse()
      coreaDelSur.esLimitrofeDe(coreaDelNorte).shouldBeTrue()
    }

    it("Necesita traduccion con") {
      argentina.necesitaTraduccionCon(brasil).shouldBeFalse()
      chile.necesitaTraduccionCon(islandia).shouldBeTrue()
      brasil.necesitaTraduccionCon(chile).shouldBeFalse()
      islandia.necesitaTraduccionCon(coreaDelSur).shouldBeTrue()
      coreaDelNorte.necesitaTraduccionCon(coreaDelSur).shouldBeFalse()
    }

    it("Es potencial aliado de") {
      argentina.esPotencialAliadoDe(chile).shouldBeTrue()
      argentina.esPotencialAliadoDe(brasil).shouldBeTrue()
      chile.esPotencialAliadoDe(brasil).shouldBeTrue()
      coreaDelSur.esPotencialAliadoDe(islandia).shouldBeFalse()
      coreaDelNorte.esPotencialAliadoDe(coreaDelSur).shouldBeFalse()
    }

    it("Conviene ir de compras a") {
      argentina.convieneIrDeComprasA(brasil).shouldBeFalse()
      argentina.convieneIrDeComprasA(chile).shouldBeTrue()
      brasil.convieneIrDeComprasA(argentina).shouldBeTrue()
      chile.convieneIrDeComprasA(argentina).shouldBeFalse()
      coreaDelSur.convieneIrDeComprasA(coreaDelNorte).shouldBeFalse()
      //coreaDelNorte.convieneIrDeComprasA(coreaDelSur).shouldBeTrue()
    }

    it("A cuanto equivale") {
      argentina.aCuantoEquivaleEn(20000.00, brasil).shouldBe(485.74 plusOrMinus 0.01)
      brasil.aCuantoEquivaleEn(485.74, argentina).shouldBe(20000.00 plusOrMinus 20.00)
      argentina.aCuantoEquivaleEn(100000.00, chile).shouldBe(438029.18 plusOrMinus 0.01)
    }

  }
})