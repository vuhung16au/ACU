package com.acu.neo4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public Person savePerson(Person person) {
        return personRepository.save(person);
    }

    public List<Person> getAllPeople() {
        return personRepository.findAll();
    }

    public Optional<Person> getPersonById(Long id) {
        return personRepository.findById(id);
    }

    public List<Person> getPeopleByName(String name) {
        return personRepository.findByName(name);
    }

    public List<Person> getPeopleOlderThan(int age) {
        return personRepository.findPeopleOlderThan(age);
    }

    public List<Person> getAllPeopleOrderedByName() {
        return personRepository.findAllOrderedByName();
    }

    public void deletePerson(Long id) {
        personRepository.deleteById(id);
    }
}
