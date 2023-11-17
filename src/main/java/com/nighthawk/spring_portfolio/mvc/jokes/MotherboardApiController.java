package com.nighthawk.spring_portfolio.mvc.jokes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import com.nighthawk.spring_portfolio.mvc.jokes.Motherboard;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

@RestController // annotation to simplify the creation of RESTful web services
@RequestMapping("/api/motherboards")  // all requests in file begin with this URI
public class MotherboardApiController {

    // Autowired enables Control to connect URI request and POJO Object to easily for Database CRUD operations
    @Autowired
    private MotherboardJpaRepository repository;

    /* GET List of Jokes
     * @GetMapping annotation is used for mapping HTTP GET requests onto specific handler methods.
     */
    @GetMapping("/")
    public ResponseEntity<List<Motherboard>> getMotherboards() {
        // ResponseEntity returns List of Jokes provide by JPA findAll()
        return new ResponseEntity<>( repository.findAll(), HttpStatus.OK);
    }

    /* Update Like
     * @PutMapping annotation is used for mapping HTTP PUT requests onto specific handler methods.
     * @PathVariable annotation extracts the templated part {id}, from the URI
     */
    @PostMapping("/like/{id}")
    public ResponseEntity<Motherboard> setLike(@PathVariable long id) {
        /* 
        * Optional (below) is a container object which helps determine if a result is present. 
        * If a value is present, isPresent() will return true
        * get() will return the value.
        */
        Optional<Motherboard> optional = repository.findById(id);
        if (optional.isPresent()) {  // Good ID
            Motherboard joke = optional.get();  // value from findByID
            joke.setLikes(joke.getLikes()+1); // increment value
            repository.save(joke);  // save entity
            return new ResponseEntity<>(joke, HttpStatus.OK);  // OK HTTP response: status code, headers, and body
        }
        // Bad ID
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);  // Failed HTTP response: status code, headers, and body
    }

    /* Update Jeer
     */
    @PostMapping("/dislike/{id}")
    public ResponseEntity<Motherboard> setJeer(@PathVariable long id) {
        Optional<Motherboard> optional = repository.findById(id);
        if (optional.isPresent()) {  // Good ID
            Motherboard joke = optional.get();
            joke.setLikes(joke.getLikes()-1);
            repository.save(joke);
            return new ResponseEntity<>(joke, HttpStatus.OK);
        }
        // Bad ID
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping( "/post")
    public ResponseEntity<Object> postPerson(@RequestParam("title") String title,
                                             @RequestParam("desc") String desc,
                                             @RequestParam("ddr") int ddr,
                                             @RequestParam("gigahertz") int gigahertz,
                                             @RequestParam("gigahertz") boolean onboardWifi) {

        // A person object WITHOUT ID will create a new record with default roles as student
        Motherboard motherboard = new Motherboard(title, desc, 0, ddr, gigahertz, onboardWifi);
        repository.save(motherboard);
        return new ResponseEntity<>(title + " is created successfully", HttpStatus.CREATED);
    }
}
