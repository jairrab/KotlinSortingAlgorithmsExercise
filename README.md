I've been doing some reviewing of time and space complexity analysis to assess diferrent ways of solving a problem. I decided to look at the age old ways of sorting an array of numbers. The code is written in Kotlin, so it was also an opportunity for me to update plenty of code resources available on the internet to Google's new official Android language.

Running the test driver file at [SortingExercise.kt](https://github.com/jairrab/KotlinSortingAlgorithmsExercise/blob/master/testlibrary/src/main/java/com/jairrab/testlibrary/SortingExercise.kt) will yield the following type of result:
```
Array size = 1000

Test #1= [BubbleSort, BuiltInSort, MergeSort, InsertionSort, QuickSortRandom, QuickSortEnd, QuickSortMiddle, QuickSortMiddle2], fastest=QuickSortMiddle2 slowest=InsertionSort
Test #2= [InsertionSort, MergeSort, QuickSortRandom, QuickSortEnd, BubbleSort, QuickSortMiddle, QuickSortMiddle2, BuiltInSort], fastest=QuickSortMiddle2 slowest=BubbleSort
Test #3= [BubbleSort, QuickSortRandom, QuickSortMiddle2, MergeSort, QuickSortEnd, BuiltInSort, InsertionSort, QuickSortMiddle], fastest=QuickSortEnd slowest=BubbleSort
Test #4= [InsertionSort, QuickSortMiddle, QuickSortMiddle2, BubbleSort, QuickSortEnd, QuickSortRandom, BuiltInSort, MergeSort], fastest=QuickSortEnd slowest=BubbleSort
Test #5= [QuickSortEnd, QuickSortMiddle, QuickSortMiddle2, InsertionSort, BuiltInSort, MergeSort, BubbleSort, QuickSortRandom], fastest=QuickSortEnd slowest=BubbleSort
Test #6= [QuickSortRandom, QuickSortMiddle2, InsertionSort, BubbleSort, BuiltInSort, QuickSortEnd, QuickSortMiddle, MergeSort], fastest=QuickSortEnd slowest=BubbleSort
Test #7= [QuickSortMiddle, BuiltInSort, BubbleSort, InsertionSort, MergeSort, QuickSortMiddle2, QuickSortEnd, QuickSortRandom], fastest=QuickSortMiddle2 slowest=BubbleSort
Test #8= [MergeSort, BubbleSort, QuickSortMiddle, QuickSortRandom, QuickSortEnd, QuickSortMiddle2, BuiltInSort, InsertionSort], fastest=QuickSortEnd slowest=BubbleSort
Test #9= [BubbleSort, QuickSortMiddle2, MergeSort, QuickSortRandom, BuiltInSort, InsertionSort, QuickSortEnd, QuickSortMiddle], fastest=QuickSortEnd slowest=BubbleSort
Test #10= [BuiltInSort, BubbleSort, QuickSortRandom, QuickSortMiddle2, QuickSortEnd, InsertionSort, QuickSortMiddle, MergeSort], fastest=QuickSortEnd slowest=BubbleSort
Test #11= [MergeSort, QuickSortMiddle, QuickSortEnd, QuickSortRandom, BubbleSort, InsertionSort, QuickSortMiddle2, BuiltInSort], fastest=QuickSortEnd slowest=BubbleSort
Test #12= [QuickSortMiddle, QuickSortMiddle2, BubbleSort, InsertionSort, MergeSort, QuickSortRandom, BuiltInSort, QuickSortEnd], fastest=QuickSortEnd slowest=BubbleSort
Test #13= [BubbleSort, InsertionSort, QuickSortEnd, BuiltInSort, QuickSortMiddle2, MergeSort, QuickSortMiddle, QuickSortRandom], fastest=QuickSortEnd slowest=BubbleSort
Test #14= [QuickSortMiddle2, BubbleSort, BuiltInSort, QuickSortMiddle, InsertionSort, QuickSortRandom, QuickSortEnd, MergeSort], fastest=QuickSortEnd slowest=BubbleSort
Test #15= [MergeSort, BuiltInSort, QuickSortMiddle2, QuickSortMiddle, InsertionSort, QuickSortRandom, BubbleSort, QuickSortEnd], fastest=QuickSortEnd slowest=BubbleSort

0.049ms for QuickSortEnd, 0.0% slower. 100.0% correct
0.055ms for QuickSortMiddle2, 13.0% slower. 100.0% correct
0.061ms for QuickSortMiddle, 24.8% slower. 100.0% correct
0.063ms for BuiltInSort, 30.1% slower. 100.0% correct
0.067ms for QuickSortRandom, 38.3% slower. 100.0% correct
0.167ms for MergeSort, 243.7% slower. 100.0% correct
0.514ms for InsertionSort, 956.9% slower. 100.0% correct
0.825ms for BubbleSort, 1595.8% slower. 100.0% correct
```
The tests are randomized and warm-up tests are added to eliminate testing order bias. The size of the array, the number of tests and warm-up tests can be configured in the test driver.
# QuickSort
## Using end of array as pivot
Best Case: The best case occurs when the partition process always picks the middle number as pivot. There are many resources on the internet that would give the math explanation for this, but at best case, the time complexity for this is Θ(n*Log*n).

Worst Case: The worst case occurs when the partition process always picks greatest or smallest number as pivot. The time complexity at this condition is Θ(n<sup>2</sup>)

On a sorted or reverse sorted array, this type of sorting will be bad as you always end up picking the greatest or smallest number as pivot. One way to significantly reduce the chances of picking the extreme elements as pivot is to pick the middle or a random element as pivot as seen below.

The memory overhead of running QuickSort is very small, and in general, it's space complexity is considered in-place (constant), meaning there's no increase in memory requirement as the size of the array grows.
~~~ kotlin
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
~~~
## Using middle element as pivot
~~~ kotlin
class QuickSortMiddle {
    fun sort(array: IntArray): IntArray {
        qsort(array, 0, array.size - 1)
        return array
    }

    fun qsort(array: IntArray, left_index: Int, right_index: Int) {
        if (left_index >= right_index) return

        var left = left_index
        var right = right_index
        // pivot selection
        val pivot = array[(left_index + right_index) / 2]

        // partition
        while (left <= right) {
            while (array[left] < pivot) left++
            while (array[right] > pivot) right--
            if (left <= right) {
                swap(array, left, right)
                left++
                right--
            }
        }

        // recursion
        qsort(array, left_index, right)
        qsort(array, left, right_index)
    }
}
~~~
## Using random element as pivot
~~~kotlin
class QuickSortRandom {

    private val random: ThreadLocalRandom = ThreadLocalRandom.current()

    fun sort(array: IntArray): IntArray {
        sort(array, 0, array.size - 1)
        return array
    }

    private fun sort(array: IntArray, begin: Int, end: Int) {
        if (end - begin <= 0) {
            return
        } else if (end - begin == 1) {
            if (array[begin] > array[end]) swap(array, begin, end)
        } else {
            val index = partition(array, begin, end)
            sort(array, begin, index - 1)
            sort(array, index, end)
        }
    }

    private fun partition(array: IntArray, begin: Int, end: Int): Int {
        var i = begin
        var j = end
        val pivot = random.nextInt(begin, end)

        while (i < j) {
            while (array[i] <= array[pivot]) {
                if (i == end) break
                i++
            }

            while (array[pivot] <= array[j]) {
                if (j == i) break
                j--
            }

            if (i == j) {
                if (i > pivot) {
                    if (array[pivot] > array[i]) swap(array, i, pivot)
                } else if (i < pivot) {
                    if (array[pivot] < array[i]) swap(array, i, pivot)
                }
                return i
            }

            swap(array, i, j)
        }

        throw Exception("Sorting error...")
    }
}
~~~
# MergeSort
Overall time complexity of Merge sort is O(n*Log*n). In worst case the runtime is also O(n*log*n). However, the space complexity of Merge sort is O(n), which means that this algorithm takes a lot of space and may slow down operations for the last data sets.
~~~ kotlin
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
~~~
# InsertionSort
The best case input is an array that is already sorted. In this case insertion sort has a linear running time (i.e., O(n)).

The simplest worst case input is an array sorted in reverse order. The set of all worst case inputs consists of all arrays where each element is the smallest or second-smallest of the elements before it. This gives insertion sort a quadratic running time (i.e., O(n<sup>2</sup>)).

The average case is also quadratic, which makes insertion sort impractical for sorting large arrays. However, insertion sort is one of the fastest algorithms for sorting very small arrays, even faster than quicksort. The exact threshold must be determined experimentally and depends on the machine, but is commonly around ten.
~~~ kotlin
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
~~~
# BubbleSort
Bubble sort has a worst-case and average complexity of О(n<sup>2</sup>), where n is the number of items being sorted. Most practical sorting algorithms have substantially better worst-case or average complexity, often O(n*log*n). Even other О(n<sup>2</sup>) sorting algorithms, such as insertion sort, generally run faster than BubbleSort, and are no more complex. Therefore, bubble sort is not a practical sorting algorithm. BubbleSort is sometimes used to compare with other algorithms when the list is already sorted (best-case), since the complexity of BubbleSort is only O(n) in these cases.
~~~ kotlin
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
~~~
# Note:
All the swap functions called above is a simple method to swap the elements at given position within an array:
~~~ kotlin
fun swap(array: IntArray, i: Int, j: Int) {
    val swapTemp = array[i]
    array[i] = array[j]
    array[j] = swapTemp
}
~~~
