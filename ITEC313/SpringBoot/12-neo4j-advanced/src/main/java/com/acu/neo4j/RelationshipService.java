package com.acu.neo4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Service
public class RelationshipService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private TechnologyRepository technologyRepository;

    private final Random random = new Random();

    @Transactional
    public void createRelationships() {
        System.out.println("Creating relationships between nodes...");

        List<Person> people = personRepository.findAll();
        List<Company> companies = companyRepository.findAll();
        List<Project> projects = projectRepository.findAll();
        List<Skill> skills = skillRepository.findAll();
        List<Technology> technologies = technologyRepository.findAll();

        if (people.isEmpty() || companies.isEmpty() || projects.isEmpty() || 
            skills.isEmpty() || technologies.isEmpty()) {
            System.out.println("Cannot create relationships: some entity lists are empty");
            return;
        }

        // Create WORKS_FOR relationships
        createWorksForRelationships(people, companies);
        
        // Create WORKS_ON relationships
        createWorksOnRelationships(people, projects);
        
        // Create HAS_SKILL relationships
        createHasSkillRelationships(people, skills);
        
        // Create KNOWS_TECHNOLOGY relationships
        createKnowsTechnologyRelationships(people, technologies);

        // Create HAS_PROJECT relationships (Company -> Project)
        createHasProjectRelationships(companies, projects);
        
        // Create USES_TECHNOLOGY relationships (Company -> Technology)
        createUsesTechnologyRelationships(companies, technologies);

        System.out.println("Relationships created successfully!");
    }

    private void createWorksForRelationships(List<Person> people, List<Company> companies) {
        String[] positions = {"Software Engineer", "Senior Developer", "Team Lead", "Architect", "Manager"};
        String[] startDates = {"2020-01-15", "2021-03-01", "2022-06-15", "2023-01-01", "2023-09-01"};

        for (Person person : people) {
            // Each person works for 1-2 companies
            int numCompanies = random.nextInt(2) + 1;
            for (int i = 0; i < numCompanies; i++) {
                Company company = companies.get(random.nextInt(companies.size()));
                String position = positions[random.nextInt(positions.length)];
                String startDate = startDates[random.nextInt(startDates.length)];

                WorksFor worksFor = new WorksFor(position, startDate, company);
                if (person.getCompanies() == null) {
                    person.setCompanies(List.of(worksFor));
                } else {
                    person.getCompanies().add(worksFor);
                }
            }
            personRepository.save(person);
        }
        System.out.println("Created WORKS_FOR relationships");
    }

    private void createWorksOnRelationships(List<Person> people, List<Project> projects) {
        String[] roles = {"Developer", "Senior Developer", "Team Lead", "Architect", "Project Manager"};
        String[] startDates = {"2024-01-01", "2024-02-01", "2024-03-01", "2024-04-01", "2024-05-01"};

        for (Person person : people) {
            // Each person works on 1-3 projects
            int numProjects = random.nextInt(3) + 1;
            for (int i = 0; i < numProjects; i++) {
                Project project = projects.get(random.nextInt(projects.size()));
                String role = roles[random.nextInt(roles.length)];
                String startDate = startDates[random.nextInt(startDates.length)];

                WorksOn worksOn = new WorksOn(role, startDate, project);
                if (person.getProjects() == null) {
                    person.setProjects(List.of(worksOn));
                } else {
                    person.getProjects().add(worksOn);
                }
            }
            personRepository.save(person);
        }
        System.out.println("Created WORKS_ON relationships");
    }

    private void createHasSkillRelationships(List<Person> people, List<Skill> skills) {
        String[] proficiencyLevels = {"Beginner", "Intermediate", "Advanced", "Expert"};
        Integer[] yearsOfExperience = {1, 2, 3, 5, 7, 10};

        for (Person person : people) {
            // Each person has 3-6 skills
            int numSkills = random.nextInt(4) + 3;
            for (int i = 0; i < numSkills; i++) {
                Skill skill = skills.get(random.nextInt(skills.size()));
                String proficiencyLevel = proficiencyLevels[random.nextInt(proficiencyLevels.length)];
                Integer years = yearsOfExperience[random.nextInt(yearsOfExperience.length)];

                HasSkill hasSkill = new HasSkill(proficiencyLevel, years, skill);
                if (person.getSkills() == null) {
                    person.setSkills(List.of(hasSkill));
                } else {
                    person.getSkills().add(hasSkill);
                }
            }
            personRepository.save(person);
        }
        System.out.println("Created HAS_SKILL relationships");
    }

    private void createKnowsTechnologyRelationships(List<Person> people, List<Technology> technologies) {
        String[] proficiencyLevels = {"Beginner", "Intermediate", "Advanced", "Expert"};
        Integer[] yearsOfExperience = {1, 2, 3, 5, 7, 10};

        for (Person person : people) {
            // Each person knows 2-5 technologies
            int numTechnologies = random.nextInt(4) + 2;
            for (int i = 0; i < numTechnologies; i++) {
                Technology technology = technologies.get(random.nextInt(technologies.size()));
                String proficiencyLevel = proficiencyLevels[random.nextInt(proficiencyLevels.length)];
                Integer years = yearsOfExperience[random.nextInt(yearsOfExperience.length)];

                KnowsTechnology knowsTechnology = new KnowsTechnology(proficiencyLevel, years, technology);
                if (person.getTechnologies() == null) {
                    person.setTechnologies(List.of(knowsTechnology));
                } else {
                    person.getTechnologies().add(knowsTechnology);
                }
            }
            personRepository.save(person);
        }
        System.out.println("Created KNOWS_TECHNOLOGY relationships");
    }

    private void createHasProjectRelationships(List<Company> companies, List<Project> projects) {
        String[] projectTypes = {"Internal", "Client", "Research", "Product"};
        String[] startDates = {"2023-01-01", "2023-06-01", "2024-01-01", "2024-06-01"};

        for (Company company : companies) {
            // Each company has 1-3 projects
            int numProjects = random.nextInt(3) + 1;
            for (int i = 0; i < numProjects; i++) {
                Project project = projects.get(random.nextInt(projects.size()));
                String projectType = projectTypes[random.nextInt(projectTypes.length)];
                String startDate = startDates[random.nextInt(startDates.length)];

                HasProject hasProject = new HasProject(projectType, startDate, project);
                if (company.getProjects() == null) {
                    company.setProjects(List.of(hasProject));
                } else {
                    company.getProjects().add(hasProject);
                }
            }
            companyRepository.save(company);
        }
        System.out.println("Created HAS_PROJECT relationships");
    }

    private void createUsesTechnologyRelationships(List<Company> companies, List<Technology> technologies) {
        String[] usageTypes = {"Primary", "Secondary", "Legacy", "Experimental"};
        String[] adoptionDates = {"2020-01-01", "2021-01-01", "2022-01-01", "2023-01-01", "2024-01-01"};

        for (Company company : companies) {
            // Each company uses 2-5 technologies
            int numTechnologies = random.nextInt(4) + 2;
            for (int i = 0; i < numTechnologies; i++) {
                Technology technology = technologies.get(random.nextInt(technologies.size()));
                String usageType = usageTypes[random.nextInt(usageTypes.length)];
                String adoptionDate = adoptionDates[random.nextInt(adoptionDates.length)];

                UsesTechnology usesTechnology = new UsesTechnology(usageType, adoptionDate, technology);
                if (company.getTechnologies() == null) {
                    company.setTechnologies(List.of(usesTechnology));
                } else {
                    company.getTechnologies().add(usesTechnology);
                }
            }
            companyRepository.save(company);
        }
        System.out.println("Created USES_TECHNOLOGY relationships");
    }

    public void displayRelationshipStats() {
        System.out.println("\n=== Relationship Statistics ===");
        
        List<Person> people = personRepository.findAll();
        for (Person person : people) {
            System.out.println("\nPerson: " + person.getName());
            
            if (person.getCompanies() != null) {
                System.out.println("  Works for: " + person.getCompanies().size() + " companies");
                for (WorksFor worksFor : person.getCompanies()) {
                    System.out.println("    - " + worksFor.getCompany().getName() + " as " + worksFor.getPosition());
                }
            }
            
            if (person.getProjects() != null) {
                System.out.println("  Works on: " + person.getProjects().size() + " projects");
                for (WorksOn worksOn : person.getProjects()) {
                    System.out.println("    - " + worksOn.getProject().getName() + " as " + worksOn.getRole());
                }
            }
            
            if (person.getSkills() != null) {
                System.out.println("  Has skills: " + person.getSkills().size() + " skills");
                for (HasSkill hasSkill : person.getSkills()) {
                    System.out.println("    - " + hasSkill.getSkill().getName() + " (" + hasSkill.getProficiencyLevel() + ")");
                }
            }
            
            if (person.getTechnologies() != null) {
                System.out.println("  Knows technologies: " + person.getTechnologies().size() + " technologies");
                for (KnowsTechnology knowsTechnology : person.getTechnologies()) {
                    System.out.println("    - " + knowsTechnology.getTechnology().getName() + " (" + knowsTechnology.getProficiencyLevel() + ")");
                }
            }
        }
    }
}
