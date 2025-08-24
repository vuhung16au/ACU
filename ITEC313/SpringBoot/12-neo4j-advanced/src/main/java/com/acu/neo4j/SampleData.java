package com.acu.neo4j;

import java.util.List;

public class SampleData {
    private List<PersonData> people;
    private List<TechnologyData> technologies;
    private List<CompanyData> companies;
    private List<ProjectData> projects;
    private List<SkillData> skills;

    // Getters and Setters
    public List<PersonData> getPeople() {
        return people;
    }

    public void setPeople(List<PersonData> people) {
        this.people = people;
    }

    public List<TechnologyData> getTechnologies() {
        return technologies;
    }

    public void setTechnologies(List<TechnologyData> technologies) {
        this.technologies = technologies;
    }

    public List<CompanyData> getCompanies() {
        return companies;
    }

    public void setCompanies(List<CompanyData> companies) {
        this.companies = companies;
    }

    public List<ProjectData> getProjects() {
        return projects;
    }

    public void setProjects(List<ProjectData> projects) {
        this.projects = projects;
    }

    public List<SkillData> getSkills() {
        return skills;
    }

    public void setSkills(List<SkillData> skills) {
        this.skills = skills;
    }

    // Inner classes for JSON data
    public static class PersonData {
        private String name;
        private int age;
        private String email;
        private String location;
        private int experienceYears;

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public int getAge() { return age; }
        public void setAge(int age) { this.age = age; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getLocation() { return location; }
        public void setLocation(String location) { this.location = location; }
        public int getExperienceYears() { return experienceYears; }
        public void setExperienceYears(int experienceYears) { this.experienceYears = experienceYears; }
    }

    public static class TechnologyData {
        private String name;
        private String description;
        private String version;
        private int popularityScore;

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public String getVersion() { return version; }
        public void setVersion(String version) { this.version = version; }
        public int getPopularityScore() { return popularityScore; }
        public void setPopularityScore(int popularityScore) { this.popularityScore = popularityScore; }
    }

    public static class CompanyData {
        private String name;
        private String industry;
        private String size;
        private int foundedYear;

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getIndustry() { return industry; }
        public void setIndustry(String industry) { this.industry = industry; }
        public String getSize() { return size; }
        public void setSize(String size) { this.size = size; }
        public int getFoundedYear() { return foundedYear; }
        public void setFoundedYear(int foundedYear) { this.foundedYear = foundedYear; }
    }

    public static class ProjectData {
        private String name;
        private String description;
        private String status;
        private String startDate;
        private int budget;

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        public String getStartDate() { return startDate; }
        public void setStartDate(String startDate) { this.startDate = startDate; }
        public int getBudget() { return budget; }
        public void setBudget(int budget) { this.budget = budget; }
    }

    public static class SkillData {
        private String name;
        private String level;
        private String category;

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getLevel() { return level; }
        public void setLevel(String level) { this.level = level; }
        public String getCategory() { return category; }
        public void setCategory(String category) { this.category = category; }
    }
}
