package com.jcsapp

import com.jcsapp.frequencydistribution.FrequencyDistribution
import com.jcsapp.positionmeasurements.PositionMeasurements
import kotlin.math.ceil
import kotlin.math.roundToInt

fun IntArray.toDataString(): String {
    var data = ""
    for (element in this.indices) {
        data += "$element "
    }
    return data.trim()
}

fun String.toData(): DoubleArray {
    val elements = if (this.trim().contains(",")) this.split(",") else this.split(" ")
    val data = ArrayList<Double>()
    repeat(elements.size) {
        val digit = elements[it].trim()
        data.add(digit.toDouble())
    }

    return data.toDoubleArray()
}

fun Double.roundUp(): Double{
    return times(100).roundToInt().div(100.0)
}

class Operations(dataString: String) {

    companion object {
        const val Q1_INDEX = 0
        const val Q2_INDEX = 1
        const val Q3_INDEX = 2
    }

    private var data: DoubleArray = dataString.toData().apply { this.sort() }

    private fun getTotalBreadth(): Double {
        return data[data.size - 1].toDouble() - data[0].toDouble()
    }

    private fun getClassBreadth(DoubleOfClasses: Int): Int {
        return ceil(getTotalBreadth().toDouble().div(DoubleOfClasses)).toInt()
    }

    private fun quartis(dados: List<Double>): ArrayList<Pair<String, Double>> {
        val q1: Double
        val q2 = mediana(dados).second
        val q3: Double
        if (dados.size % 2 == 0) {
            val indexI = (indexMediana(dados) as Pair<*, *>).first
            val indexS = (indexMediana(dados) as Pair<*, *>).second
            val q2Left = dados.toList().mapIndexedNotNull { index, i -> if (index <= indexI as Int) i else null }
            val q2Right = dados.toList().mapIndexedNotNull { index, i -> if (index >= indexS as Int) i else null }
            q1 = mediana(q2Left).second
            q3 = mediana(q2Right).second

        } else {
            val indexUnico = (indexMediana(dados) as Int)
            val q2Left = dados.mapIndexedNotNull { index, i -> if (index < indexUnico) i else null }
            val q2Right = dados.mapIndexedNotNull { index, i -> if (index > indexUnico) i else null }
            q1 = mediana(q2Left).second
            q3 = mediana(q2Right).second
        }
        return arrayListOf(
            Pair("Q1", q1),
            Pair("Q2", q2),
            Pair("Q3", q3),
        )
    }

    private fun mediana(dados: List<Double>): Pair<Any, Double> {
        val isPar = dados.size % 2 == 0
        val meio = dados.size.div(2)
        val mediana: Double
        return if (isPar) {
            mediana = (dados[meio - 1].plus(dados[meio])).div(2.0)
            Pair(Pair(meio - 1, meio), mediana.roundUp())
        } else {
            val index: Int = meio
            mediana = dados[index]
            Pair(index, mediana.roundUp())
        }
    }

    private fun indexMediana(dados: List<Double>) = if (data.size % 2 == 0) {
        val indexMedia = mediana(dados).first
        indexMedia as Pair<*, *>
    } else {
        val indexMedia = mediana(dados).first
        indexMedia as Int
    }

    private fun outliers(
        limiteInferior: Double,
        limiteSuperior: Double,
        dados: DoubleArray
    ): List<Pair<ArrayList<Double>, Boolean>> {
        val outliers = ArrayList<Pair<ArrayList<Double>, Boolean>>()
        val outInferior = ArrayList<Double>()
        val outSuperior = ArrayList<Double>()
        for (outlier in dados) {
            if (outlier < limiteInferior)
                outInferior.add(outlier)
            if (outlier > limiteSuperior)
                outSuperior.add(outlier)
        }
        outliers.add(Pair(outInferior, outInferior.isNotEmpty()))
        outliers.add(Pair(outSuperior, outSuperior.isNotEmpty()))
        return outliers
    }


