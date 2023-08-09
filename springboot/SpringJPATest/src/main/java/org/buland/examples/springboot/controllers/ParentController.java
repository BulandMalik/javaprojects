package org.buland.examples.springboot.controllers;

import org.buland.examples.springboot.repositories.ParentEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/parents")
public class ParentController {

    @Autowired
    private ParentEntityRepository parentEntityRepository;

    @GetMapping
    public List<Object[]> getParentsWithChildProperties() {
        return parentEntityRepository.findAllWithChildProperties();
    }
}
