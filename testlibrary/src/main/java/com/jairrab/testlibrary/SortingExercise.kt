package com.jairrab.testlibrary

import com.jairrab.testlibrary.sortalgorithims.*
import java.util.*
import java.util.concurrent.ThreadLocalRandom
import java.util.stream.Collectors
import kotlin.system.measureNanoTime


private const val ARRAY_SIZE = 10000
private const val SMALLEST_ELEMENT = -ARRAY_SIZE / 2
private const val LARGEST_ELEMENT = ARRAY_SIZE / 2
private const val NUMBER_OF_TEST = 11
private const val WARMUP_RUNS = 3
private const val SHOW_INPUT_ARRAY = false
private const val SHOW_OUTPUT_ARRAY = false //show first output array of each algorithm

@Suppress("ConstantConditionIf")
fun main() {
    var array = IntArray(ARRAY_SIZE)
    println("Array size = $ARRAY_SIZE")

    when (1) {
        -1 -> array = intArrayOf(1, -1, 5, 4, -3, -5, 3, 1, 1, 1, 4) // custom array
        0 -> for (i in 0 until array.size) array[i] = i //ascending elements
        1 -> for (i in 0 until array.size) array[i] =
            ThreadLocalRandom.current().nextInt(SMALLEST_ELEMENT, LARGEST_ELEMENT + 1) //random elements
        2 -> for (i in 0 until array.size) array[i] = array.size - i //descending elements
    }

    if (SHOW_INPUT_ARRAY) println(array.toList().toString())

    val sortAlgorithms: MutableList<Pair<String, () -> IntArray>> = ArrayList()
    sortAlgorithms.add(Pair("BuiltInSort", { BuiltInSort().sort(array.copyOf()) }))
    sortAlgorithms.add(Pair("QuickSortEnd", { QuickSortEnd().sort(array.copyOf()) }))
    sortAlgorithms.add(Pair("QuickSortMiddle", { QuickSortMiddle().sort(array.copyOf()) }))
    sortAlgorithms.add(Pair("QuickSortRandom", { QuickSortRandom().sort(array.copyOf()) }))
    sortAlgorithms.add(Pair("MergeSort", { MergeSort().sort(array.copyOf()) }))
    sortAlgorithms.add(Pair("BubbleSort", { BubbleSort().sort(array.copyOf()) })) //throws exception for large arrays
    sortAlgorithms.add(Pair("InsertionSort", { InsertionSort().sort(array.copyOf()) }))

    val results = ArrayList<Result>()

    println()

    repeat(WARMUP_RUNS) {
        sortAlgorithms.shuffle()
        for (j in sortAlgorithms) j.second()
    }

    repeat(NUMBER_OF_TEST) { i ->
        sortAlgorithms.shuffle()

        println("Test #$i= ${sortAlgorithms.map { it.first }}")

        for (j in sortAlgorithms) {
            var arr = intArrayOf()
            results.add(
                Result(
                    name = j.first,
                    time = measureNanoTime { arr = j.second() } / 1000000.0,
                    array = arr
                )
            )
        }
    }

    println()

    val correctArray = results.first { it.name == "BuiltInSort" }.array

    val groupedResult = results
        .groupBy { it.name }
        .mapValues { entry ->
            GroupedResult(
                arrays = entry.value.map { it.array },
                time = entry.value.sumByDouble { it.time } / NUMBER_OF_TEST,
                correctness = entry.value.map { it.array.contentEquals(correctArray) })
        }
        .entries
        .stream()
        .sorted { a, b -> ((a.value.time - b.value.time) * 1000000).toInt() }
        .map { it }
        .collect(Collectors.toList())

    val fastestTime = groupedResult[0].value.time

    groupedResult.forEach { i ->
        val name = i.key
        val time = String.format("%.3f", i.value.time).toDouble()
        val speed = String.format("%.1f", (((i.value.time - fastestTime) / fastestTime) * 100.0))
        val correctOutput = String.format("%.1f", (i.value.correctness.filter { it }.count() * 100.0) / NUMBER_OF_TEST)

        println("${time}ms for $name, $speed% slower. $correctOutput% correct")

        if (SHOW_OUTPUT_ARRAY) println(i.value.arrays[0].toList())
    }
}

internal fun swap(array: IntArray, i: Int, j: Int) {
    val swapTemp = array[i]
    array[i] = array[j]
    array[j] = swapTemp
}

class Result(
    val name: String,
    val time: Double,
    val array: IntArray
)

class GroupedResult(
    val time: Double,
    val arrays: List<IntArray>,
    val correctness: List<Boolean>
)