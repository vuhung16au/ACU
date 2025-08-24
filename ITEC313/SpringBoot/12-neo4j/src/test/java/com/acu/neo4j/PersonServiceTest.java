package com.acu.neo4j;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.Neo4jContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
class PersonServiceTest {

    @Container
    static Neo4jContainer<?> neo4jContainer = new Neo4jContainer<>("neo4j:latest")
            .withAdminPassword("Sydney@9876");

    @DynamicPropertySource
    static void neo4jProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.neo4j.uri", neo4jContainer::getBoltUrl);
        registry.add("spring.neo4j.authentication.username", () -> "neo4j");
        registry.add("spring.neo4j.authentication.password", neo4jContainer::getAdminPassword);
    }

    @Autowired
    private PersonService personService;

    @Test
    void testSaveAndRetrievePerson() {
        // Create a new person
        Person person = new Person("John Doe", 30);
        
        // Save the person
        Person savedPerson = personService.savePerson(person);
        
        // Verify the person was saved with an ID
        assertNotNull(savedPerson.getId());
        assertEquals("John Doe", savedPerson.getName());
        assertEquals(30, savedPerson.getAge());
        
        // Retrieve the person by ID
        Optional<Person> retrievedPerson = personService.getPersonById(savedPerson.getId());
        
        // Verify the person was retrieved correctly
        assertTrue(retrievedPerson.isPresent());
        assertEquals("John Doe", retrievedPerson.get().getName());
        assertEquals(30, retrievedPerson.get().getAge());
    }

    @Test
    void testFindPeopleByName() {
        // Clear existing data first
        List<Person> existingPeople = personService.getAllPeople();
        existingPeople.forEach(person -> personService.deletePerson(person.getId()));
        
        // Create and save multiple people with the same name
        Person person1 = personService.savePerson(new Person("Jane Smith", 25));
        Person person2 = personService.savePerson(new Person("Jane Smith", 35));
        Person person3 = personService.savePerson(new Person("Bob Johnson", 40));
        
        // Find people by name
        List<Person> janePeople = personService.getPeopleByName("Jane Smith");
        
        // Verify we found the correct people
        assertEquals(2, janePeople.size());
        assertTrue(janePeople.stream().allMatch(p -> p.getName().equals("Jane Smith")));
    }

    @Test
    void testFindPeopleOlderThan() {
        // Clear existing data first
        List<Person> existingPeople = personService.getAllPeople();
        existingPeople.forEach(person -> personService.deletePerson(person.getId()));
        
        // Create and save people with different ages
        Person person1 = personService.savePerson(new Person("Young Person", 20));
        Person person2 = personService.savePerson(new Person("Middle Person", 30));
        Person person3 = personService.savePerson(new Person("Older Person", 40));
        
        // Find people older than 25
        List<Person> olderPeople = personService.getPeopleOlderThan(25);
        
        // Verify we found the correct people
        assertEquals(2, olderPeople.size());
        assertTrue(olderPeople.stream().allMatch(p -> p.getAge() > 25));
    }

    @Test
    void testGetAllPeopleOrderedByName() {
        // Clear existing data first
        List<Person> existingPeople = personService.getAllPeople();
        existingPeople.forEach(person -> personService.deletePerson(person.getId()));
        
        // Create and save people in random order
        Person person1 = personService.savePerson(new Person("Charlie", 25));
        Person person2 = personService.savePerson(new Person("Alice", 30));
        Person person3 = personService.savePerson(new Person("Bob", 35));
        
        // Get all people ordered by name
        List<Person> orderedPeople = personService.getAllPeopleOrderedByName();
        
        // Verify the order
        assertEquals(3, orderedPeople.size());
        
        // Check that the people are in alphabetical order
        assertEquals("Alice", orderedPeople.get(0).getName());
        assertEquals("Bob", orderedPeople.get(1).getName());
        assertEquals("Charlie", orderedPeople.get(2).getName());
    }

    @Test
    void testDeletePerson() {
        // Create and save a person
        Person person = personService.savePerson(new Person("To Delete", 50));
        Long personId = person.getId();
        
        // Verify the person exists
        assertTrue(personService.getPersonById(personId).isPresent());
        
        // Delete the person
        personService.deletePerson(personId);
        
        // Verify the person was deleted
        assertFalse(personService.getPersonById(personId).isPresent());
    }

    @Test
    void testGetAllPeople() {
        // Clear existing data first
        List<Person> existingPeople = personService.getAllPeople();
        existingPeople.forEach(person -> personService.deletePerson(person.getId()));
        
        // Create and save multiple people
        Person person1 = personService.savePerson(new Person("Person 1", 25));
        Person person2 = personService.savePerson(new Person("Person 2", 30));
        Person person3 = personService.savePerson(new Person("Person 3", 35));
        
        // Get all people
        List<Person> allPeople = personService.getAllPeople();
        
        // Verify we have exactly the 3 people we just created
        assertEquals(3, allPeople.size());
        
        // Verify our created people are in the list
        assertTrue(allPeople.stream().anyMatch(p -> p.getName().equals("Person 1")));
        assertTrue(allPeople.stream().anyMatch(p -> p.getName().equals("Person 2")));
        assertTrue(allPeople.stream().anyMatch(p -> p.getName().equals("Person 3")));
    }
}
