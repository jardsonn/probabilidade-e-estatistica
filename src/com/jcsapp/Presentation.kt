package com.jcsapp

fun main() {
//    val data =
//        "15.60 18.75 14.60 15.80 14.35 13.90 17.50 17.55 13.80 14.20 19.05 15.35 15.20 19.45 15.95 16.50 16.30 15.25 15.05 19.10 15.20 16.22 17.75 18.40 15.25"
//    val operations = Operations(data)
//    val distribution = operations.getFrequencyDistribution(8)
//    val quartil = operations.getQuartil()
    println("INFORMAÇÕES")
    println("________________________________________________________________________________________")
    println("|Os dados devem ser digitados um por um ou colados tudo de uma vez.\t\t\t\t\t\t|\n|Caso os dados sejam colados, eles devem ser separados por vírgulas  ou por espaços.\t|\n|Quando finalizar de inserir os dados digite 's' para sair.\t\t\t\t\t\t\t\t|")
    println("________________________________________________________________________________________")
    var digitado: String?
    var dados = ""
    println("DIGITE OS DADOS: ")
    do {
        digitado = readLine()
        if (digitado != "s") dados += " ${digitado?.trim()}"
        if (digitado?.contains(" ") == true) break
    } while (digitado != "s")
    println("DIGITE A OPERAÇÃO (DISTRIBUIÇÃO DE FREQUÊNCIA (DF)  OU MEDIDAS DE POSIÇÃO (MP): ")
    val operacao = readLine() ?: return
    when {
        operacao.lowercase() == "df" -> {
            print("DIGITE O NÚMERO DE CLASSE: ")
            val numClass = readLine() ?: return
            println(Operations(dados.trim()).getFrequencyDistribution(numClass.toInt()))
        }
        operacao.lowercase() == "mp" -> {
            println(Operations(dados.trim()).getQuartil())
        }
    }

}
