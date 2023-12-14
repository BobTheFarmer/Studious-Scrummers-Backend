package com.nighthawk.spring_portfolio.mvc.analytics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.nighthawk.spring_portfolio.EvenFibonacci;
import com.nighthawk.spring_portfolio.Fibonacci;
import com.nighthawk.spring_portfolio.OddFibonacci;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;



class SortingMethods {


    @Autowired
    private AnalyticsJpaRepository repository;

    //Sorting Types:
    //Bubble Sort
    //Selection Sort
    //Insertion Sort
    //Merge Sort
    //Quick Sort
    //Heap Sort

    public int[] bubbleSort(int[] unsortedList) {
        long startTime = System.nanoTime();
        // Sort the list using Bubble Sort
        int n = unsortedList.length;
        for (int i = 0; i < n-1; i++) {
            for (int j = 0; j < n-i-1; j++) {
                if (unsortedList[j] > unsortedList[j+1]) {
                    // swap temp and arr[i]
                    int temp = unsortedList[j];
                    unsortedList[j] = unsortedList[j+1];
                    unsortedList[j+1] = temp;
                }
            }
        }
        long endTime = System.nanoTime();
        long time = endTime - startTime;
        //repository.save(new Analytics(null, time, "bubble", 0 /*iterations*/, serialize(unsortedList), serialize(unsortedList)));
        return unsortedList;
    }

    public int[] selectionSort(int[] unsortedList) {
        long startTime = System.nanoTime();
        int n = unsortedList.length;
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (unsortedList[j] < unsortedList[minIndex]) {
                    minIndex = j;
                }
            }
            // Swap the found minimum element with the first element
            int temp = unsortedList[minIndex];
            unsortedList[minIndex] = unsortedList[i];
            unsortedList[i] = temp;
        }
        long endTime = System.nanoTime();
        long time = endTime - startTime;
        //repository.save(new Analytics(null, time, "Selection", unsortedList.length, serialize(unsortedList), serialize(unsortedList)));
        return unsortedList;
    }

    public int[] insertionSort(int[] unsortedList) {
        long startTime = System.nanoTime();
        // Sort the list using Insertion Sort
        int n = unsortedList.length;
        for (int i = 1; i < n; ++i) {
            int key = unsortedList[i];
            int j = i - 1;
            while (j >= 0 && unsortedList[j] > key) {
                unsortedList[j + 1] = unsortedList[j];
                j = j - 1;
            }
            unsortedList[j + 1] = key;
        }
        long endTime = System.nanoTime();
        long time = endTime - startTime;
        //repository.save(new Analytics(null, time, "Insertion", 0 /*iterations*/, serialize(unsortedList), serialize(unsortedList)));
        return unsortedList;
    }

private void merge(int[] arr, int[] left, int[] right) {
    int i = 0, j = 0, k = 0;
    int n1 = left.length;
    int n2 = right.length;
    while (i < n1 && j < n2) {
        if (left[i] <= right[j]) {
            arr[k] = left[i];
            i++;
        } else {
            arr[k] = right[j];
            j++;
        }
        k++;
    }
    while (i < n1) {
        arr[k] = left[i];
        i++;
        k++;
    }
    while (j < n2) {
        arr[k] = right[j];
        j++;
        k++;
    }
}

public int[] quickSort(int[] unsortedList) {
    long startTime = System.nanoTime();
    // Sort the list using Quick Sort
    int n = unsortedList.length;
    quickSortHelper(unsortedList, 0, n - 1);
    long endTime = System.nanoTime();
    long time = endTime - startTime;
    //repository.save(new Analytics(null, time, "Quick", 0 /*iterations*/, serialize(unsortedList), serialize(unsortedList)));
    return unsortedList;
}

private void quickSortHelper(int[] arr, int low, int high) {
    if (low < high) {
        int pi = partition(arr, low, high);
        quickSortHelper(arr, low, pi - 1);
        quickSortHelper(arr, pi + 1, high);
    }
}

private int partition(int[] arr, int low, int high) {
    int pivot = arr[high];
    int i = (low - 1);
    for (int j = low; j < high; j++) {
        if (arr[j] < pivot) {
            i++;
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }
    int temp = arr[i + 1];
    arr[i + 1] = arr[high];
    arr[high] = temp;
    return i + 1;
}

public int[] heapSort(int[] unsortedList) {
    long startTime = System.nanoTime();
    // Sort the list using Heap Sort
    int n = unsortedList.length;
    int iterations = 0;
    for (int i = n / 2 - 1; i >= 0; i--) {
        iterations++;
        heapify(unsortedList, n, i);
    }
    for (int i = n - 1; i >= 0; i--) {
        iterations++;
        int temp = unsortedList[0];
        unsortedList[0] = unsortedList[i];
        unsortedList[i] = temp;
        heapify(unsortedList, i, 0);
    }
    long endTime = System.nanoTime();
    long time = endTime - startTime;
    //repository.save(new Analytics(null, time, "Heap", iterations, serialize(unsortedList), serialize(unsortedList)));
    return unsortedList;
}

private void heapify(int[] arr, int n, int i) {
    int largest = i;
    int l = 2 * i + 1;
    int r = 2 * i + 2;
    if (l < n && arr[l] > arr[largest])
        largest = l;
    if (r < n && arr[r] > arr[largest])
        largest = r;
    if (largest != i) {
        int swap = arr[i];
        arr[i] = arr[largest];
        arr[largest] = swap;
        heapify(arr, n, largest);
    }
}

//Sorting
public static String serialize(int[] numbers) {
    return Arrays.stream(numbers)
            .mapToObj(String::valueOf)
            .collect(Collectors.joining(","));
}

public static int[] unserialize(String numbersString) {
    return Arrays.stream(numbersString.split(","))
            .mapToInt(Integer::parseInt)
            .toArray();
    }
}
