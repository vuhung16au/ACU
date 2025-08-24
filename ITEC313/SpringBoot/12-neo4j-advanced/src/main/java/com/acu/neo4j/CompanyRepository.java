package com.acu.neo4j;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CompanyRepository extends Neo4jRepository<Company, Long> {
    
    List<Company> findByName(String name);
    
    List<Company> findByIndustry(String industry);
    
    List<Company> findBySize(String size);
    
    @Query("MATCH (p:Person)-[:WORKS_AT]->(c:Company) WHERE p.name = $personName RETURN c")
    List<Company> findCompaniesByPerson(@Param("personName") String personName);
    
    @Query("MATCH (c:Company)-[:PARTNERS_WITH]->(partner:Company) WHERE c.name = $companyName RETURN partner")
    List<Company> findPartnerCompanies(@Param("companyName") String companyName);
}
