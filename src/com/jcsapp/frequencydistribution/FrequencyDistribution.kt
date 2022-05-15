package com.jcsapp.frequencydistribution

data class FrequencyDistribution(
    val numberOfClasses: Int,
    val data: DoubleArray,
    val frequency: ArrayList<Int>,
    val midpoints: ArrayList<Double>,
    val relativeFrequency: ArrayList<Double>,
    val cumulativeFrequency: ArrayList<Double>,
    val minimumValue: Int,
    val maximumValue: Int,
    val biggerFrequency: Pair<String, Int>,
    val lowerFrequency: Pair<String, Int>,
    val totalBreadth: Double,
    val classBreadth: Int,
    val classes: ArrayList<Pair<Double, Double>>,
    val totalSize: Int,
) {

    override fun toString(): String {
        var table = "DADOS:  ${data.map { it }}\n" +
                "NÚMERO DE CLASSES: $numberOfClasses\n" +
                "TAMANHO TOTAL: $totalSize\n" +
                "VALOR MÁXIMO: $maximumValue\n" +
                "VALOR MÍNIMO: $minimumValue\n" +
                "AMPLITUDE: $totalBreadth\n" +
                "AMPLITUDE DA CLASSE: $classBreadth\n" +
                "MAIOR FREQUÊNCIA: $biggerFrequency\n" +
                "MENOR FREQUÊNCIA: $lowerFrequency\n"
        table += " ___________________________________________________________________________________\n"
        for (i in 0 until numberOfClasses) {
            if (i == 0) {
                table += "|\tClasses\t\t\tFreq.\t\tPonto Médio\t\tFreq. Relativa\t\tFreq. Acumulada |\n"
                table += "|-----------------------------------------------------------------------------------|\n"
            }
            table += "|\t${classes[i].first} - ${classes[i].second} \t|\t ${frequency[i]} \t\t|\t ${midpoints[i]} \t\t|\t\t ${relativeFrequency[i]} \t\t|\t\t ${cumulativeFrequency[i]}\t\t|\n"
        }
        table += "____________________________________________________________________________________"
        return table
    }
}