    fun getQuartil(): PositionMeasurements {
        val quartis = quartis(data.toList())
        val q1 = quartis[Q1_INDEX].second
        val q3 = quartis[Q3_INDEX].second
        val amplitudeInterquartil = q3 - q1
        val limiteInferior = q1.minus(1.5.times(amplitudeInterquartil)).roundUp()
        val limiteSuperior = q3.plus(1.5.times(amplitudeInterquartil)).roundUp()
        val outliers = outliers(limiteInferior, limiteSuperior, data)
        val temOutliers = outliers[0].second || outliers[1].second

        return PositionMeasurements(
            dados = data,
            quartis = quartis,
            amplitudeInterquartil = amplitudeInterquartil.roundUp(),
            limites = arrayListOf(Pair("LIMITE INFERIOR", limiteInferior), Pair("LIMITE SUPERIOR", limiteSuperior)),
            temOutliers = temOutliers,
            outliers = if (outliers[0].second) outliers[0].first else if (outliers[1].second) outliers[1].first else arrayListOf()
        )
    }

    fun getFrequencyDistribution(numebrOfClasses: Int): FrequencyDistribution {
        val totalBreadth = getTotalBreadth()
        val classBreadth = getClassBreadth(numebrOfClasses)
        val classes = ArrayList<Pair<Double, Double>>()
        var minimumValue = data[0]
        var maximumValue = minimumValue.plus(classBreadth).minus(1)
        val frequency = arrayListOf<Int>()
        val midpoints = arrayListOf<Double>()
        val relativeFrequency = arrayListOf<Double>()
        val cumulativeFrequency = arrayListOf<Double>()
        var biggerFrequency: Pair<String, Int>
        var lowerFrequency: Pair<String, Int>

        classes.add(Pair(minimumValue, maximumValue))

        for (i in 2..numebrOfClasses) {
            maximumValue = minimumValue.plus(classBreadth)
            classes.add(Pair(maximumValue, maximumValue.plus(classBreadth).minus(1)))
            minimumValue = maximumValue
        }


        val frequencyTemp = ArrayList<Int>()
        for (i in data.indices) {
            for (j in classes.indices) {
                if (data[i].toInt() >= classes[j].first && data[i].toInt() <= classes[j].second) {
                    frequencyTemp.add(j)
                }
            }
        }

        for (i in 0 until numebrOfClasses) {
            val fre = frequencyTemp.count { it == i }
            val freRelative = fre.toDouble().div(data.size)
            frequency.add(fre)
            midpoints.add((classes[i].first.toDouble().plus(classes[i].second)).div(2))
            relativeFrequency.add(freRelative.roundUp())
            if (i == 0) cumulativeFrequency.add(relativeFrequency[i])
            else
                cumulativeFrequency.add((cumulativeFrequency[i - 1] + freRelative).roundUp())
        }

        biggerFrequency = Pair("[${classes[0].first} - ${classes[0].second}]", frequency[0])
        lowerFrequency = Pair("[${classes[0].first} - ${classes[0].second}]", frequency[0])
        for ((i, element) in frequency.withIndex()) {
            if (element > biggerFrequency.second) biggerFrequency =
                Pair("[${classes[i].first} - ${classes[i].second}]", element)
            if (element < lowerFrequency.second) lowerFrequency =
                Pair("[${classes[i].first} - ${classes[i].second}]", element)
        }


        return FrequencyDistribution(
            numberOfClasses = numebrOfClasses,
            data = data,
            frequency = frequency,
            midpoints = midpoints,
            relativeFrequency = relativeFrequency,
            cumulativeFrequency = cumulativeFrequency,
            minimumValue = data[0].toInt(),
            classBreadth = classBreadth,
            totalBreadth = totalBreadth,
            maximumValue = minimumValue.toInt().plus(classBreadth).minus(1),
            classes = classes,
            biggerFrequency = biggerFrequency,
            lowerFrequency = lowerFrequency,
            totalSize = data.size
        )
    }
}