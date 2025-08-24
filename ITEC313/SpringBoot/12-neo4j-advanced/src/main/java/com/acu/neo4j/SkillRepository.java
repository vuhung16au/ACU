package com.acu.neo4j;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SkillRepository extends Neo4jRepository<Skill, Long> {
    
    List<Skill> findByName(String name);
    
    List<Skill> findByCategory(String category);
    
    List<Skill> findByLevel(String level);
    
    @Query("MATCH (p:Person)-[:HAS_SKILL]->(s:Skill) WHERE p.name = $personName RETURN s")
    List<Skill> findSkillsByPerson(@Param("personName") String personName);
    
    @Query("MATCH (s:Skill) WHERE s.level = 'Expert' RETURN s")
    List<Skill> findExpertSkills();
}
