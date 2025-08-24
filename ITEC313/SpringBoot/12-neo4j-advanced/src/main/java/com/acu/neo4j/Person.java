package com.acu.neo4j;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

import java.util.List;

@Node("Person")
public class Person {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private int age;
    private String email;
    private String location;
    private Integer experienceYears;

    @Relationship(type = "WORKS_FOR")
    private List<WorksFor> companies;

    @Relationship(type = "WORKS_ON")
    private List<WorksOn> projects;

    @Relationship(type = "HAS_SKILL")
    private List<HasSkill> skills;

    @Relationship(type = "KNOWS_TECHNOLOGY")
    private List<KnowsTechnology> technologies;

    public Person() {
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Person(String name, int age, String email, String location, Integer experienceYears) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.location = location;
        this.experienceYears = experienceYears;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(Integer experienceYears) {
        this.experienceYears = experienceYears;
    }

    public List<WorksFor> getCompanies() {
        return companies;
    }

    public void setCompanies(List<WorksFor> companies) {
        this.companies = companies;
    }

    public List<WorksOn> getProjects() {
        return projects;
    }

    public void setProjects(List<WorksOn> projects) {
        this.projects = projects;
    }

    public List<HasSkill> getSkills() {
        return skills;
    }

    public void setSkills(List<HasSkill> skills) {
        this.skills = skills;
    }

    public List<KnowsTechnology> getTechnologies() {
        return technologies;
    }

    public void setTechnologies(List<KnowsTechnology> technologies) {
        this.technologies = technologies;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", location='" + location + '\'' +
                ", experienceYears=" + experienceYears +
                '}';
    }
}

@RelationshipProperties
class WorksFor {
    @RelationshipId
    private Long id;
    
    private String position;
    private String startDate;
    
    @TargetNode
    private Company company;
    
    public WorksFor() {}
    
    public WorksFor(String position, String startDate, Company company) {
        this.position = position;
        this.startDate = startDate;
        this.company = company;
    }
    
    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }
    public String getStartDate() { return startDate; }
    public void setStartDate(String startDate) { this.startDate = startDate; }
    public Company getCompany() { return company; }
    public void setCompany(Company company) { this.company = company; }
}

@RelationshipProperties
class WorksOn {
    @RelationshipId
    private Long id;
    
    private String role;
    private String startDate;
    
    @TargetNode
    private Project project;
    
    public WorksOn() {}
    
    public WorksOn(String role, String startDate, Project project) {
        this.role = role;
        this.startDate = startDate;
        this.project = project;
    }
    
    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public String getStartDate() { return startDate; }
    public void setStartDate(String startDate) { this.startDate = startDate; }
    public Project getProject() { return project; }
    public void setProject(Project project) { this.project = project; }
}

@RelationshipProperties
class HasSkill {
    @RelationshipId
    private Long id;
    
    private String proficiencyLevel;
    private Integer yearsOfExperience;
    
    @TargetNode
    private Skill skill;
    
    public HasSkill() {}
    
    public HasSkill(String proficiencyLevel, Integer yearsOfExperience, Skill skill) {
        this.proficiencyLevel = proficiencyLevel;
        this.yearsOfExperience = yearsOfExperience;
        this.skill = skill;
    }
    
    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getProficiencyLevel() { return proficiencyLevel; }
    public void setProficiencyLevel(String proficiencyLevel) { this.proficiencyLevel = proficiencyLevel; }
    public Integer getYearsOfExperience() { return yearsOfExperience; }
    public void setYearsOfExperience(Integer yearsOfExperience) { this.yearsOfExperience = yearsOfExperience; }
    public Skill getSkill() { return skill; }
    public void setSkill(Skill skill) { this.skill = skill; }
}

@RelationshipProperties
class KnowsTechnology {
    @RelationshipId
    private Long id;
    
    private String proficiencyLevel;
    private Integer yearsOfExperience;
    
    @TargetNode
    private Technology technology;
    
    public KnowsTechnology() {}
    
    public KnowsTechnology(String proficiencyLevel, Integer yearsOfExperience, Technology technology) {
        this.proficiencyLevel = proficiencyLevel;
        this.yearsOfExperience = yearsOfExperience;
        this.technology = technology;
    }
    
    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getProficiencyLevel() { return proficiencyLevel; }
    public void setProficiencyLevel(String proficiencyLevel) { this.proficiencyLevel = proficiencyLevel; }
    public Integer getYearsOfExperience() { return yearsOfExperience; }
    public void setYearsOfExperience(Integer yearsOfExperience) { this.yearsOfExperience = yearsOfExperience; }
    public Technology getTechnology() { return technology; }
    public void setTechnology(Technology technology) { this.technology = technology; }
}
