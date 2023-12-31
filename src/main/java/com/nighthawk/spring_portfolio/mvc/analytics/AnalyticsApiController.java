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

@RestController // annotation to simplify the creation of RESTful web services
@RequestMapping("/api/analytics")  // all requests in file begin with this URI
public class AnalyticsApiController {

    // Autowired enables Control to connect URI request and POJO Object to easily for Database CRUD operations
    @Autowired
    private AnalyticsJpaRepository repository;

    /* GET List of Jokes
     * @GetMapping annotation is used for mapping HTTP GET requests onto specific handler methods.
     */
    @GetMapping("/")
    public ResponseEntity<List<Analytics>> getJokes() {
        // ResponseEntity returns List of Jokes provide by JPA findAll()
        return new ResponseEntity<>( repository.findAll(), HttpStatus.OK);
    }

    /* Update Like
     * @PutMapping annotation is used for mapping HTTP PUT requests onto specific handler methods.
     * @PathVariable annotation extracts the templated part {id}, from the URI
     */

    /* Update Jeer
     */

    @PostMapping("/post")
    public ResponseEntity<Object> postPerson(@RequestParam("time") long time,
    @RequestParam("type") String type,
    @RequestParam("iterations") int iterations,
    @RequestParam("swaps") int swaps,
    @RequestParam("serializedUnsorted") String serializedUnsorted,
    @RequestParam("serializedSorted") String serializedSorted) {

        repository.save(new Analytics(null, time, type, iterations, serializedUnsorted, serializedSorted));
        return new ResponseEntity<>("Created successfully", HttpStatus.CREATED);
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

    /*Sorting Types: 
    Bubble Sort
    Selection Sort
    Insertion Sort
    Merge Sort
    Quick Sort
    Heap Sort*/

    private SortingMethods sorter = new SortingMethods();

    @GetMapping("/bubble")
    public ResponseEntity<String> bubble(@RequestParam("list") String serializedUnsortedList) {
        int[] unsortedList;
        try {
            unsortedList = sorter.unserialize(serializedUnsortedList);
        } catch (Exception e) {
            return new ResponseEntity<>("List could not be unserialized. It needs to be in a format like this: \"1,2,3.\"", HttpStatus.BAD_REQUEST);
        }
        // Sort the list using Bubble Sort
        int[] sortedList = sorter.bubbleSort(unsortedList);
        return new ResponseEntity<>(sorter.serialize(sortedList), HttpStatus.OK);
    }

    @GetMapping("/selection")
    public ResponseEntity<String> selection(@RequestParam("list") String serializedUnsortedList) {
        int[] unsortedList;
        try {
            unsortedList = unserialize(serializedUnsortedList);
        } catch (Exception e) {
            return new ResponseEntity<>("List could not be unserialized. It needs to be in a format like this: \"1,2,3.\"", HttpStatus.BAD_REQUEST);
        }
        // Sort the list using Selection Sort
        int[] sortedList = sorter.selectionSort(unsortedList);
        return new ResponseEntity<>(serialize(sortedList), HttpStatus.OK);
    }

    @GetMapping("/insertion")
    public ResponseEntity<String> insertion(@RequestParam("list") String serializedUnsortedList) {
        int[] unsortedList;
        try {
            unsortedList = sorter.unserialize(serializedUnsortedList);
        } catch (Exception e) {
            return new ResponseEntity<>("List could not be unserialized. It needs to be in a format like this: \"1,2,3.\"", HttpStatus.BAD_REQUEST);
        }
        // Sort the list using Insertion Sort
        int[] sortedList = sorter.insertionSort(unsortedList);
        return new ResponseEntity<>(sorter.serialize(sortedList), HttpStatus.OK);
    }

    /*@GetMapping("/merge")
    public ResponseEntity<String> merge(@RequestParam("list") String serializedUnsortedList) {
        int[] unsortedList;
        try {
            unsortedList = sorter.unserialize(serializedUnsortedList);
        } catch (Exception e) {
            return new ResponseEntity<>("List could not be unserialized. It needs to be in a format like this: \"1,2,3.\"", HttpStatus.BAD_REQUEST);
        }
        // Sort the list using Merge Sort
        int[] sortedList = sorter.mergeSort(unsortedList);
        return new ResponseEntity<>(sorter.serialize(sortedList), HttpStatus.OK);
    }*/

    @GetMapping("/quick")
    public ResponseEntity<String> quick(@RequestParam("list") String serializedUnsortedList) {
        int[] unsortedList;
        try {
            unsortedList = sorter.unserialize(serializedUnsortedList);
        } catch (Exception e) {
            return new ResponseEntity<>("List could not be unserialized. It needs to be in a format like this: \"1,2,3.\"", HttpStatus.BAD_REQUEST);
        }
        // Sort the list using Quick Sort
        int[] sortedList = sorter.quickSort(unsortedList);
        return new ResponseEntity<>(sorter.serialize(sortedList), HttpStatus.OK);
    }
    

    @GetMapping("/heap")
    public ResponseEntity<String> heap(@RequestParam("list") String serializedUnsortedList) {
        int[] unsortedList;
        try {
            unsortedList = unserialize(serializedUnsortedList);
        } catch(Exception e) {
            return new ResponseEntity<>("List could not be unserialized. It needs to be in a format like this: \"1,2,3.\"", HttpStatus.BAD_REQUEST);
        }
    
        int n = unsortedList.length;
        int iterations = 0;
    
        long startTime = System.nanoTime();
    
        for (int i = n / 2 - 1; i >= 0; i--) {
            iterations++;
            heapify(unsortedList, n, i);
        }
    
        for (int i=n-1; i>=0; i--) {
            iterations++;
            int temp = unsortedList[0];
            unsortedList[0] = unsortedList[i];
            unsortedList[i] = temp;
    
            heapify(unsortedList, i, 0);
        }
    
        long endTime = System.nanoTime();
        long time = endTime - startTime;
    
        String type = "Heap";
        String serializedSorted = serialize(unsortedList);
    
        repository.save(new Analytics(null, time, type, iterations, serializedUnsortedList, serializedSorted));
        return new ResponseEntity<>(serializedSorted, HttpStatus.OK);
    }
    void heapify(int arr[], int n, int i) {
        int largest = i;
    
        int l = 2*i + 1;
        int r = 2*i + 2;
    
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
    
    
    
    @GetMapping("/fibonacci")
    public ResponseEntity<String> fibonacci(@RequestParam("values") int numberOfValues,
    @RequestParam("even") boolean even) {
        if(numberOfValues > 150 || numberOfValues < 1) {
            return new ResponseEntity<>("Requests for more than 150 or less than 1 values not allowed", HttpStatus.BAD_REQUEST);
        }
        
        String fibonacciValues = Stream.iterate(0, i -> i + 1)
                                    .limit(numberOfValues)
                                    .map(i -> (even ? new EvenFibonacci(i) : new OddFibonacci(i)).calculate())
                                    .map(String::valueOf)
                                    .collect(Collectors.joining(", "));
        return new ResponseEntity<>(fibonacciValues, HttpStatus.OK);
    }

}