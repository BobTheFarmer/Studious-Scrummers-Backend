package com.nighthawk.spring_portfolio.mvc.analytics;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Data  // Annotations to simplify writing code (ie constructors, setters)
@NoArgsConstructor
@AllArgsConstructor
@Entity // Annotation to simplify creating an entity, which is a lightweight persistence domain object. Typically, an entity represents a table in a relational database, and each entity instance corresponds to a row in that table.
public class Analytics {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;



    @Column(unique=false)
    private int time;
    @Column(unique=false)
    private String type;
    @Column(unique=false)
    private int merges;
    @Column(unique=false)
    private int swaps;
    @Column(unique=false)
    private String serializedUnsorted;
    @Column(unique=false)
    private String serializedSorted;
}
