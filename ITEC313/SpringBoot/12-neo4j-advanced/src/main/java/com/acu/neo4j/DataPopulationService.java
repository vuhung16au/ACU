package com.acu.neo4j;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Profile("!test")
public class DataPopulationService implements CommandLineRunner {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private TechnologyRepository technologyRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private SkillRepository skillRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Starting Neo4j data population...");
        
        // Load sample data from JSON file
        loadSampleDataFromJson();
        
        System.out.println("Neo4j data population completed successfully!");
    }

    private void loadSampleDataFromJson() {
        try {
            // Load JSON file from classpath
            ClassPathResource resource = new ClassPathResource("data/sample.json");
            InputStream inputStream = resource.getInputStream();
            
            // Parse JSON to SampleData object
            SampleData sampleData = objectMapper.readValue(inputStream, SampleData.class);
            
            // Create entities from JSON data
            createEntitiesFromJsonData(sampleData);
            
            System.out.println("Successfully loaded sample data from JSON file");
            
        } catch (IOException e) {
            System.err.println("Error loading sample data from JSON: " + e.getMessage());
            e.printStackTrace();
            // Fallback to hardcoded data if JSON loading fails
            System.out.println("Falling back to hardcoded sample data...");
            createSampleData();
        }
    }

    private void createEntitiesFromJsonData(SampleData sampleData) {
        // Create People from JSON data
        List<Person> people = sampleData.getPeople().stream()
                .map(personData -> new Person(
                        personData.getName(),
                        personData.getAge(),
                        personData.getEmail(),
                        personData.getLocation(),
                        personData.getExperienceYears()
                ))
                .collect(Collectors.toList());
        personRepository.saveAll(people);

        // Create Technologies from JSON data
        List<Technology> technologies = sampleData.getTechnologies().stream()
                .map(techData -> new Technology(
                        techData.getName(),
                        techData.getDescription(),
                        techData.getVersion(),
                        techData.getPopularityScore()
                ))
                .collect(Collectors.toList());
        technologyRepository.saveAll(technologies);

        // Create Companies from JSON data
        List<Company> companies = sampleData.getCompanies().stream()
                .map(companyData -> new Company(
                        companyData.getName(),
                        companyData.getIndustry(),
                        companyData.getSize(),
                        companyData.getFoundedYear()
                ))
                .collect(Collectors.toList());
        companyRepository.saveAll(companies);

        // Create Projects from JSON data
        List<Project> projects = sampleData.getProjects().stream()
                .map(projectData -> new Project(
                        projectData.getName(),
                        projectData.getDescription(),
                        projectData.getStatus(),
                        projectData.getStartDate(),
                        projectData.getBudget()
                ))
                .collect(Collectors.toList());
        projectRepository.saveAll(projects);

        // Create Skills from JSON data
        List<Skill> skills = sampleData.getSkills().stream()
                .map(skillData -> new Skill(
                        skillData.getName(),
                        skillData.getLevel(),
                        skillData.getCategory()
                ))
                .collect(Collectors.toList());
        skillRepository.saveAll(skills);

        System.out.println("Created sample data from JSON:");
        System.out.println("- " + people.size() + " people");
        System.out.println("- " + technologies.size() + " technologies");
        System.out.println("- " + companies.size() + " companies");
        System.out.println("- " + projects.size() + " projects");
        System.out.println("- " + skills.size() + " skills");
        
        // Display some sample queries
        System.out.println("\nSample data queries:");
        System.out.println("People: " + personRepository.findAll().size());
        System.out.println("Technologies: " + technologyRepository.findAll().size());
        System.out.println("Companies: " + companyRepository.findAll().size());
        System.out.println("Projects: " + projectRepository.findAll().size());
        System.out.println("Skills: " + skillRepository.findAll().size());
    }

    // Fallback method with hardcoded data (kept for backward compatibility)
    private void createSampleData() {
        // Create People
        Person alice = new Person("Alice Johnson", 28, "alice.johnson@email.com", "San Francisco", 5);
        Person bob = new Person("Bob Smith", 35, "bob.smith@email.com", "New York", 8);
        Person carol = new Person("Carol Davis", 42, "carol.davis@email.com", "Boston", 12);
        Person david = new Person("David Wilson", 25, "david.wilson@email.com", "Austin", 2);
        Person eve = new Person("Eve Brown", 30, "eve.brown@email.com", "Seattle", 6);

        List<Person> people = List.of(alice, bob, carol, david, eve);
        personRepository.saveAll(people);

        // Create Technologies
        Technology java = new Technology("Java", "Programming Language", "17", 9);
        Technology springBoot = new Technology("Spring Boot", "Framework", "3.2", 8);
        Technology neo4j = new Technology("Neo4j", "Database", "5.0", 7);
        Technology docker = new Technology("Docker", "Containerization", "24.0", 8);
        Technology kubernetes = new Technology("Kubernetes", "Orchestration", "1.28", 7);
        Technology python = new Technology("Python", "Programming Language", "3.11", 9);
        Technology react = new Technology("React", "Frontend Framework", "18.0", 8);
        Technology postgresql = new Technology("PostgreSQL", "Database", "15.0", 8);

        List<Technology> technologies = List.of(java, springBoot, neo4j, docker, kubernetes, python, react, postgresql);
        technologyRepository.saveAll(technologies);

        // Create Companies
        Company techCorp = new Company("TechCorp", "Software", "Enterprise", 1995);
        Company startupXYZ = new Company("StartupXYZ", "FinTech", "Startup", 2020);
        Company globalSoft = new Company("GlobalSoft", "Consulting", "SME", 2005);

        List<Company> companies = List.of(techCorp, startupXYZ, globalSoft);
        companyRepository.saveAll(companies);

        // Create Projects
        Project ecommerce = new Project("E-commerce Platform", "Online shopping platform", "Active", "2024-01-15", 500);
        Project mobileBanking = new Project("Mobile Banking App", "Digital banking application", "Active", "2024-03-01", 300);
        Project dataAnalytics = new Project("Data Analytics Dashboard", "Business intelligence platform", "Completed", "2023-09-01", 200);

        List<Project> projects = List.of(ecommerce, mobileBanking, dataAnalytics);
        projectRepository.saveAll(projects);

        // Create Skills
        Skill problemSolving = new Skill("Problem Solving", "Advanced", "Technical");
        Skill teamLeadership = new Skill("Team Leadership", "Expert", "Soft Skills");
        Skill agileMethodology = new Skill("Agile Methodology", "Advanced", "Process");
        Skill systemDesign = new Skill("System Design", "Expert", "Technical");
        Skill codeReview = new Skill("Code Review", "Advanced", "Technical");
        Skill testing = new Skill("Testing", "Intermediate", "Technical");

        List<Skill> skills = List.of(problemSolving, teamLeadership, agileMethodology, systemDesign, codeReview, testing);
        skillRepository.saveAll(skills);

        System.out.println("Created sample data (fallback):");
        System.out.println("- " + people.size() + " people");
        System.out.println("- " + technologies.size() + " technologies");
        System.out.println("- " + companies.size() + " companies");
        System.out.println("- " + projects.size() + " projects");
        System.out.println("- " + skills.size() + " skills");
        
        // Display some sample queries
        System.out.println("\nSample data queries:");
        System.out.println("People: " + personRepository.findAll().size());
        System.out.println("Technologies: " + technologyRepository.findAll().size());
        System.out.println("Companies: " + companyRepository.findAll().size());
        System.out.println("Projects: " + projectRepository.findAll().size());
        System.out.println("Skills: " + skillRepository.findAll().size());
    }
}
