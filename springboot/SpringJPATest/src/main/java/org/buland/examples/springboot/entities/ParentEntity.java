package org.buland.examples.springboot.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
public class ParentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String parentProperty;

    @OneToMany(orphanRemoval = true)
    private Set<ChildEntity> children = new java.util.LinkedHashSet<>();

    public Set<ChildEntity> getChildren() {
        return children;
    }

    // getters and setters
}
