package com.jairrab.testlibrary.sortalgorithims

import com.jairrab.testlibrary.swap

class BubbleSort {

    fun sort(array: IntArray): IntArray {
        sort(array, array.size - 1)
        return array
    }

    private fun sort(array: IntArray, end: Int) {
        if (end == 0) return

        for (i in 0 until end) {
            if (array[i] > array[i + 1]) {
                swap(array, i, i + 1)
            }
        }

        sort(array, end - 1)
    }
}