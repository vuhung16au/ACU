package com.acu.neo4j;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TechnologyRepository extends Neo4jRepository<Technology, Long> {
    
    List<Technology> findByName(String name);
    
    List<Technology> findByCategory(String category);
    
    @Query("MATCH (t:Technology) WHERE t.popularityScore >= $minScore RETURN t ORDER BY t.popularityScore DESC")
    List<Technology> findPopularTechnologies(@Param("minScore") Integer minScore);
    
    @Query("MATCH (p:Person)-[:KNOWS]->(t:Technology) WHERE p.name = $personName RETURN t")
    List<Technology> findTechnologiesByPerson(@Param("personName") String personName);
} 