package com.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * example spring service
 */
@Service
public class PersonService {

    PersonRepository personRepository;

    @Autowired
    public PersonService(final PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public void normalizeNames(final String personId) {
        final Person person = personRepository.findByPId(personId);
        savePerson(person);
    }

    private void savePerson(final Person person) {
        person.setFirstName(StringUtils.capitalize(person.getFirstName()));
        person.setLastName(StringUtils.capitalize(person.getLastName()));
        personRepository.save(person);
    }

}
