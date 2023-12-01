package com.nighthawk.spring_portfolio.mvc.analytics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

import java.util.Arrays;
import java.util.stream.Collectors;

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
    public ResponseEntity<Object> postPerson(@RequestParam("time") int time,
    @RequestParam("type") String type,
    @RequestParam("iterations") int iterations,
    @RequestParam("swaps") int swaps,
    @RequestParam("serializedUnsorted") String serializedUnsorted,
    @RequestParam("serializedSorted") String serializedSorted) {

        repository.save(new Analytics(null, time, type, iterations, swaps, serializedUnsorted, serializedSorted));
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

    @GetMapping("/bubble")
    public ResponseEntity<String> bubble(@RequestParam("list") String serializedUnsortedList) {
        int[] unsortedList;
        try {
            unsortedList = unserialize(serializedUnsortedList);
        }
        catch(Exception e) {
            return new ResponseEntity<>("List could not be unserialized. It needs to be in a format like this: \"1,2,3.\"", HttpStatus.BAD_REQUEST);
        }

        //Sort the list
        int[] sortedList = unsortedList; //Change this into the required sorting method

        return new ResponseEntity<>(serialize(sortedList), HttpStatus.OK);
    }

    @GetMapping("/selection")
    public ResponseEntity<String> selection(@RequestParam("list") String serializedUnsortedList) {
        int[] unsortedList;
        try {
            unsortedList = unserialize(serializedUnsortedList);
        }
        catch(Exception e) {
            return new ResponseEntity<>("List could not be unserialized. It needs to be in a format like this: \"1,2,3.\"", HttpStatus.BAD_REQUEST);
        }

        //Sort the list
        int[] sortedList = unsortedList; //Change this into the required sorting method

        return new ResponseEntity<>(serialize(sortedList), HttpStatus.OK);
    }

    @GetMapping("/insertion")
    public ResponseEntity<String> insertion(@RequestParam("list") String serializedUnsortedList) {
        int[] unsortedList;
        try {
            unsortedList = unserialize(serializedUnsortedList);
        }
        catch(Exception e) {
            return new ResponseEntity<>("List could not be unserialized. It needs to be in a format like this: \"1,2,3.\"", HttpStatus.BAD_REQUEST);
        }

        //Sort the list
        int[] sortedList = unsortedList; //Change this into the required sorting method

        return new ResponseEntity<>(serialize(sortedList), HttpStatus.OK);
    }

    @GetMapping("/merge")
    public ResponseEntity<String> merge(@RequestParam("list") String serializedUnsortedList) {
        int[] unsortedList;
        try {
            unsortedList = unserialize(serializedUnsortedList);
        }
        catch(Exception e) {
            return new ResponseEntity<>("List could not be unserialized. It needs to be in a format like this: \"1,2,3.\"", HttpStatus.BAD_REQUEST);
        }

        //Sort the list
        int[] sortedList = unsortedList; //Change this into the required sorting method

        return new ResponseEntity<>(serialize(sortedList), HttpStatus.OK);
    }

    @GetMapping("/quick")
    public ResponseEntity<String> quick(@RequestParam("list") String serializedUnsortedList) {
        int[] unsortedList;
        try {
            unsortedList = unserialize(serializedUnsortedList);
        }
        catch(Exception e) {
            return new ResponseEntity<>("List could not be unserialized. It needs to be in a format like this: \"1,2,3.\"", HttpStatus.BAD_REQUEST);
        }

        //Sort the list
        int[] sortedList = unsortedList; //Change this into the required sorting method

        return new ResponseEntity<>(serialize(sortedList), HttpStatus.OK);
    }

    @GetMapping("/heap")
    public ResponseEntity<String> heap(@RequestParam("list") String serializedUnsortedList) {
        int[] unsortedList;
        try {
            unsortedList = unserialize(serializedUnsortedList);
        }
        catch(Exception e) {
            return new ResponseEntity<>("List could not be unserialized. It needs to be in a format like this: \"1,2,3.\"", HttpStatus.BAD_REQUEST);
        }

        //Sort the list
        int[] sortedList = unsortedList; //Change this into the required sorting method

        return new ResponseEntity<>(serialize(sortedList), HttpStatus.OK);
    }
}
