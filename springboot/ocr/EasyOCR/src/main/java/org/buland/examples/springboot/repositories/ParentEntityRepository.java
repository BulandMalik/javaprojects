package org.buland.examples.springboot.repositories;

import org.buland.examples.springboot.entities.ParentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParentEntityRepository extends JpaRepository<ParentEntity, Long> {

    @Query("SELECT p, c.childProperty1, c.childProperty2 FROM ParentEntity p JOIN p.children c")
    List<Object[]> findAllWithChildProperties();
}