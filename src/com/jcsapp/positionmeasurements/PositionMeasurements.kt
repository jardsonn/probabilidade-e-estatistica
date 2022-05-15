package com.jcsapp.positionmeasurements

data class PositionMeasurements(
    val dados: DoubleArray,
    val quartis: ArrayList<Pair<String, Double>>,
    val amplitudeInterquartil: Double,
    val limites: ArrayList<Pair<String, Double>>,
    val temOutliers: Boolean,
    val outliers: ArrayList<Double>
) {
    override fun toString(): String {
        return "DADOS = ${dados.map { it }}\n" +
                "QUANTIDADE DE ELEMENTOS = ${dados.size}\n" +
//                "QUARTIS = $quartis\n" +
                "${quartis[0].first} - ${quartis[0].second}\n"+
                "${quartis[1].first} - ${quartis[1].second}\n"+
                "${quartis[2].first} - ${quartis[2].second}\n"+
                "AMPLITUDE INTERQUARTIL = $amplitudeInterquartil\n" +
                "LIMITES = $limites\n" +
                "TEM OUTLIERS = ${if (temOutliers) "SIM" else "NÃO"}\n" +
                if (temOutliers) "OUTLIERS = $outliers" else ""
    }


}
