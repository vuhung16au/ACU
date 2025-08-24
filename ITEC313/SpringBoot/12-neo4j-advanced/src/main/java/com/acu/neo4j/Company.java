package com.acu.neo4j;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

import java.util.List;

@Node("Company")
public class Company {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String industry;
    private String size;
    private Integer foundedYear;

    @Relationship(type = "HAS_PROJECT")
    private List<HasProject> projects;

    @Relationship(type = "USES_TECHNOLOGY")
    private List<UsesTechnology> technologies;

    public Company() {
    }

    public Company(String name, String industry, String size, Integer foundedYear) {
        this.name = name;
        this.industry = industry;
        this.size = size;
        this.foundedYear = foundedYear;
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

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Integer getFoundedYear() {
        return foundedYear;
    }

    public void setFoundedYear(Integer foundedYear) {
        this.foundedYear = foundedYear;
    }

    public List<HasProject> getProjects() {
        return projects;
    }

    public void setProjects(List<HasProject> projects) {
        this.projects = projects;
    }

    public List<UsesTechnology> getTechnologies() {
        return technologies;
    }

    public void setTechnologies(List<UsesTechnology> technologies) {
        this.technologies = technologies;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", industry='" + industry + '\'' +
                ", size='" + size + '\'' +
                ", foundedYear=" + foundedYear +
                '}';
    }
}

@RelationshipProperties
class HasProject {
    @RelationshipId
    private Long id;
    
    private String projectType;
    private String startDate;
    
    @TargetNode
    private Project project;
    
    public HasProject() {}
    
    public HasProject(String projectType, String startDate, Project project) {
        this.projectType = projectType;
        this.startDate = startDate;
        this.project = project;
    }
    
    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getProjectType() { return projectType; }
    public void setProjectType(String projectType) { this.projectType = projectType; }
    public String getStartDate() { return startDate; }
    public void setStartDate(String startDate) { this.startDate = startDate; }
    public Project getProject() { return project; }
    public void setProject(Project project) { this.project = project; }
}

@RelationshipProperties
class UsesTechnology {
    @RelationshipId
    private Long id;
    
    private String usageType;
    private String adoptionDate;
    
    @TargetNode
    private Technology technology;
    
    public UsesTechnology() {}
    
    public UsesTechnology(String usageType, String adoptionDate, Technology technology) {
        this.usageType = usageType;
        this.adoptionDate = adoptionDate;
        this.technology = technology;
    }
    
    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsageType() { return usageType; }
    public void setUsageType(String usageType) { this.usageType = usageType; }
    public String getAdoptionDate() { return adoptionDate; }
    public void setAdoptionDate(String adoptionDate) { this.adoptionDate = adoptionDate; }
    public Technology getTechnology() { return technology; }
    public void setTechnology(Technology technology) { this.technology = technology; }
}
