package com.acu.neo4j;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PersonRepository extends Neo4jRepository<Person, Long> {

    List<Person> findByName(String name);

    @Query("MATCH (p:Person) WHERE p.age > $minAge RETURN p")
    List<Person> findPeopleOlderThan(@Param("minAge") int minAge);

    @Query("MATCH (p:Person) RETURN p ORDER BY p.name")
    List<Person> findAllOrderedByName();
}
