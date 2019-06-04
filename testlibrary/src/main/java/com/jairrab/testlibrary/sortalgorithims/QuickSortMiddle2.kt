package com.jairrab.testlibrary.sortalgorithims

import com.jairrab.testlibrary.swap

/**
 * https://www.bogotobogo.com/Algorithms/quicksort.php
 */
class QuickSortMiddle2 {
    fun sort(array: IntArray): IntArray {
        qsort(array, 0, array.size - 1)
        return array
    }

    fun qsort(a: IntArray, left_index: Int, right_index: Int) {
        if (left_index >= right_index) return

        var left = left_index
        var right = right_index
        // pivot selection
        val pivot = a[(left_index + right_index) / 2]

        // partition
        while (left <= right) {
            while (a[left] < pivot) left++
            while (a[right] > pivot) right--
            if (left <= right) {
                swap(a, left, right)
                left++
                right--
            }
        }

        // recursion
        qsort(a, left_index, right)
        qsort(a, left, right_index)
    }
}