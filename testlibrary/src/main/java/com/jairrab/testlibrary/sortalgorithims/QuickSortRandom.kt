package com.jairrab.testlibrary.sortalgorithims

import com.jairrab.testlibrary.swap
import java.util.concurrent.ThreadLocalRandom

class QuickSortRandom {

    private val random: ThreadLocalRandom = ThreadLocalRandom.current()

    fun sort(array: IntArray): IntArray {
        sort(array, 0, array.size - 1)
        return array
    }

    private fun sort(array: IntArray, begin: Int, end: Int) {
        if (begin < end) {
            val index = partition(array, begin, end)

            sort(array, begin, index - 1)
            sort(array, index, end)
        }
    }

    private fun partition(array: IntArray, begin: Int, end: Int): Int {
        var i = begin
        var j = end
        val pivot = random.nextInt(begin,end)

        while (i < j) {
            while (array[i] <= array[pivot]) {
                if (i == end) break
                i++
            }

            while (j > i && array[pivot] <= array[j]) {
                if (j == 0) break
                j--
            }

            if (i != j) {
                swap(array, i, j)
            } else {
                if (i > pivot) {
                    if (array[pivot] > array[i]) swap(array, i, pivot)
                } else if (i < pivot) {
                    if (array[pivot] < array[i]) swap(array, i, pivot)
                }
                return i
            }
        }

        throw Exception("Sorting error...")
    }
}