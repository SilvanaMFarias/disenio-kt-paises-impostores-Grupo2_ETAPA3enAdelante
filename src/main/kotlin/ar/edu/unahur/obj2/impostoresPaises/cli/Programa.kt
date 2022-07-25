package ar.edu.unahur.obj2.impostoresPaises.cli

import ar.edu.unahur.obj2.impostoresPaises.Observatorio
import ar.edu.unahur.obj2.impostoresPaises.Pais
import ar.edu.unahur.obj2.impostoresPaises.api.*

// El código de nuestro programa, que (eventualmente) interactuará con una persona real
object Programa {
  // Ambas son variables para poder cambiarlas en los tests
  var entradaSalida = Consola
  //var api = RestCountriesAPI()
  val apiCountry = RestCountriesAPI()
  val apiCurrency = CurrencyConverterAPI(apiKey = "294dc89cf3f803ab8787")


  fun iniciar() {
    Observatorio.setApis(apiCountry, apiCurrency)
    menu()
    /*entradaSalida.escribirLinea(AsciiArt.mundo)
    entradaSalida.escribirLinea("Hola, poné el nombre de un país y te mostramos algo de data")
    val pais = entradaSalida.leerLinea()
    checkNotNull(pais) { "Sin nombre no puedo hacer nada :(" }
    val paisesEncontrados = api.buscarPaisesPorNombre(pais)
    check(paisesEncontrados.isNotEmpty())
      { "No encontramos nada, fijate si lo escribiste bien" }
    paisesEncontrados.forEach {
      entradaSalida.escribirLinea(
        "${it.name} (${it.alpha3Code}) es un país de ${it.region}, con una población de ${it.population} habitantes.\n"
      )
    } */
  }

