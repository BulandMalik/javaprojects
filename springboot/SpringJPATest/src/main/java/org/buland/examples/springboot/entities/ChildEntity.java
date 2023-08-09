package org.buland.examples.springboot.entities;

import javax.persistence.*;

@Entity
public class ChildEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String childProperty1;
    private String childProperty2;

    @ManyToOne
    private ParentEntity parent;

    // getters and setters
}