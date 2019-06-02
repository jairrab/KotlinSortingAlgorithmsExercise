package com.jairrab.testlibrary.sortalgorithims

class MergeSort {
    fun sort(array: IntArray): IntArray {
        split(array)
        return array
    }

    private fun split(array: IntArray): IntArray {
        val size = array.size

        if (size == 1) return array

        val mid = size / 2

        val left = split(array.sliceArray(0 until mid))
        val right = split(array.sliceArray(mid until size))

        return mergeSort(array, left, right)
    }

    private fun mergeSort(array: IntArray, left: IntArray, right: IntArray): IntArray {
        var j = 0
        var k = 0

        for (i in 0 until array.size) {
            when {
                k == right.size -> {
                    array[i] = left[j]
                    j++
                }
                j == left.size -> {
                    array[i] = right[k]
                    k++
                }
                left[j] < right[k] -> {
                    array[i] = left[j]
                    j++
                }
                else -> {
                    array[i] = right[k]
                    k++
                }
            }
        }

        return array
    }
}