package com.jairrab.testlibrary.sortexercise

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
        val pivot = array[end]
        var i = begin

        for (j in begin until end) {
            if (array[j] <= pivot) {
                swap(array, i, j)
                i++
            }
        }

        swap(array, i, end)
        return i
    }
}