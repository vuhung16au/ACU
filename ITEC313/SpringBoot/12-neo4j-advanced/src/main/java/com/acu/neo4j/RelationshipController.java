package com.acu.neo4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/relationships")
public class RelationshipController {

    @Autowired
    private RelationshipService relationshipService;

    @PostMapping("/create")
    public ResponseEntity<Map<String, String>> createRelationships() {
        try {
            relationshipService.createRelationships();
            Map<String, String> response = new HashMap<>();
            response.put("message", "Relationships created successfully!");
            response.put("status", "success");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Error creating relationships: " + e.getMessage());
            response.put("status", "error");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getRelationshipStats() {
        try {
            relationshipService.displayRelationshipStats();
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Relationship statistics displayed in console");
            response.put("status", "success");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Error getting relationship stats: " + e.getMessage());
            response.put("status", "error");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> getRelationshipInfo() {
        Map<String, Object> info = new HashMap<>();
        info.put("available_relationships", new String[]{
            "WORKS_FOR - Person -> Company (with position, startDate)",
            "WORKS_ON - Person -> Project (with role, startDate)", 
            "HAS_SKILL - Person -> Skill (with proficiencyLevel, yearsOfExperience)",
            "KNOWS_TECHNOLOGY - Person -> Technology (with proficiencyLevel, yearsOfExperience)"
        });
        info.put("endpoints", new String[]{
            "POST /api/relationships/create - Create relationships between existing nodes",
            "GET /api/relationships/stats - Display relationship statistics",
            "GET /api/relationships/info - Show this information"
        });
        info.put("note", "Make sure you have nodes (Person, Company, Project, Skill, Technology) before creating relationships");
        return ResponseEntity.ok(info);
    }
}
