package com.acu.neo4j;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepository extends Neo4jRepository<Project, Long> {
    
    List<Project> findByName(String name);
    
    List<Project> findByStatus(String status);
    
    @Query("MATCH (p:Person)-[:WORKS_ON]->(proj:Project) WHERE p.name = $personName RETURN proj")
    List<Project> findProjectsByPerson(@Param("personName") String personName);
    
    @Query("MATCH (proj:Project)-[:USES_TECHNOLOGY]->(t:Technology) WHERE proj.name = $projectName RETURN t")
    List<Technology> findTechnologiesByProject(@Param("projectName") String projectName);
    
    @Query("MATCH (proj:Project) WHERE proj.budget >= $minBudget RETURN proj ORDER BY proj.budget DESC")
    List<Project> findProjectsByBudget(@Param("minBudget") Integer minBudget);
}
