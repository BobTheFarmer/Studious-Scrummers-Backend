package com.nighthawk.spring_portfolio.mvc.analytics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

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
}
