package com.jairrab.testlibrary

import com.jairrab.testlibrary.sortalgorithims.*
import java.util.concurrent.ThreadLocalRandom
import kotlin.system.measureNanoTime

private const val ARRAY_SIZE = 15000
private const val SMALLEST_ELEMENT = -ARRAY_SIZE / 2
private const val LARGEST_ELEMENT = ARRAY_SIZE / 2
private const val NUMBER_OF_TEST = 10

fun main() {
    var array = IntArray(ARRAY_SIZE)
    println("Array size = $ARRAY_SIZE")

    when (1) {
        -1 -> array = intArrayOf(1, -1, 5, 4, -3, -5, 3, 1, 1, 1, 4) // custom array
        0 -> for (i in 0 until array.size) array[i] = i //ascending elements
        1 -> for (i in 0 until array.size) array[i] = randomInt() //random elements
        2 -> for (i in 0 until array.size) array[i] = array.size - i //descending elements
    }

    //println(array.toList().toString()) //uncomment to print input array

    val sortAlgorithms = ArrayList<Pair<String, () -> IntArray>>()
    sortAlgorithms.add(Pair("BuiltInSort", { BuiltInSort().sort(array.copyOf()) }))
    sortAlgorithms.add(Pair("QuickSortEnd", { QuickSortEnd().sort(array.copyOf()) }))
    sortAlgorithms.add(Pair("QuickSortMiddle", { QuickSortMiddle().sort(array.copyOf()) }))
    sortAlgorithms.add(Pair("QuickSortRandom", { QuickSortRandom().sort(array.copyOf()) }))
    sortAlgorithms.add(Pair("MergeSort", { MergeSort().sort(array.copyOf()) }))
    sortAlgorithms.add(Pair("BubbleSort", { BubbleSort().sort(array.copyOf()) }))
    sortAlgorithms.add(Pair("InsertionSort", { InsertionSort().sort(array.copyOf()) }))

    val results = ArrayList<Triple<String, Long, IntArray>>()

    println()

    for (i in 1..NUMBER_OF_TEST) {
        sortAlgorithms.shuffle()

        println("Test #$i= ${sortAlgorithms.map { it.first }}")

        for (j in sortAlgorithms) {
            val item = results.find { it.first == j.first }

            if (item == null) {
                var arr = intArrayOf()
                results.add(
                    Triple(
                        first = j.first,
                        second = measureNanoTime { arr = j.second() },
                        third = arr
                    )
                )
            } else {
                var arr = intArrayOf()
                results.remove(item)
                results.add(
                    Triple(
                        first = j.first,
                        second = (measureNanoTime { arr = j.second() } + item.second) / 2,
                        third = arr
                    )
                )
            }
        }
    }

    println()

    results.sortBy { it.second }

    for (i in results) {
        val speed = getPercentageSpeed(results[0].second, i.second)
        val correctOutput = results.firstOrNull { it.first == "BuiltInSort" }?.third?.contentEquals(i.third)
        println("${i.second}ns for ${i.first}, $speed% slower. Output equal to built-in sort= $correctOutput")
        //println(i.third.toList()) //uncomment to print output array
    }
}

private fun getPercentageSpeed(reference: Long, subject: Long): Int {
    return (((subject - reference) / reference.toDouble()) * 100).toInt()
}

internal fun swap(array: IntArray, i: Int, j: Int) {
    val swapTemp = array[i]
    array[i] = array[j]
    array[j] = swapTemp
}

private fun randomInt(): Int {
    return ThreadLocalRandom.current().nextInt(SMALLEST_ELEMENT, LARGEST_ELEMENT + 1)
}