    fun menu(){
        do {
            entradaSalida.escribirLinea("Elija una de las opciones del menú:\n")
            entradaSalida.escribirLinea("1 - Obtener información sobre un país")
            entradaSalida.escribirLinea("2 - Mostrar cuál es el vecino más poblado de un país")
            entradaSalida.escribirLinea("3 - Verificar si dos países son limítrofes")
            entradaSalida.escribirLinea("4 - Verificar si dos países necesitan traducción")
            entradaSalida.escribirLinea("5 - Verificar si dos países son potenciales aliados")
            entradaSalida.escribirLinea("6 - Verificar si conviene ir de compras de un país a otro")
            entradaSalida.escribirLinea("7 - Obtener los códigos de los países más densamente poblados")
            entradaSalida.escribirLinea("8 - Obtener el continente con más países plurinacionales")
            entradaSalida.escribirLinea("9 - Obtener el promedio de densidad poblacional de los países insulares")
            entradaSalida.escribirLinea("10 - Obtener la densidad poblacional de un país")
            entradaSalida.escribirLinea("11 - Salir\n")
            entradaSalida.escribirLinea("Su elección: ")
            val respuesta = entradaSalida.leerLinea()

            when (respuesta) {
                "1" -> {  entradaSalida.escribirLinea("\nIngrese el país del cual desea obtener información:")
                    var paisT = entradaSalida.leerLinea()
                    checkNotNull(paisT) { "Debe ingresar un nombre de país" }
                    try{
                        var pais = Observatorio.obtenerPais(paisT)
                        entradaSalida.escribirLinea(
                        "\n${pais.nombre} (${pais.codigoIso3}) es un país de ${pais.continente}, con una población" +
                                " de ${pais.poblacion} habitantes y una superficie de ${pais.superficie} " + "kilómetros cuadrados."
                        )}
                    catch (e: Exception){
                        entradaSalida.escribirLinea("\nEl país ingresado no existe. Corrobore su escritura.")
                    }

                }

                "2" -> {  entradaSalida.escribirLinea("\nIngrese el país del cual desea conocer su vecino más poblado:")
                    var paisT = entradaSalida.leerLinea()
                    checkNotNull(paisT) { "Debe ingresar un nombre de país" }
                    try{
                        var pais = Observatorio.vecinoMasPoblado(paisT)
                        entradaSalida.escribirLinea(
                        "\nEl país vecino de ${paisT} más poblado es \"${pais.nombre}\", con una población" +
                                " de ${pais.poblacion} habitantes.")
                        }
                    catch (e: Exception){
                        entradaSalida.escribirLinea("\nEl país ingresado no existe. Corrobore su escritura.")
                    }
                }

                "3" -> {  entradaSalida.escribirLinea("\nIngrese los dos países para verificar si son limítrofes:\n")
                    entradaSalida.escribirLinea("Primer País:")
                    var primerPais = entradaSalida.leerLinea()
                    entradaSalida.escribirLinea("Segundo País:")
                    try{
                        var segundoPais = entradaSalida.leerLinea()
                        checkNotNull(primerPais) { "Debe ingresar un nombre para el primer país" }
                        checkNotNull(segundoPais) { "Debe ingresar un nombre para el segundo país" }
                        if (Observatorio.sonLimitrofes(primerPais,segundoPais))  {
                            entradaSalida.escribirLinea("\n${primerPais} y  ${segundoPais}, son limítrofes.")
                        }
                        else{
                            entradaSalida.escribirLinea("\n${primerPais} y ${segundoPais}, no son limítrofes.")
                        }
                    }
                    catch (e: Exception){
                        entradaSalida.escribirLinea("\nAlguno de los países ingresados no existe. Corrobore su escritura.")
                    }
                }

                "4" -> {  entradaSalida.escribirLinea("\nIngrese los dos países para verificar si necesitan traducción:\n")
                    entradaSalida.escribirLinea("Primer País:")
                    var primerPais = entradaSalida.leerLinea()
                    entradaSalida.escribirLinea("Segundo País:")
                    var segundoPais = entradaSalida.leerLinea()
                    checkNotNull(primerPais) { "Debe ingresar un nombre para el primer país" }
                    checkNotNull(segundoPais) { "Debe ingresar un nombre para el segundo país" }
                    try{
                        if (Observatorio.necesitanTraduccion(primerPais,segundoPais))  {
                            entradaSalida.escribirLinea("\n${primerPais} y  ${segundoPais}, necesitan traducción.")
                        }
                        else{
                            entradaSalida.escribirLinea("\n${primerPais} y ${segundoPais}, no necesitan traducción.")
                        }
                    }
                    catch (e: Exception){
                        entradaSalida.escribirLinea("\nAlguno de los países ingresados no existe. Corrobore su escritura.")
                    }
                }

                "5" -> {  entradaSalida.escribirLinea("\nIngrese los dos países para verificar si son potenciales aliados:\n")
                    entradaSalida.escribirLinea("Primer País:")
                    var primerPais = entradaSalida.leerLinea()
                    entradaSalida.escribirLinea("Segundo País:")
                    var segundoPais = entradaSalida.leerLinea()
                    checkNotNull(primerPais) { "Debe ingresar un nombre para el primer país" }
                    checkNotNull(segundoPais) { "Debe ingresar un nombre para el segundo país" }
                    try{
                        if (Observatorio.sonPotencialesAliados(primerPais,segundoPais))  {
                            entradaSalida.escribirLinea("\nLos países ${primerPais} y ${segundoPais}, son potenciales aliados.")
                        }
                        else{
                            entradaSalida.escribirLinea("\nLos países ${primerPais} y ${segundoPais}, no son potenciales aliados.")
                        }
                    }
                    catch (e: Exception){
                        entradaSalida.escribirLinea("\nAlguno de los países ingresados no existe. Corrobore su escritura.")
                    }
                }

                "6" -> {  entradaSalida.escribirLinea("\nIngrese los dos países para evaluar si conviene ir de compras de un país a otro:\n")
                    entradaSalida.escribirLinea("Primer País:")
                    var primerPais = entradaSalida.leerLinea()
                    entradaSalida.escribirLinea("Segundo País:")
                    var segundoPais = entradaSalida.leerLinea()
                    checkNotNull(primerPais) { "Debe ingresar un nombre para el primer país" }
                    checkNotNull(segundoPais) { "Debe ingresar un nombre para el segundo país" }
                    try {
                        if (Observatorio.convieneIrDeComprasDesdeA(primerPais,segundoPais))  {
                            entradaSalida.escribirLinea("\nConviene ir desde ${primerPais} a ${segundoPais} a realizar compras.")
                        }
                        else{
                            entradaSalida.escribirLinea("\nNo conviene ir desde ${primerPais} a ${segundoPais} a realizar compras.")
                        }
                    }
                    catch (e: Exception){
                        entradaSalida.escribirLinea("\nAlguno de los países ingresados no existe. Corrobore su escritura.")
                    }
                }

                "7" -> {  var codigos = Observatorio.codigosPaisesMasDensamentePoblados()
                    var pais: Pais
                    var densidad: Int
                    entradaSalida.escribirLinea("\nLos códigos de los países más densamente poblados son: ${codigos}. Correspondientes a: " )
                    codigos.forEach {
                        pais = Observatorio.paisConCodigo(it)
                        densidad = pais.densidadPoblacional()
                        entradaSalida.escribirLinea("${pais.nombre}, con una densidad poblacional de ${densidad} habitantes por km cuadrado")
                    }
                }

                "8" -> {  var continente = Observatorio.continenteConMasPaisesPlurinacionales()
                    entradaSalida.escribirLinea("\nEl continente son más países plurinacionales es: ${continente}.")
                }

                "9" -> {  var densidad = Observatorio.promedioDensidadPoblacionalPaisesInsulares()
                    entradaSalida.escribirLinea(
                        "\nEl promedio de densidad poblacional de los países insulares es: ${densidad}.")
                }

                "10" -> { entradaSalida.escribirLinea("\nIngrese el nombre del país del cual desea conocer su densidad poblacional:")
                    var nombrePais = entradaSalida.leerLinea()
                    checkNotNull(nombrePais) { "Debe ingresar un nombre de país" }
                    try{
                        var pais = Observatorio.obtenerPais(nombrePais)
                        var densidad = pais.densidadPoblacional()
                        entradaSalida.escribirLinea("\nLa densidad poblacional de ${nombrePais} es ${densidad}.")
                    }
                    catch (e: Exception){
                        entradaSalida.escribirLinea("\nEl país ingresado no existe. Corrobore su escritura.")
                    }
                }

                "11" -> entradaSalida.escribirLinea("\nHa salido")

                else -> { entradaSalida.escribirLinea("\nOpción Inválida")}

            }
            entradaSalida.escribirLinea("\nPresione la tecla ENTER para continuar")
            entradaSalida.leerLinea()

        } while (respuesta != "11")
    }
}
