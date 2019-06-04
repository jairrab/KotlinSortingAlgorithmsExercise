package com.jairrab.testlibrary.sortalgorithims

import com.jairrab.testlibrary.swap

class QuickSortEnd {
    fun sort(array: IntArray): IntArray {
        sort(array, 0, array.size - 1)
        return array
    }

    private fun sort(array: IntArray, begin: Int, end: Int) {
        if (begin < end) {
            val partitionIndex = partition(array, begin, end)
            sort(array, begin, partitionIndex - 1)
            sort(array, partitionIndex + 1, end)
        }
    }

    private fun partition(array: IntArray, begin: Int, end: Int): Int {
        var i = begin

        for (j in begin until end) {
            if (array[j] <= array[end]) {
                swap(array, i, j)
                i++
            }
        }

        swap(array, i, end)
        return i
    }
}