package com.jairrab.testlibrary.sortalgorithims

class InsertionSort {

    fun sort(array: IntArray): IntArray {
        for (i in 1 until array.size) {
            if (array[i] < array[i - 1]) {
                val temp = array[i]

                for (j in i downTo 0) {
                    if (j != 0 && array[j - 1] >= temp) {
                        array[j] = array[j - 1]
                    } else {
                        array[j] = temp
                        break
                    }
                }
            }
        }

        return array
    }
}