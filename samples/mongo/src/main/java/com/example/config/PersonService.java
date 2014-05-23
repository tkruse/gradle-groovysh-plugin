package com.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class PersonService {

    PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public void normalizeNames(String id) {
        Person person = personRepository.findById(id);
        person.setFirstName(StringUtils.capitalize(person.getFirstName()));
        person.setLastName(StringUtils.capitalize(person.getLastName()));
        personRepository.save(person);
    }

}
