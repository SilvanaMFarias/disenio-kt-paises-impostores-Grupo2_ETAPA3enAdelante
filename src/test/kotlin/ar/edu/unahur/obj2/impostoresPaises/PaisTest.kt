package ar.edu.unahur.obj2.impostoresPaises

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.doubles.plusOrMinus
import io.kotest.matchers.shouldBe

class PaisTest : DescribeSpec ({

    val argentina = Pais( "Argentina", "ARG",47000000, 2780400.0, "America",
        "ARS",  129.0, listOf("UNASUR", "MERCOSUR"), listOf("español","guarani","qom")
    )

    val brasil = Pais("Brasil","BRA",208388000,8515770.00,"America",
        "BRL", 5.5, listOf("UNASUR", "MERCOSUR"), listOf("portugues","español")
    )

    val chile = Pais("Chile","CHI",18430408,756950.00,"America",
        "CLP", 925.0, listOf("UNASUR"), listOf("español","rapanui")
    )

    val coreaDelNorte = Pais("Corea del Norte","CDN",25026772,120540.00,"Asia",
        "KPW", 10.0, listOf(), listOf("coreano")
    )

    val coreaDelSur = Pais("Corea del Sur","CDs",51709000,100339.00,"Asia",
        "KRW", 1.0, listOf(), listOf("coreano")
    )

    val islandia = Pais(
        "Islandia","ISL",457050,103000.0,"Europa",
        "ISK",  137.0, listOf("OTAN"), listOf("ingles","islandes")
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
            coreaDelNorte.esUnaIsla().shouldBeFalse()
            islandia.esUnaIsla().shouldBeTrue()
        }

        it("Vecino mas poblado") {
            argentina.vecinoMasPoblado().nombre.shouldBe("Brasil")
            brasil.vecinoMasPoblado().nombre.shouldBe("Brasil")
            chile.vecinoMasPoblado().nombre.shouldBe("Argentina")
            islandia.vecinoMasPoblado().nombre.shouldBe("Islandia")
        }

        it("Densidad poblacional") {
            argentina.densidadPoblacional().shouldBe(17)
            brasil.densidadPoblacional().shouldBe(24)
            chile.densidadPoblacional().shouldBe(24)
            coreaDelNorte.densidadPoblacional().shouldBe(208)
            coreaDelSur.densidadPoblacional().shouldBe(515)
            islandia.densidadPoblacional().shouldBe(4)
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
            coreaDelSur.convieneIrDeComprasA(coreaDelNorte).shouldBeTrue()
            coreaDelNorte.convieneIrDeComprasA(coreaDelSur).shouldBeFalse()
        }

        it("A cuanto equivale") {
            argentina.aCuantoEquivaleEn(20000.00, brasil).shouldBe(850.00 plusOrMinus 5.00)
            brasil.aCuantoEquivaleEn(1485.00, argentina).shouldBe(34800.00 plusOrMinus 50.00)
            argentina.aCuantoEquivaleEn(100000.00, chile).shouldBe(717000.00 plusOrMinus 100.00)
        }
    }
})